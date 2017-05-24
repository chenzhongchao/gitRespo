package com.fise.service.memberthirdparty.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fise.base.ErrorCode;
import com.fise.base.Response;
import com.fise.dao.MemberThirdpartyMapper;
import com.fise.framework.redis.RedisManager;
import com.fise.model.entity.Member;
import com.fise.model.entity.MemberAccount;
import com.fise.model.entity.MemberThirdparty;
import com.fise.model.entity.Order;
import com.fise.model.param.OAuthParam;
import com.fise.model.result.LoginResult;
import com.fise.model.result.MemberProfileResult;
import com.fise.service.account.IAccountService;
import com.fise.service.member.IMemberService;
import com.fise.service.memberthirdparty.IMemberThirdpartyService;
import com.fise.service.order.IOrderService;
import com.fise.utils.CommonUtil;
import com.fise.utils.Constants;
import com.fise.utils.DateUtil;
import com.fise.utils.StringUtil;

import redis.clients.jedis.Jedis;

@Service
public class MemberThirdpartyServiceImpl implements IMemberThirdpartyService {
	@Resource
	private IMemberService memberService;
	
	@Resource
	private IAccountService accountService;
	
	@Resource
	private IOrderService orderService;
	
	@Autowired
	private MemberThirdpartyMapper memberThirdpartyDao;
	
	@Override
	public void insertMemberThirdparty(MemberThirdparty memberThirdparty) {
		memberThirdpartyDao.insertSelective(memberThirdparty);
	}
	
	@Override
	public MemberThirdparty getMemberThirdparty(MemberThirdparty memberThirdparty) {
		return memberThirdpartyDao.selectByMemberThirdpartySelective(memberThirdparty);
	}

	@Override
	public void updateMemberThirdparty(MemberThirdparty memberThirdparty) {
		memberThirdpartyDao.updateByPrimaryKeySelective(memberThirdparty);
	}

