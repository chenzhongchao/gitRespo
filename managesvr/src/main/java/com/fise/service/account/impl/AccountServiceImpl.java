package com.fise.service.account.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fise.base.ErrorCode;
import com.fise.base.Page;
import com.fise.base.Response;
import com.fise.dao.GymAccountMapper;
import com.fise.dao.MemberAccountMapper;
import com.fise.framework.redis.RedisManager;
import com.fise.model.dto.GymAccountTotalDto;
import com.fise.model.entity.GymAccount;
import com.fise.model.entity.Member;
import com.fise.model.entity.MemberAccount;
import com.fise.model.param.AccountPasswdResetParam;
import com.fise.service.account.IAccountService;
import com.fise.service.member.IMemberService;
import com.fise.utils.Constants;
import com.fise.utils.StringUtil;

import redis.clients.jedis.Jedis;

@Service
public class AccountServiceImpl implements IAccountService {
	@Autowired
	private MemberAccountMapper memberAccountDao;
	
	@Autowired
	private GymAccountMapper gymAccountDao;
	
	@Resource
	private IMemberService memberService;
	
	@Override
	public void createMemberAccount(MemberAccount account) {
		
		memberAccountDao.insertMemberAccount(account);
		
		return;
	}

	@Override
	public MemberAccount getMemberAccountByMemberId(Integer memberId) {
		return memberAccountDao.selectMemberAccountByMemberId(memberId);
	}

	@Override
	public void updateMemberAccountBalance(MemberAccount memberAccount) {
		memberAccountDao.updateMemberAccountBalance(memberAccount);
		return ;
	}

	@Override
	public void createGymAccount(GymAccount gymAccount) {
		gymAccountDao.insertGymAccount(gymAccount);

		return ;
	}

	@Override
	public GymAccount getGymAccountByGymId(Integer gymId) {
		return gymAccountDao.selectGymAccountByGymId(gymId);
	}

	@Override
	public void updateGymAccountBalance(GymAccount gymAccount) {
		gymAccountDao.updateGymAccountBalance(gymAccount);
		
		return ;
	}

	@Override
	public List<GymAccount> getGymAccountList(Page<GymAccount> page) {
		return gymAccountDao.selectGymAccountPage(page);
	}
	
	@Override
	public List<GymAccount> getGymAccountListByGymName(Page<GymAccount> page) {
		return gymAccountDao.selectGymAccountPageByGymName(page);
	}

	@Override
	public Response resetMemberAccountPasswd(Integer memberId, AccountPasswdResetParam param) {
		Response resp = new Response();
		
		String cmd = param.getCmd();
		if (cmd.equalsIgnoreCase("reset")) {
			Member member = memberService.getMemberById(memberId);
			if (member == null) {
				resp.failure(ErrorCode.ERROR_MEMBER_INDB_IS_NULL);
				return resp;
			}
			
			MemberAccount account = memberAccountDao.selectMemberAccountByMemberId(memberId);
			if (account == null) {
				resp.failure(ErrorCode.ERROR_MEMBER_ACCOUNT_INDB_IS_NULL);
				return resp;
			}
			
			// 老密码校验
			if (StringUtil.isEmpty(account.getPassword()) || StringUtil.isEmpty(param.getOldPassword()) || !param.getOldPassword().equals(account.getPassword())) {
				resp.failure(ErrorCode.ERROR_PASSWORD_INCORRECT);
				return resp;
			} 
			
			if (checkVerifyCode(member.getMobile(), param.getSmsCode()) == false) {
				resp.failure(ErrorCode.ERROR_MEMBER_REGISTER_VERIFY_CODE_ERROR);
				return resp;
			}
			
			MemberAccount accountForUpdate = new MemberAccount();
			accountForUpdate.setAccountId(account.getAccountId());
			accountForUpdate.setPassword(param.getNewPassword());
			memberAccountDao.updateByPrimaryKeySelective(accountForUpdate);
		} else if (cmd.equalsIgnoreCase("newset")) {
			MemberAccount account = memberAccountDao.selectMemberAccountByMemberId(memberId);
			if (account == null) {
				resp.failure(ErrorCode.ERROR_MEMBER_ACCOUNT_INDB_IS_NULL);
				return resp;
			}
			
			// 注释原因： 忘记支付密码，需要重置
			/*
			if (StringUtil.isNotEmpty(account.getPassword())) {
				resp.failure(ErrorCode.ERROR_DB_RECORD_ALREADY_EXIST);
				return resp;
			}
			*/
			
			MemberAccount accountForUpdate = new MemberAccount();
			accountForUpdate.setAccountId(account.getAccountId());
			accountForUpdate.setPassword(param.getNewPassword());
			memberAccountDao.updateByPrimaryKeySelective(accountForUpdate);
		} else {
			resp.failure(ErrorCode.ERROR_PARAMETER_BUSINESS_CHECK_ERROR);
			return resp;
		} 
		
		resp.success();
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

	@Override
	public GymAccountTotalDto getGymAccountTotal() {
		return gymAccountDao.selectTotalWaitBalance();
	}
}
