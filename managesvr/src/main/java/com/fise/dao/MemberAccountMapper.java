package com.fise.dao;

import java.util.List;

import com.fise.model.entity.MemberAccount;


public interface MemberAccountMapper {
	// 新建用户账户
	public void insertMemberAccount(MemberAccount memberAccount);
    
	// 根据memberId获取用户账户
	public MemberAccount selectMemberAccountByMemberId(Integer memberId);
	
	// 更新MemberAccount余额字段
	public void updateMemberAccountBalance(MemberAccount memberAccount);
	
	// 选择更新memberAccount
	public void updateByPrimaryKeySelective(MemberAccount memberAccount);
}