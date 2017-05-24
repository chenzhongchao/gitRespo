package com.fise.service.account;

import java.util.Date;
import java.util.List;

import com.fise.model.dto.ChargeStatDto;
import com.fise.model.entity.GymAccountTransaction;
import com.fise.model.entity.MemberAccountTransaction;

public interface IAccountTransactionService {
	// 新增用户账户交易流水
	public void insertMemberAccountTransaction(MemberAccountTransaction memberAccountTransaction);
	
	// 新增商户账户交易流水
	public void insertGymAccountTransaction(GymAccountTransaction gymAccountTransaction);
	
	// 查询用户账户交易流水
	public MemberAccountTransaction getMemberAccountTransactionByTransNo(String transNo);
	
	// 更新用户交易流水
	public void updateMemberAccountTransaction(MemberAccountTransaction memberAccountTransaction); 
	
	// 获取每日充值统计
	public List<ChargeStatDto> getChargeDailyStat();

	// 获取每周充值统计
	public List<ChargeStatDto> getChargeWeeklyStat();

	// 获取每月充值统计
	public List<ChargeStatDto> getChargeMonthlyStat();

	// 获取每日消耗统计
	public List<ChargeStatDto> getCostDailyStat();

	// 获取每周消耗统计
	public List<ChargeStatDto> getCostWeeklyStat();

	// 获取每月消耗统计
	public List<ChargeStatDto> getCostMonthlyStat();
	
	// 获取时间区间内分时充值统计
	public List<ChargeStatDto> getChargeHourlyStatPeriod(Date startTime, Date endTime);

	// 获取时间区间内分时消费统计
	public List<ChargeStatDto> getCostHourlyStatPeriod(Date startTime, Date endTime);

	// 获取时间区间内按天充值统计
	public List<ChargeStatDto> getChargeDailyStatPeriod(Date startTime, Date endTime);

	// 获取时间区间内按天消费统计
	public List<ChargeStatDto> getCostDailyStatPeriod(Date startTime, Date endTime);
	
	// 获取时间区间内充值总数
	public Long getChargeTotalPeriod(Date startTime, Date endTime);
	
	// 获取时间区间内消费总数
	public Long getCostTotalPeriod(Date startTime, Date endTime);
}
