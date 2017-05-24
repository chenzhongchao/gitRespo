package com.fise.service.manager.impl;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.xml.crypto.Data;

import org.omg.CORBA.PRIVATE_MEMBER;
import org.springframework.util.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fise.base.ErrorCode;
import com.fise.base.Response;
import com.fise.dao.ManagerMapper;
import com.fise.dao.MemberMapper;
import com.fise.framework.redis.RedisManager;
import com.fise.model.dto.ChargeStatDto;
import com.fise.model.dto.RegisterStatDto;
import com.fise.model.entity.Manager;
import com.fise.model.entity.Member;
import com.fise.model.param.AppStatParam;
import com.fise.model.param.ManagerLoginParam;
import com.fise.model.param.ManagerPasswdResetParam;
import com.fise.model.result.AppStatResult;
import com.fise.model.result.ManagerLoginResult;
import com.fise.model.result.RegisterResult;
import com.fise.service.account.IAccountTransactionService;
import com.fise.service.manager.IManagerService;
import com.fise.service.member.IMemberService;
import com.fise.utils.CommonUtil;
import com.fise.utils.Constants;
import com.fise.utils.DateUtil;
import com.fise.utils.StringUtil;
import com.qq.jutil.j4log.Logger;

import redis.clients.jedis.Jedis;

@Service
public class ManagerServiceImpl implements IManagerService {
	@Autowired
	private ManagerMapper managerDao;
	
	@Resource
	private IMemberService memberService;
	
	@Resource
	private IAccountTransactionService accountTransactionService;

	// 用户登录
	@Override
	public Response login(ManagerLoginParam param) {
		Response resp = new Response();

		String userName = param.getUserName();
		String password = param.getPassword();
		
		Manager managerInDB = new Manager();
		managerInDB = managerDao.getManagerByUserName(userName);
		
		if (managerInDB == null) {
			resp.failure(ErrorCode.ERROR_MEMBER_INDB_IS_NULL);
			return resp;
		}
		
		if (!password.equalsIgnoreCase(managerInDB.getPassword())) {
			resp.failure(ErrorCode.ERROR_PASSWORD_INCORRECT);
			return resp;
		}
				
		Manager managerForUpdate = new Manager();
		
		Integer managerId = managerInDB.getManagerId();
		String accessToken = genAccessToken(managerId);
		
		managerForUpdate.setLastLogin(new Date());
		managerForUpdate.setManagerId(managerId);
		managerDao.updateManager(managerForUpdate);
				
		ManagerLoginResult result = new ManagerLoginResult();
		result.setManagerId(managerId);
		result.setUserName(managerInDB.getUserName());
		result.setLastLogin(managerForUpdate.getLastLogin());
		result.setAccessToken(accessToken);
		resp.success(result);
		
		return resp;
	}

	// 用户退出
	@Override
	public Response logout(Integer managerId, String accessToken) {
		Response resp = new Response();;
		
		try {			
			
			delAccessToken(managerId, accessToken);
			
			resp.success();
		} catch (Exception e) {
			System.out.println("logout, updateMemberError: " + e.getMessage());
			
			resp.failure(ErrorCode.ERROR_DATABASE);
		}
		
		
		return resp;
	}
	
	// 生成access token
	private String genAccessToken(Integer managerId) {
		Jedis jedis = null;
		String accessToken = null;
		try {
			jedis = RedisManager.getInstance().getResource(Constants.REDIS_POOL_NAME_SYSTEM);
			accessToken = CommonUtil.getAccessToken();
			String key = Constants.REDIS_KEY_PREFIX_MANAGER_ACCESS_TOKEN + accessToken;
			String value = managerId + "";
			String ret = jedis.setex(key, Constants.ACCESS_TOKEN_EXPIRE_SECONDS, value);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			RedisManager.getInstance().returnResource(Constants.REDIS_POOL_NAME_SYSTEM, jedis);
		}
		
		return accessToken;
	}

