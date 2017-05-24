package com.fise.service.account.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fise.dao.GymAccountTransactionMapper;
import com.fise.dao.MemberAccountTransactionMapper;
import com.fise.model.dto.ChargeStatDto;
import com.fise.model.entity.GymAccountTransaction;
import com.fise.model.entity.MemberAccountTransaction;
import com.fise.service.account.IAccountTransactionService;

@Service
public class AccountTransactionServiceImpl implements IAccountTransactionService {
	@Autowired
	private MemberAccountTransactionMapper memberAccountTransactionDao;
	
	@Autowired
	private GymAccountTransactionMapper gymAccountTransactionDao;

	@Override
	public void insertMemberAccountTransaction(MemberAccountTransaction memberAccountTransaction) {
		memberAccountTransactionDao.insertMemberAccountTransaction(memberAccountTransaction);
		return ;
	}

	@Override
	public void insertGymAccountTransaction(GymAccountTransaction gymAccountTransaction) {
		gymAccountTransactionDao.insertGymAccountTransaction(gymAccountTransaction);
		return ;
	}

	@Override
	public MemberAccountTransaction getMemberAccountTransactionByTransNo(String transNo) {
		return memberAccountTransactionDao.selectByTransNoUniKey(transNo);
	}

	@Override
	public void updateMemberAccountTransaction(MemberAccountTransaction memberAccountTransaction) {
		memberAccountTransactionDao.updateByPrimaryKeySelective(memberAccountTransaction);
		return ;
	}

	@Override
	public List<ChargeStatDto> getChargeDailyStat() {
		return memberAccountTransactionDao.selectChargeStatDaily();
	}

	@Override
	public List<ChargeStatDto> getChargeWeeklyStat() {
		return memberAccountTransactionDao.selectChargeStatWeekly();
	}

	@Override
	public List<ChargeStatDto> getChargeMonthlyStat() {
		return memberAccountTransactionDao.selectChargeStatMonthly();
	}

	@Override
	public List<ChargeStatDto> getCostDailyStat() {
		return memberAccountTransactionDao.selectCostStatDaily();
	}

	@Override
	public List<ChargeStatDto> getCostWeeklyStat() {
		return memberAccountTransactionDao.selectCostStatWeekly();
	}

	@Override
	public List<ChargeStatDto> getCostMonthlyStat() {
		return memberAccountTransactionDao.selectCostStatMonthly();
	}

	@Override
	public List<ChargeStatDto> getChargeHourlyStatPeriod(Date startTime, Date endTime) {
		return memberAccountTransactionDao.selectChargeStatHourlyPeriod(startTime, endTime);
	}
	
	@Override
	public List<ChargeStatDto> getCostHourlyStatPeriod(Date startTime, Date endTime) {
		return memberAccountTransactionDao.selectCostStatHourlyPeriod(startTime, endTime);
	}
	
	@Override
	public List<ChargeStatDto> getChargeDailyStatPeriod(Date startTime, Date endTime) {
		return memberAccountTransactionDao.selectChargeStatDailyPeriod(startTime, endTime);
	}
	
	@Override
	public List<ChargeStatDto> getCostDailyStatPeriod(Date startTime, Date endTime) {
		return memberAccountTransactionDao.selectCostStatDailyPeriod(startTime, endTime);
	}

	@Override
	public Long getChargeTotalPeriod(Date startTime, Date endTime) {
		return memberAccountTransactionDao.selectChargeTotalPeriod(startTime, endTime);
	}

	@Override
	public Long getCostTotalPeriod(Date startTime, Date endTime) {
		return memberAccountTransactionDao.selectCostTotalPeriod(startTime, endTime);
	}
}
