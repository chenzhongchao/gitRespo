package com.fise.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.fise.model.dto.ChargeStatDto;
import com.fise.model.entity.MemberAccountTransaction;

public interface MemberAccountTransactionMapper {
	// 新增用户账户交易流水
	public void insertMemberAccountTransaction(MemberAccountTransaction memberAccountTransaction);
	
	// 根据交易流水号查交易
	public MemberAccountTransaction selectByTransNoUniKey(String transNo);
	
	// 更新用户账户交易流水
	public void updateByPrimaryKeySelective(MemberAccountTransaction memberAccountTransaction);
	
	// 获取每日充值统计
	public List<ChargeStatDto> selectChargeStatDaily();
	
	// 获取每周充值统计
	public List<ChargeStatDto> selectChargeStatWeekly();
	
	// 获取每月充值统计
	public List<ChargeStatDto> selectChargeStatMonthly();
	
	// 获取每日消费统计
	public List<ChargeStatDto> selectCostStatDaily();
	
	// 获取每周消费统计
	public List<ChargeStatDto> selectCostStatWeekly();
	
	// 获取每月消费统计
	public List<ChargeStatDto> selectCostStatMonthly();
	
	// 获取时间区间内充值分时统计
	public List<ChargeStatDto> selectChargeStatHourlyPeriod(@Param("startTime") Date startTime, @Param("endTime") Date endTime);
	
	// 获取时间区间内充值按天统计
	public List<ChargeStatDto> selectChargeStatDailyPeriod(@Param("startTime") Date startTime, @Param("endTime") Date endTime);
	
	// 获取时间区间内消耗分时统计
	public List<ChargeStatDto> selectCostStatHourlyPeriod(@Param("startTime") Date startTime, @Param("endTime") Date endTime);
	
	// 获取时间区间内消耗按天统计
	public List<ChargeStatDto> selectCostStatDailyPeriod(@Param("startTime") Date startTime, @Param("endTime") Date endTime);
	
	// 获取时间区间内充值总数
	public Long selectChargeTotalPeriod(@Param("startTime") Date startTime, @Param("endTime") Date endTime);
	
	// 获取时间区间内消费总数
	public Long selectCostTotalPeriod(@Param("startTime") Date startTime, @Param("endTime") Date endTime);
}