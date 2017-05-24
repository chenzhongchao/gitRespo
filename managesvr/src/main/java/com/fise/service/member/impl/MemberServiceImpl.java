package com.fise.service.member.impl;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.xml.crypto.Data;

import org.apache.commons.pool.impl.GenericKeyedObjectPool.Config;
import org.omg.CORBA.PRIVATE_MEMBER;
import org.springframework.util.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fise.base.ErrorCode;
import com.fise.base.HttpContext;
import com.fise.base.Page;
import com.fise.base.Response;
import com.fise.dao.MemberMapper;
import com.fise.framework.config.ConfigProperties;
import com.fise.framework.redis.RedisManager;
import com.fise.model.dto.AppVersionDto;
import com.fise.model.dto.RegisterStatDto;
import com.fise.model.entity.Comment;
import com.fise.model.entity.Gym;
import com.fise.model.entity.Member;
import com.fise.model.entity.MemberAccount;
import com.fise.model.entity.Order;
import com.fise.model.param.CommentAddParam;
import com.fise.model.param.InitParam;
import com.fise.model.param.LoginParam;
import com.fise.model.param.LogoutParam;
import com.fise.model.param.MobileChangeParam;
import com.fise.model.param.PasswdResetParam;
import com.fise.model.param.SmsFetchParam;
import com.fise.model.result.CommentResult;
import com.fise.model.result.InitAppResult;
import com.fise.model.result.LoginResult;
import com.fise.model.result.MemberProfileResult;
import com.fise.model.result.RegisterResult;
import com.fise.service.account.IAccountService;
import com.fise.service.comment.ICommentService;
import com.fise.service.member.IMemberService;
import com.fise.service.order.IOrderService;
import com.fise.utils.CommonUtil;
import com.fise.utils.Constants;
import com.fise.utils.DateUtil;
import com.fise.utils.StringUtil;
import com.fise.utils.sms.CLSmsChannel;
import com.qq.jutil.j4log.Logger;

import redis.clients.jedis.Jedis;

@Service
public class MemberServiceImpl implements IMemberService {
	@Autowired
	private MemberMapper memberDao;
	
	@Resource
	private IAccountService accountService;
	
	@Resource
	private ICommentService commentService;
	
	@Resource 
	private IOrderService orderService;
	
	@Override
	public boolean isMemberExist(Integer memberId) {
		return false;
	}

	@Override
	public boolean checkSMSCode(String mobile, String code) {
		return false;
	}

	// 注册手机号是否已存在
	@Override
	public boolean isMobileExist(String mobile) {
		
		Integer count = memberDao.getMobileCount(mobile);
		if (count == 0) return false;
		
		return true;
	}
	
	// 通过memberId查Member实体
	@Override
	public Member getMemberById(Integer memberId) {
		Member member = new Member();
		member = memberDao.getMemberById(memberId);
		
		return member;
	}
	
	// 通过手机号查Member实体
	@Override
	public Member getMemberByMobile(String mobile) {
		Member member = memberDao.getMemberByMobile(mobile);
		
		return member;
	}

	// 更新member表记录
	@Override
	public void updateMemberLastLogin(Member member) {
		memberDao.updateMemberLastLogin(member);
		
		return ;
	}
	