	// 删除access token 
	private boolean delAccessToken(Integer managerId, String accessToken) {
		boolean ret = false;
		Jedis jedis = null;
		try {
			
			jedis = RedisManager.getInstance().getResource(Constants.REDIS_POOL_NAME_SYSTEM);
			
			String key = Constants.REDIS_KEY_PREFIX_MANAGER_ACCESS_TOKEN + accessToken;
			
			String managerIdInRedis = jedis.get(key);
			if (!managerId.toString().equals(managerIdInRedis)) return false;
			jedis.del(key);
			ret = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			RedisManager.getInstance().returnResource(Constants.REDIS_POOL_NAME_SYSTEM, jedis);
		}
		
		return ret;
	}

	@Override
	public Response getAppStat(AppStatParam param) {
		Response resp = new Response();
		
		String cmd= param.getCmd();
		List<RegisterStatDto> regStatList = null;
		List<ChargeStatDto> chargeStatList = null;
		List<ChargeStatDto> costStatList = null;
		if (cmd.equalsIgnoreCase("daily")) {
			regStatList = memberService.getRegDailyStat();
			chargeStatList = accountTransactionService.getChargeDailyStat();
			costStatList = accountTransactionService.getCostDailyStat();
		} else if (cmd.equalsIgnoreCase("weekly")) {
			regStatList = memberService.getRegWeeklyStat();
			chargeStatList = accountTransactionService.getChargeWeeklyStat();
			costStatList = accountTransactionService.getCostWeeklyStat();
		} else if (cmd.equalsIgnoreCase("monthly")) {
			regStatList = memberService.getRegMonthlyStat();
			chargeStatList = accountTransactionService.getChargeMonthlyStat();
			costStatList = accountTransactionService.getCostMonthlyStat();
		} else {
			resp.failure(ErrorCode.ERROR_PARAMETER_BUSINESS_CHECK_ERROR);
			return resp;
		}
		
		AppStatResult result = new AppStatResult();
		result.setRegStat(regStatList);
		result.setChargeStat(chargeStatList);
		result.setCostStat(costStatList);
		resp.success(result);
		
		return resp;
	}

