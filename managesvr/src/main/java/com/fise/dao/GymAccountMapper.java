package com.fise.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.fise.base.Page;
import com.fise.model.dto.GymAccountTotalDto;
import com.fise.model.entity.GymAccount;

public interface GymAccountMapper {
	// 新建用户账户
	public void insertGymAccount(GymAccount gymAccount);
    
	// 根据gymId获取商户账户
	public GymAccount selectGymAccountByGymId(Integer gymId);
	
	// 更新GymAccount
	public void updateGymAccountBalance(GymAccount gymAccount);
	
	// 获取商户账户列表
	public List<GymAccount> selectGymAccountPage(@Param("page")Page<GymAccount> page);
	
	// 获取商户账户列表(通过商户名模糊查询)
	public List<GymAccount> selectGymAccountPageByGymName(@Param("page")Page<GymAccount> page);
	
	// 获取商户服务费结算总揽
	public GymAccountTotalDto selectTotalWaitBalance();
}