	// 新用户注册
	@Override
	public Response register(String mobile, String password, String verifyCode) {
		Response resp = new Response();
		
		// 查询是否已经存在该注册手机号
		if (isMobileExist(mobile)) {
			resp.failure(ErrorCode.ERROR_REGISTER_MEMBER_MOBILE_EXISTED);
			return resp;
		}
		
		// 判断验证码
		Jedis jedis = null;
        try {
        	jedis = RedisManager.getInstance().getResource(Constants.REDIS_POOL_NAME_MEMBER);
    		String key = Constants.REDIS_KEY_PREFIX_MEMBER_VERIFYCODE + mobile;
    		String codeInRedis = jedis.get(key);
    		if (StringUtil.isEmpty(codeInRedis) || !codeInRedis.equals(verifyCode)) {
    			resp.failure(ErrorCode.ERROR_MEMBER_REGISTER_VERIFY_CODE_ERROR);
    			return resp;
    		}
		} catch (Exception e) {
			e.printStackTrace();
			resp.failure(ErrorCode.ERROR_CACHE);
			return resp;
		} finally {
			RedisManager.getInstance().returnResource(Constants.REDIS_POOL_NAME_MEMBER, jedis);
		}
		
		Member member = new Member();
		member.setMobile(mobile);
		member.setPassword(password);	
		mobile = mobile.substring(0,3) + "****" + mobile.substring(7);   // 设置默认昵称为手机号， 隐去中间4位
		member.setNickName(mobile);	
		member.setSignature("一开始知道要签名，我是拒绝的");			// 设置默认签名		
		member.setAvatar("http://xx.com/avatar.png");		// 设置默认头像路径，需要优化从后台配置取	
		
		Date date = new Date(System.currentTimeMillis());
		member.setRegtime(date);							// 设置注册时间
		member.setLastlogin(date);							// 设置最近登录时间
		// member.setAccessToken(CommonUtil.getAccessToken());	// 设置access token
		memberDao.insertMember2(member);
		
		// 生成账户信息
		MemberAccount account = new MemberAccount();
		account.setMemberId(member.getMemberId());
		account.setAvailableBalance(0L);
		account.setFrozenBalance(0L);
		accountService.createMemberAccount(account);
		
		// 生成token
		String accessToken = genAccessToken(member.getMemberId());
		
		// 返回
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
		result.setNotCommentCount(0);
		result.setNotPayCount(0);
		
		resp.success(result);
		return resp;
	}

	// 用户登录
	@Override
	public Response login(LoginParam param) {
		Response resp = new Response();

		Member memberInDB = getMemberByMobile(param.getMobile());
		if (memberInDB == null) {
			resp.failure(ErrorCode.ERROR_MEMBER_INDB_IS_NULL);
			return resp;
		}
		
		if (!param.getPassword().equals(memberInDB.getPassword())) {
			resp.failure(ErrorCode.ERROR_PASSWORD_INCORRECT);
			return resp;
		}
		
		resp = login(memberInDB);		
		return resp;
	}
	
	// 用户登录
	@Override
	public Response thirdpartyLogin(Integer memberId) {
		Response resp = new Response();
			
		Member memberInDB = getMemberById(memberId);
		if (memberInDB == null) {
			resp.failure(ErrorCode.ERROR_MEMBER_INDB_IS_NULL);
			return resp;
		}
			
		resp = login(memberInDB);
		return resp;
	}
	
	// login真实逻辑
	private Response login(Member member) {
		Response resp = new Response();
		
		Integer memberId = member.getMemberId();
		String accessToken = genAccessToken(memberId);
		
		Member memberForUpdate = new Member();
		memberForUpdate.setLastlogin(new Date());
		memberForUpdate.setMemberId(memberId);
		memberDao.updateMember(memberForUpdate);
		
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
		
		
		MemberProfileResult result = new MemberProfileResult();
		// LoginResult result = new LoginResult();
		result.setMobile(member.getMobile());
		result.setMemberId(memberId);
		result.setLastLogin(memberForUpdate.getLastlogin());
		result.setAccessToken(accessToken);
		result.setAvatar(member.getAvatar());
		result.setSex(member.getSex());
		result.setBirthday(member.getBirthday());
		result.setHeight(member.getHeight());
		result.setWeight(member.getWeight());
		result.setHasLoginPasswd(StringUtil.isNotEmpty(member.getPassword()));
		result.setNickName(member.getNickName());
		result.setAvailableBalance(account.getAvailableBalance());
		result.setHasAccountPasswd(StringUtil.isNotEmpty(account.getPassword()));
		result.setNotPayCount(notPayCount);
		result.setNotCommentCount(notCommentCount);
		
		resp.success(result);
		
		return resp;
	}

	@Override
	public Response logout(LogoutParam param, HttpServletRequest request) {
		Response resp = new Response();;
		Integer memberId = param.getMemberId();
		String accessToken = request.getHeader(Constants.MANAGER_HEADER_FIELD_NAME_ACCESS_TOKEN);
		
		try {
			
			delAccessToken(memberId, accessToken);			
			resp.success();
			
		} catch (Exception e) {
			System.out.println("logout, updateMemberError: " + e.getMessage());
			resp.failure(ErrorCode.ERROR_DATABASE);
		}
		
		return resp;
	}