	@Override
	public Response bindThirdparty(OAuthParam param) {
		Response resp = new Response();
		
		// 需要哪些入参
		String mobile = param.getMobile();
		String verifyCode = param.getSmsCode();
		String thirdParty = param.getThirdParty();
		String loginType = param.getLoginType();
		Map<String, String> userInfo = param.getUserInfo();
		
		
		String unionid = userInfo.get("unionid");
		String nickName = userInfo.get("nickname");
		String avatar = userInfo.get("avatar");
		
		// 验证码
		if (checkVerifyCode(mobile, verifyCode) == false) {
			resp.failure(ErrorCode.ERROR_MEMBER_REGISTER_VERIFY_CODE_ERROR);
			return resp;
		}
		
		if (Constants.THIRD_PARTY_LOGIN_WECHAT.equals(thirdParty)) {
			Integer thirdpartyId = Constants.THIRD_PARTY_LOGIN_ID_WECHAT;
			MemberThirdparty memberThirdparty = new MemberThirdparty();
			memberThirdparty.setThirdpartyId(thirdpartyId);
			memberThirdparty.setUnionid(unionid);
			
			MemberThirdparty memberThirdpartyInDb = getMemberThirdparty(memberThirdparty);
			if (memberThirdpartyInDb == null) {
				resp.setErrorCode(ErrorCode.ERROR_LOGIN_BIND_THIRDPARTY_NOT_AUTHED);
				return resp;
			}
			
			Long updateTimeStamp = memberThirdpartyInDb.getUpdateTime().getTime();
			Long nowTimeStamp = System.currentTimeMillis();
			if (nowTimeStamp - updateTimeStamp > Constants.THIRDPARTY_LOGIN_AUTH_BIND_TIMEINTERVAL_MILLISECONDS) {
				resp.setErrorCode(ErrorCode.ERROR_LOGIN_BIND_THIRDPARTY_NEED_REAUTHE);
				return resp;
			}
			Date date = new Date(nowTimeStamp);
			
			// 已注册，绑定后走登录流程，为注册，先注册再绑定
			Member memberInDb = memberService.getMemberByMobile(mobile);
			if (memberInDb == null) {
				// 注册
				Member member = new Member();
				member.setMobile(mobile);
				member.setPassword("");	
				member.setNickName(nickName);	
				member.setSignature("一开始知道要签名，我是拒绝的");			// 设置默认签名		
				member.setAvatar(avatar);		// 设置默认头像路径，需要优化从后台配置取	
				
				member.setRegtime(date);							// 设置注册时间
				member.setLastlogin(date);							// 设置最近登录时间
				memberService.insertMember(member);
				
				// 生成账户信息
				MemberAccount account = new MemberAccount();
				account.setMemberId(member.getMemberId());
				account.setAvailableBalance(0L);
				account.setFrozenBalance(0L);
				accountService.createMemberAccount(account);
				
				// 生成token
				String accessToken = genAccessToken(member.getMemberId());
				
				// 绑定
				memberThirdpartyInDb.setMemberId(member.getMemberId());
				memberThirdpartyInDb.setUpdateTime(date);
				memberThirdpartyDao.updateByPrimaryKeySelective(memberThirdpartyInDb);
				
				MemberProfileResult result = new MemberProfileResult();
				result.setMemberId(member.getMemberId());
				result.setLastLogin(date);
				result.setAccessToken(accessToken);
				result.setMobile(member.getMobile());
				result.setAvatar(member.getAvatar());
				result.setSex(member.getSex());
				result.setBirthday(member.getBirthday());
				result.setHeight(member.getHeight());
				result.setWeight(member.getWeight());
				result.setHasLoginPasswd(StringUtil.isNotEmpty(member.getPassword()));
				result.setNickName(member.getNickName());
				result.setAvailableBalance(account.getAvailableBalance());
				result.setHasAccountPasswd(StringUtil.isNotEmpty(account.getPassword()));
				result.setNotPayCount(0);
				result.setNotCommentCount(0);
				
				resp.setData(result);
			} else {
				// 登录
				Integer memberId = memberInDb.getMemberId();
				String accessToken = genAccessToken(memberId);
				
				String avatarInDb = memberInDb.getAvatar();
				String mobileInDb = memberInDb.getMobile();
				String nickNameInDb = memberInDb.getNickName();
				String defaultNickName = mobileInDb.substring(0,3) + "****" + mobileInDb.substring(7);
				
				Member memberForUpdate = new Member();
				memberForUpdate.setLastlogin(date);
				memberForUpdate.setMemberId(memberId);
				if (StringUtil.isEmpty(nickNameInDb) || nickNameInDb.equals(defaultNickName)) {
					memberForUpdate.setNickName(nickName);			// 微信nickname
					memberInDb.setNickName(nickName);
				}
				if (StringUtil.isEmpty(avatarInDb) || avatarInDb.equals(Constants.DEFAULT_AVATAR_URL)) {
					memberForUpdate.setAvatar(avatar);				// 微信avatar
					memberInDb.setAvatar(avatar);
				}
				memberService.updateMember(memberForUpdate);
				
				// 绑定
				memberThirdpartyInDb.setMemberId(memberId);
				memberThirdpartyInDb.setUpdateTime(date);
				memberThirdpartyDao.updateByPrimaryKeySelective(memberThirdpartyInDb);
				
				// 账户信息
				MemberAccount account = accountService.getMemberAccountByMemberId(memberId);
				
				Integer notPayCount = 0;
				Integer notCommentCount = 0;
				Order orderParam = new Order();
				orderParam.setMemberId(memberId);
				orderParam.setStatus(Constants.ORDER_STATUS_NOT_CLEARING);
				List<Order> order = orderService.getOrderSelective(orderParam);
				if (order != null) {
					notPayCount = order.size();
				}
				
				orderParam = new Order();
				orderParam.setMemberId(memberId);
				orderParam.setIsCommented(0);
				order = orderService.getOrderSelective(orderParam);
				if (order != null) {
					notCommentCount = order.size();
				}	
				
				// 返回
				MemberProfileResult result = new MemberProfileResult();
				// LoginResult result = new LoginResult();
				result.setMemberId(memberId);
				result.setLastLogin(date);
				result.setAccessToken(accessToken);
				result.setMobile(memberInDb.getMobile());
				result.setAvatar(memberInDb.getAvatar());
				result.setSex(memberInDb.getSex());
				result.setBirthday(memberInDb.getBirthday());
				result.setHeight(memberInDb.getHeight());
				result.setWeight(memberInDb.getWeight());
				result.setHasLoginPasswd(StringUtil.isNotEmpty(memberInDb.getPassword()));
				result.setNickName(memberInDb.getNickName());
				result.setAvailableBalance(account.getAvailableBalance());
				result.setHasAccountPasswd(StringUtil.isNotEmpty(account.getPassword()));
				result.setNotCommentCount(notCommentCount);
				result.setNotPayCount(notPayCount);
				
				resp.setData(result);
			}
		}
		
		return resp;
	}
	
	// 校验绑定验证码
	private boolean checkVerifyCode(String mobile, String verifyCode) {
		Jedis jedis = null;
		try {
		  	jedis = RedisManager.getInstance().getResource(Constants.REDIS_POOL_NAME_MEMBER);
		    String key = Constants.REDIS_KEY_PREFIX_MEMBER_VERIFYCODE + mobile;
		    String codeInRedis = jedis.get(key);
		    if (StringUtil.isNotEmpty(codeInRedis) && codeInRedis.equals(verifyCode)) {
		    	return true;
		    }
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			RedisManager.getInstance().returnResource(Constants.REDIS_POOL_NAME_MEMBER, jedis);
		}
		
		return false;
	}
	
	// 生成access token
	private String genAccessToken(Integer memberId) {
		Jedis jedis = null;
		String accessToken = null;
		try {
			jedis = RedisManager.getInstance().getResource(Constants.REDIS_POOL_NAME_MEMBER);
			accessToken = CommonUtil.getAccessToken();
			String key = Constants.REDIS_KEY_PREFIX_MEMBER_ACCESS_TOKEN + accessToken;
			String value = memberId + "";
			String ret = jedis.setex(key, Constants.ACCESS_TOKEN_EXPIRE_SECONDS, value);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			RedisManager.getInstance().returnResource(Constants.REDIS_POOL_NAME_MEMBER, jedis);
		}
			
		return accessToken;
	}
}