	@Override
	public Response getAppStatDetail(AppStatParam param) {
		Response resp = new Response();
		Logger logger = Logger.getLogger("app_stat_detail");
		
		Integer statType = param.getStatType();
		Date startTime = param.getStartTime();
		Date endTime = param.getEndTime();
		Date compareStartTime = param.getCompareStartTime();
		Date compareEndTime = param.getCompareEndTime();
		
		Long interval = 0L;
		List<RegisterStatDto> regStatPeriodList = null;
		List<ChargeStatDto> chargeStatPeriodList = null;
		List<ChargeStatDto> costStatPeriodList = null;
		Integer regPeriodCount = 0;
		Long chargePeriodCount = 0L;
		Long costPeriodCount = 0L;
		
		if (statType.intValue() == 0)
		{
			interval = 3600000L;		// 每小时的时间间隔（单位：毫秒）
			regStatPeriodList = memberService.getRegHourlyStatPeriod(startTime, endTime);
			chargeStatPeriodList = accountTransactionService.getChargeHourlyStatPeriod(startTime, endTime); 
			costStatPeriodList = accountTransactionService.getCostHourlyStatPeriod(startTime, endTime); 
		} else if (statType.intValue() == 1 || statType.intValue() == 2) {
			interval = 86400000L;		// 每天的时间间隔（单位：毫秒）
			regStatPeriodList = memberService.getRegDailyStatPeriod(startTime, endTime);
			chargeStatPeriodList = accountTransactionService.getChargeDailyStatPeriod(startTime, endTime); 
			costStatPeriodList = accountTransactionService.getCostDailyStatPeriod(startTime, endTime); 
		} else {
			resp.failure(ErrorCode.ERROR_PARAMETER_BUSINESS_CHECK_ERROR);
			return resp;
		}
		regPeriodCount = memberService.getRegTotalPeriod(compareStartTime, compareEndTime);
		chargePeriodCount = accountTransactionService.getChargeTotalPeriod(compareStartTime, compareEndTime);
		costPeriodCount = accountTransactionService.getCostTotalPeriod(compareStartTime, compareEndTime);
		
		// 处理输出格式
		List<RegisterStatDto> regStatPeriodFullList = new ArrayList<RegisterStatDto>();
		List<ChargeStatDto> chargeStatPeriodFullList = new ArrayList<ChargeStatDto>();
		List<ChargeStatDto> costStatPeriodFullList = new ArrayList<ChargeStatDto>();
		Map<String,Integer> regStatPeriodMap = new HashMap<String, Integer>();
		Map<String,Long> chargeStatPeriodMap = new HashMap<String, Long>();
		Map<String,Long> costStatPeriodMap = new HashMap<String, Long>();
		
		DateFormat hourFormat = new SimpleDateFormat("yyyy-MM-dd HH");
		DateFormat dayFormat = new SimpleDateFormat("yyyy-MM-dd");
		Long startIdx = startTime.getTime();
		Long endIdx = endTime.getTime();
		
		for (RegisterStatDto registerStatDto : regStatPeriodList) {
			regStatPeriodMap.put(registerStatDto.getPeriod(), registerStatDto.getCount());
		}
		
		for (ChargeStatDto chargeStatDto : chargeStatPeriodList) {
			chargeStatPeriodMap.put(chargeStatDto.getPeriod(), chargeStatDto.getAmount());
		}
		
		for (ChargeStatDto chargeStatDto : costStatPeriodList) {
			costStatPeriodMap.put(chargeStatDto.getPeriod(), chargeStatDto.getAmount());
		}
		
		for (Long i = startIdx; i < endIdx; i += interval) {
			String key = null;
			String period = null;
			if (statType.intValue() == 0) {
				key = hourFormat.format(i);
				String[] feilds = key.split(" ");
				period = feilds[1];
			} else {
				key = dayFormat.format(i);
				String[] feilds = key.split("-");
				period = feilds[1]+"/"+feilds[2];
			}
			
			Integer value = 0;
			value = regStatPeriodMap.get(key) != null ? regStatPeriodMap.get(key) : 0;
			RegisterStatDto registerStatDto = new RegisterStatDto();
			registerStatDto.setPeriod(period);
			registerStatDto.setCount(value);
			
			logger.debug("i = " + i + " key=" + key + " value=" + value);
			
			Long amountValue = 0L;
			amountValue = chargeStatPeriodMap.get(key) != null ? chargeStatPeriodMap.get(key) : 0;
			ChargeStatDto chargeStatDto = new ChargeStatDto();
			chargeStatDto.setPeriod(period);
			chargeStatDto.setAmount(amountValue);
			
			amountValue = costStatPeriodMap.get(key) != null ? costStatPeriodMap.get(key) : 0;
			ChargeStatDto costStatDto = new ChargeStatDto();
			costStatDto.setPeriod(period);
			costStatDto.setAmount(amountValue);
			
			regStatPeriodFullList.add(registerStatDto);
			chargeStatPeriodFullList.add(chargeStatDto);
			costStatPeriodFullList.add(costStatDto);	
		}
		
		AppStatResult result = new AppStatResult();
		result.setRegStat(regStatPeriodFullList);
		result.setChargeStat(chargeStatPeriodFullList);
		result.setCostStat(costStatPeriodFullList);
		result.setRegTotalCount(regPeriodCount);
		result.setChargeTotalAmount(chargePeriodCount);
		result.setCostTotalAmount(costPeriodCount);
		
		resp.success(result);
		return resp;
	}

	@Override
	public Response resetPasswd(ManagerPasswdResetParam param) {
		Response resp = new Response();
		
		Manager manager = managerDao.getManagerById(param.getManagerId());
		if (manager == null) {
			resp.failure(ErrorCode.ERROR_MANAGER_INDB_IS_NULL);
			return resp;
		}
		
		// 验证老密码
		if (!manager.getPassword().equals(param.getOldPassword())) {
			resp.failure(ErrorCode.ERROR_PASSWORD_INCORRECT);
			return resp;
		}
		
		Manager managerForUpdate = new Manager();
		managerForUpdate.setManagerId(manager.getManagerId());
		managerForUpdate.setPassword(param.getNewPassword());
		managerDao.updateManager(managerForUpdate);
		resp.success();
		
		return resp;
	}
}