	@Override
	public Response smsCodeFetch(SmsFetchParam param) {
		Response resp = new Response();
		StringBuilder content = new StringBuilder();
		String code = StringUtil.makeCode(4, true);
		
		String mobile = param.getMobile();
		if ("register".equals(param.getCmd())) {
			content.append("您的注册验证码是：");
			content.append(code);
			content.append(".请完成注册");
		} else if ("resetpwd".equals(param.getCmd())) {
			content.append("您的验证码是：");
			content.append(code);
			content.append(".");
		}else if ("chgmob".equals(param.getCmd())) {
			content.append("您的验证码是：");
			content.append(code);
			content.append(".");
		} else if ("bind".equals(param.getCmd())) {
			content.append("您的验证码是：");
			content.append(code);
			content.append(".");
		}else if ("setpaypwd".equals(param.getCmd())) {
			content.append("您的验证码是：");
			content.append(code);
			content.append(".");
		} else {
			resp.failure(ErrorCode.ERROR_PARAMETER_BUSINESS_CHECK_ERROR);
			return resp;
		}

		boolean retStatus = CLSmsChannel.sendSMS(mobile, content.toString(), false);
		retStatus = true;
		if (!retStatus) {
			resp.failure(ErrorCode.ERROR_SMS_CODE_SEND_FAILED);
			return resp;
		}
		
		Jedis jedis = null;
		try {
			jedis = RedisManager.getInstance().getResource(Constants.REDIS_POOL_NAME_MEMBER);
			String key =Constants.REDIS_KEY_PREFIX_MEMBER_VERIFYCODE + param.getMobile();
			String value = code;
			String ret = jedis.setex(key, Constants.VERIFY_CODE_EXPIRE_TIME, value);
		} catch (Exception e) {
			e.printStackTrace();
			resp.failure(ErrorCode.ERROR_CACHE);
			return resp;
		} finally {
			RedisManager.getInstance().returnResource(Constants.REDIS_POOL_NAME_MEMBER, jedis);
		}
		
		resp.success();
		return resp;
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

	// 删除access token 
	private boolean delAccessToken(Integer memberId, String accessToken) {
		boolean ret = false;
		Jedis jedis = null;
		try {
			
			jedis = RedisManager.getInstance().getResource(Constants.REDIS_POOL_NAME_MEMBER);
			
			String key = Constants.REDIS_KEY_PREFIX_MEMBER_ACCESS_TOKEN + accessToken;
			
			String gymIdInRedis = jedis.get(key);
			if (!memberId.toString().equals(gymIdInRedis)) return false;
			jedis.del(key);
			ret = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			RedisManager.getInstance().returnResource(Constants.REDIS_POOL_NAME_MEMBER, jedis);
		}
		
		return ret;
	}

	@Override
	public Response postComment(CommentAddParam param) {
		Response resp = new Response();
		
		Order order = new Order();
		order.setSn(param.getOrderSn());
		order.setGymId(param.getGymId());
		order.setMemberId(param.getMemberId());
		order.setIsCommented(Constants.ORDER_COMMENT_STATUS_NOT_COMMENT);
		order.setStatus(Constants.ORDER_STATUS_NOT_COMMENTED);
		List<Order> orderList = orderService.getOrderSelective(order);
		if (orderList.size() != 1) {
			resp.failure(ErrorCode.ERROR_ORDER_NOT_EXIST);
			return resp;
		}
		
		// 提交评论
		Comment comment = new Comment();
		comment.setOrderSn(param.getOrderSn());
		comment.setGymId(param.getGymId());
		comment.setMemberId(param.getMemberId());
		comment.setContent(param.getContent());
		comment.setScore(param.getScore());
		commentService.insertComment(comment);
		
		// 更新订单状态、评论状态
		Order orderForUpdate = new Order();
		orderForUpdate.setOrderId(orderList.get(0).getOrderId());
		orderForUpdate.setIsCommented(Constants.ORDER_COMMENT_STATUS_HAVE_COMMENTED);
		orderForUpdate.setStatus(Constants.ORDER_STATUS_HAVA_COMMENTD);
		orderService.updateOrderByPrimaryKeySelective(orderForUpdate);
		
		return resp;
	}

	@Override
	public Response getGymCommentList(Page<Comment> pageParam) {
		Response resp = new Response();

		Map<String, Object> extraParamMap = pageParam.getExtraParam();
		if (extraParamMap == null || extraParamMap.get("gym_id") == null) {
			resp.failure(ErrorCode.ERROR_PARAMETER_BUSINESS_CHECK_ERROR);
		}
		Integer gymId = (Integer)extraParamMap.get("gym_id");
		List<Comment> commentList = commentService.getGymCommentList(gymId, pageParam);
		List<CommentResult> commentResultList = new ArrayList<CommentResult>();
		for(Comment comment : commentList) {
			CommentResult commentResult = new CommentResult();
			Member member = getMemberById(comment.getMemberId());
			
			commentResult.setAvatar(member.getAvatar());
			commentResult.setMemberId(comment.getMemberId());
			commentResult.setNickName(member.getNickName());
			commentResult.setGymId(comment.getGymId());
			commentResult.setCommentId(comment.getCommentId());
			commentResult.setContent(comment.getContent());
			commentResult.setScore(comment.getScore());
			commentResult.setOrderSn(comment.getOrderSn());
			commentResult.setUpdateTime(comment.getUpdateTime());
			
			commentResultList.add(commentResult);
		}
		resp.success(commentResultList);
		
		return resp;
	}

	@Override
	public void insertMember(Member member) {
		memberDao.insertMember2(member);
	}

	@Override
	public void updateMember(Member member) {
		memberDao.updateMember(member);
	}

	@Override
	public Response resetPasswd(Integer memberId, PasswdResetParam param) {
		Response resp = new Response();
		
		Member memberInDb = memberDao.getMemberById(memberId);
		
		if (StringUtil.isNotEmpty(param.getCmd()) && "reset".equals(param.getCmd())) {		// 重置密码
			if (checkVerifyCode(param.getMobile(), param.getVerifyCode()) == false) {
				resp.failure(ErrorCode.ERROR_MEMBER_REGISTER_VERIFY_CODE_ERROR);
				return resp;
			}
			
			if (!param.getMobile().equals(memberInDb.getMobile())) {
				resp.failure(ErrorCode.ERROR_MOBILE_ERROR);
				return resp;
			}
		} else {		// 修改密码
			if (!memberInDb.getPassword().equals(param.getOldPassword())) {
				resp.failure(ErrorCode.ERROR_PASSWORD_INCORRECT);
				return resp;
			}
		}
		
		Member memberForupdate = new Member();
		memberForupdate.setMemberId(memberId);
		memberForupdate.setPassword(param.getNewPassword());
		memberDao.updateMember(memberForupdate);
		
		resp.success();
		return resp;
	}

	@Override
	public Response changeMobile(Integer memberId, MobileChangeParam param) {
		Response resp = new Response();
		
		Integer STEP_VERIFY_OLD_SMS_CODE = 1;
		Integer STEP_VERIFY_ACCOUNT_PASSWD = 2;
		Integer STEP_VERIFY_NEW_SMS_CODE = 3;
		
		Integer step = param.getStep();
		String oldMobile = param.getOldMobile();
		String newMobile = param.getNewMobile();
		String accountPasswd = param.getAccountPasswd();
		String smsCode = param.getSmsCode();
		
		if (step == STEP_VERIFY_OLD_SMS_CODE) {
			Member member = getMemberById(memberId);
			if (!oldMobile.equals(member.getMobile())) {
				resp.failure(ErrorCode.ERROR_MOBILE_ERROR);
				return resp;
			}
			
			if (checkVerifyCode(oldMobile, smsCode) == false) {
				resp.failure(ErrorCode.ERROR_MEMBER_REGISTER_VERIFY_CODE_ERROR);
				return resp;
			}
			setMobileChangeStep(memberId, STEP_VERIFY_ACCOUNT_PASSWD);
		} else if (step == STEP_VERIFY_ACCOUNT_PASSWD) {
			Integer stepInCache = getMobileChangeStep(memberId);
			if (stepInCache.intValue() != STEP_VERIFY_ACCOUNT_PASSWD.intValue()) {
				resp.failure(ErrorCode.ERROR_MOBILE_CHANGE_STEP_ERROR);
				return resp;
			} 
			MemberAccount account = accountService.getMemberAccountByMemberId(memberId);
			if (!account.getPassword().equals(accountPasswd)) {
				resp.failure(ErrorCode.ERROR_PASSWORD_INCORRECT);
				return resp;
			}
			
			setMobileChangeStep(memberId, STEP_VERIFY_NEW_SMS_CODE);
		} else if (step == STEP_VERIFY_NEW_SMS_CODE) {
			Integer stepInCache = getMobileChangeStep(memberId);
			if (stepInCache.intValue() != STEP_VERIFY_NEW_SMS_CODE.intValue()) {
				resp.failure(ErrorCode.ERROR_MOBILE_CHANGE_STEP_ERROR);
				return resp;
			} 
			
			if (checkVerifyCode(newMobile, smsCode) == false) {
				resp.failure(ErrorCode.ERROR_MEMBER_REGISTER_VERIFY_CODE_ERROR);
				return resp;
			}
			
			Member memberForUpdate = new Member();
			memberForUpdate.setMemberId(memberId);
			memberForUpdate.setMobile(newMobile);
			updateMember(memberForUpdate);
		} else {
			resp.failure(ErrorCode.ERROR_PARAMETER_BUSINESS_CHECK_ERROR);
			return resp;
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
	
	// 校验绑定验证码
	private Integer getMobileChangeStep(Integer memberId) {
		Jedis jedis = null;
		Integer step = 1;
		
		try {
		  	jedis = RedisManager.getInstance().getResource(Constants.REDIS_POOL_NAME_MEMBER);
		    String key = Constants.REDIS_KEY_PREFIX_MEMBER_CHANGE_MOBILE_STEP + memberId;
		    String stepInredis = jedis.get(key);
		    if (StringUtil.isNotEmpty(stepInredis)) {
		    	step = Integer.parseInt(stepInredis);
		    }
		} catch (Exception e) {
			e.printStackTrace();
			return 1;
		} finally {
			RedisManager.getInstance().returnResource(Constants.REDIS_POOL_NAME_MEMBER, jedis);
		}
		
		return step;
	}
	
	// 生成access token
	private boolean setMobileChangeStep(Integer memberId, Integer step) {
		Jedis jedis = null;
		try {
			jedis = RedisManager.getInstance().getResource(Constants.REDIS_POOL_NAME_MEMBER);
			String key = Constants.REDIS_KEY_PREFIX_MEMBER_CHANGE_MOBILE_STEP + memberId;
			String value = step + "";
			String ret = jedis.setex(key, Constants.MEMBER_MOBILE_CHANGE_STEP_EXPIRE_SECONDS, value);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			RedisManager.getInstance().returnResource(Constants.REDIS_POOL_NAME_MEMBER, jedis);
		}
		
		return true;
	}

	@Override
	public Response getUserProfile(Integer memberId) {
		Response resp = new Response();
		
		Member member = getMemberById(memberId);
		MemberAccount account = accountService.getMemberAccountByMemberId(memberId);
		
		Integer notPayCount = 0;
		Integer notCommentCount = 0;
		Order orderParam = new Order();
		orderParam.setMemberId(memberId);
		orderParam.setStatus(Constants.ORDER_STATUS_NOT_PAY);
		List<Order> order = orderService.getOrderSelective(orderParam);
		if (order != null) {
			notPayCount = order.size();
		}
		
		orderParam = new Order();
		orderParam.setMemberId(memberId);
		orderParam.setStatus(Constants.ORDER_STATUS_NOT_COMMENTED);
		order = orderService.getOrderSelective(orderParam);
		if (order != null) {
			notCommentCount = order.size();
		}	

		
		MemberProfileResult result = new MemberProfileResult();
		// LoginResult result = new LoginResult();
		result.setMemberId(member.getMemberId());
		result.setLastLogin(member.getLastlogin());
		result.setAccessToken(member.getAccessToken());
		result.setMobile(member.getMobile());
		result.setAvatar(member.getAvatar());
		result.setHasLoginPasswd(StringUtil.isNotEmpty(member.getPassword()));
		result.setNickName(member.getNickName());
		result.setSex(member.getSex());
		result.setBirthday(member.getBirthday());
		result.setHeight(member.getHeight());
		result.setWeight(member.getWeight());
		result.setAvailableBalance(account.getAvailableBalance());
		result.setHasAccountPasswd(StringUtil.isNotEmpty(account.getPassword()));
		result.setNotCommentCount(notCommentCount);
		result.setNotPayCount(notPayCount);
		resp.success(result);
		
		return resp;
	}
	
	public enum InitCmd{
		app_version, user_profile, gym_profile, all, none;
		
		public static InitCmd getCmd(String cmd) {
			if (contains(cmd)) {
				return valueOf(cmd.toLowerCase());
			} else {
				return none;
			}
		}
		
		public static boolean contains(String cmd) {
			InitCmd[] cmds = values();
			for(InitCmd command : cmds) {
				if (command.name().equals(cmd)) {
					return true;
				}
			}
			return false;
		}
	} 
	
	@Override
	public Response initApp(Integer memberId, InitParam param) {
		Response resp = new Response();
		InitAppResult initAppResult = new InitAppResult();
		boolean allFlag = false;
		Map<String, Object> map = new HashMap<String, Object>();
		
		switch (InitCmd.getCmd(param.getCmd())) {
			case all:
				allFlag = true;
			case app_version:
				String platform = HttpContext.getPlatform().toLowerCase();
				String clientVersion = HttpContext.getVersionName();
				String latestVersion = null;
				String downloadUrl = null;
				boolean isForce = false;
				boolean needUpdate = false;
				
				if (platform.contains("ios")) {
					latestVersion = ConfigProperties.getValue("member_ios_latest_version");
					downloadUrl = ConfigProperties.getValue("member_ios_download_url");
				} else if (platform.contains("android")) {
					latestVersion = ConfigProperties.getValue("member_android_latest_version");
					downloadUrl = ConfigProperties.getValue("member_android_download_url");
				}
				
				int ret  = CommonUtil.versionComparator(latestVersion, clientVersion);
				if (ret > 0) needUpdate = true;
				
				AppVersionDto appVersionDto = new AppVersionDto();
				appVersionDto.setDownloadUrl(downloadUrl);
				appVersionDto.setIsForce(isForce);
				appVersionDto.setLatestVersion(latestVersion);
				appVersionDto.setNeedUpadate(needUpdate);
				initAppResult.setAppVersionDto(appVersionDto);
				if (!allFlag) break;
			case user_profile:
				Response respTmp = new Response();
				respTmp = getUserProfile(memberId);
				System.out.println("restpTmp toString= " + respTmp.toString());
				MemberProfileResult memberProfile = (MemberProfileResult)respTmp.getData();
				map.put("user_profile", memberProfile);
				if (!allFlag) break;
			default:
				initAppResult.setExtraMap(map);
				break;
		}
		
		resp.success(initAppResult);
		return resp;
	}

	@Override
	public Long getMemberTotalCount() {
		return memberDao.selectTotalCount();
	}

	@Override
	public Long getMemberFrozenCount() {
		return memberDao.selectFrozenCount();
	}

	@Override
	public Long getMemberActiveCount(Date beginTime) {
		return memberDao.selectActiveCount(beginTime);
	}

	@Override
	public List<RegisterStatDto> getRegDailyStat() {
		return memberDao.selectRegDaily();
	}
	
	@Override
	public List<RegisterStatDto> getRegWeeklyStat() {
		return memberDao.selectRegWeekly();
	}
	
	@Override
	public List<RegisterStatDto> getRegMonthlyStat() {
		return memberDao.selectRegMonthly();
	}

	@Override
	public List<RegisterStatDto> getRegHourlyStatPeriod(Date startTime, Date endTime) {
		return memberDao.selectRegHourlyPeriod(startTime, endTime);
	}
	
	@Override 
	public Integer getRegTotalPeriod(Date startTime, Date endTime) {
		return memberDao.selectRegTotalPeriod(startTime, endTime);
	}

	@Override
	public List<RegisterStatDto> getRegDailyStatPeriod(Date startTime, Date endTime) {
			return memberDao.getRegDailyStatPeriod(startTime, endTime);
	}

	@Override
	public List<Member> getMemberListByName(String name) {
		return memberDao.selectMemberListByName(name);
	}

	@Override
	public List<Member> getMemberListByMobile(String mobile) {
		return memberDao.selectMemberListByMobile(mobile);
	}
}
