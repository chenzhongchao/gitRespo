package com.fise.service.account;

import java.util.List;

import com.fise.base.Page;
import com.fise.base.Response;
import com.fise.model.dto.GymAccountTotalDto;
import com.fise.model.entity.GymAccount;
import com.fise.model.entity.MemberAccount;
import com.fise.model.param.AccountPasswdResetParam;

public interface IAccountService {
	// 新增用户账户
	public void createMemberAccount(MemberAccount account);
	
	// 新增商家账户
	public void createGymAccount(GymAccount account);
	
	// 通过memberId查询MemberAccount
	public MemberAccount getMemberAccountByMemberId(Integer memberId);
	
	// 更新MemberAccount
	public void updateMemberAccountBalance(MemberAccount memberAccount);	
	
	// 通过gymId查询GymAccount
	public GymAccount getGymAccountByGymId(Integer gymId);
	
	// 更新MemberAccount
	public void updateGymAccountBalance(GymAccount gymAccount);
	
	// 获取商户账户列表
	public List<GymAccount> getGymAccountList(Page<GymAccount> page);
	
	// 重置用户账户密码
	public Response resetMemberAccountPasswd(Integer memberId, AccountPasswdResetParam param);
	
	// 获取商户服务费结算总揽
	public GymAccountTotalDto getGymAccountTotal();

	// 获取商户账户列表(通过商户名模糊搜索)
	public List<GymAccount> getGymAccountListByGymName(Page<GymAccount> page);
}
