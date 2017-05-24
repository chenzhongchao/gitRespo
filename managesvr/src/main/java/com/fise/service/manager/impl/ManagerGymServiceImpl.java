package com.fise.service.manager.impl;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.lang.Math; 

import javax.annotation.Resource;
import javax.naming.spi.DirStateFactory.Result;

import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fise.base.ErrorCode;
import com.fise.base.Page;
import com.fise.base.Response;
import com.fise.dao.GymImgsDescMapper;
import com.fise.dao.GymItemMapper;
import com.fise.dao.GymMapper;
import com.fise.framework.config.ConfigProperties;
import com.fise.framework.elasticsearch.ElasticSearchManager;
import com.fise.model.dto.GymAccountDto;
import com.fise.model.dto.GymAccountSettleDto;
import com.fise.model.dto.GymAccountTotalDto;
import com.fise.model.dto.GymAddGymItemDto;
import com.fise.model.dto.GymDto;
import com.fise.model.dto.GymESIndexDto;
import com.fise.model.dto.GymImgsDescDto;
import com.fise.model.dto.GymItemDto;
import com.fise.model.dto.GymItemUpdateDto;
import com.fise.model.entity.Gym;
import com.fise.model.entity.GymAccount;
import com.fise.model.entity.GymAccountTransaction;
import com.fise.model.entity.GymImgsDesc;
import com.fise.model.entity.GymItem;
import com.fise.model.entity.MemberAccount;
import com.fise.model.param.GymAccountSettleParam;
import com.fise.model.param.GymAddParam;
import com.fise.model.param.GymLoginParam;
import com.fise.model.param.GymPasswdResetParam;
import com.fise.model.param.GymUpdateParam;
import com.fise.model.result.GymAccountSettleTotalResult;
import com.fise.model.result.GymMemberStatResult;
import com.fise.model.result.GymStatusResult;
import com.fise.model.result.MemberStatTotalResult;
import com.fise.service.account.IAccountService;
import com.fise.service.account.IAccountTransactionService;
import com.fise.service.gym.IGymService;
import com.fise.service.gymitem.IGymItemService;
import com.fise.service.manager.IManagerGymService;
import com.fise.service.member.IMemberService;
import com.fise.service.order.IOrderService;
import com.fise.utils.CommonUtil;
import com.fise.utils.Constants;
import com.fise.utils.DateUtil;
import com.fise.utils.JsonUtil;
import com.fise.utils.StringUtil;
import com.google.common.util.concurrent.Monitor.Guard;
import com.qq.jutil.ha.ContinuousFailRule;

@Service
public class ManagerGymServiceImpl implements IManagerGymService {
	@Resource
	private IAccountService accountService;
	
	@Resource
	private IGymItemService gymItemService;
	
	@Resource
	private IGymService gymService;
	
	@Resource 
	private IOrderService orderService;
	
	@Resource
	private IMemberService memberService;
	
	@Resource
	private IAccountTransactionService accountTransactionService;
	
	@Autowired
	private GymMapper gymDao;
	
	@Autowired
	private GymImgsDescMapper gymImgsDescDao;
	
	@Autowired
	private GymItemMapper gymItemDao;

	@Override
	public Response addGym(GymAddParam param) {
		Response resp = new Response();

		try {
			// 判断健身馆是否已存在
			Gym gymTmp = gymDao.getGymByGymName(param.getGymName());
			if (gymTmp != null){
				resp.failure(ErrorCode.ERROR_DB_RECORD_ALREADY_EXIST);
				return resp;
			}
			
			String plainPassword = StringUtil.makeCode(6, false);
			String password = StringUtil.md5(plainPassword);
			// 写健身馆信息表
			Gym gym = new Gym();
			gym.setGymName(param.getGymName());
			// gym.setPassword(param.getPassword());
			gym.setPassword(password);
			gym.setPlainPassword(plainPassword);
			gym.setAddress(param.getAddress());
			gym.setLatitude(param.getLatitude());
			gym.setLongitude(param.getLongitude());
			gym.setTel(param.getTel());
			gym.setStatus(0);
			gym.setMinSpendAmount(param.getMinSpendAmount());
			gym.setAvatar(param.getAvatar());
			gym.setCompanyName(param.getCompanyName());
			gym.setLegalPerson(param.getLegalPerson());
			gym.setIntroduction(param.getIntroduction());
			gym.setImgIntroductions(param.getImgIntroductions());
			
			gymDao.insertGym(gym);
			Integer gymId = gym.getGymId();
			
			Gym gymForUpdate = new Gym();
			gymForUpdate.setGymId(gymId);
			gymForUpdate.setLoginId(gymId + 10000);
			gymDao.updateGym(gymForUpdate);
			
			// 写健身馆图文详情表
			/*
			List<GymImgsDescDto> gymImgsDescDtoList = param.getImgsAndDesc(); 
			if (gymImgsDescDtoList != null) {
				for (GymImgsDescDto gymImgsDescDto : gymImgsDescDtoList) {
					GymImgsDesc gymImgsDesc = new GymImgsDesc(gymImgsDescDto);
					gymImgsDesc.setGymId(gymId);
					gymImgsDescDao.insertGymImgsDesc(gymImgsDesc);
				}
			}
			*/
			
			// 写健身项目表
			List<GymAddGymItemDto> gymItemDtoList = param.getGymItemList();
			if (gymItemDtoList != null) {
				for (GymAddGymItemDto gymItemDto : gymItemDtoList) {
					GymItem gymItem = new GymItem();
					gymItem.setItemName(gymItemDto.getGymItemName());
					gymItem.setItemPrice(gymItemDto.getGymItemPrice());
					gymItem.setGymId(gymId);
					
					gymItemDao.insertGymItem(gymItem);
				}
			}
			
			// 增加健身馆账户信息
			GymAccount account = new GymAccount();
			account.setGymId(gymId);
			account.setAvailableBalance(0L);
			account.setFrozenBalance(0L);
			accountService.createGymAccount(account);
			
			// 增加es doc
			List<Double> location = new ArrayList<Double>();
			location.add(param.getLongitude());
			location.add(param.getLatitude());
			
			GymESIndexDto esGym = new GymESIndexDto();
			esGym.setGymId(gymId);
			esGym.setGymName(param.getGymName());
			esGym.setAddress(param.getAddress());
			esGym.setAvatar(param.getAvatar());
			esGym.setMinSpendAmount(param.getMinSpendAmount());
			esGym.setLocation(location);
			
			ElasticSearchManager.getInstance().createDocument(Constants.ES_INDEX, Constants.ES_GYM_TYPE, gymId+"", esGym.toString());
			
			resp.success();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("add gym excetion: " + e.toString());
			resp.failure(ErrorCode.ERROR_DATABASE);
			
			// 显式的处理事务回滚
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resp;
	}

	@Override
	public Response setGymStatus(Integer gymId, Integer status) {
		Response resp = new Response();

		Gym gym = new Gym();
			
		gym.setGymId(gymId);
		gym.setStatus(status);
			
		gymDao.updateGym(gym);
			
		gym = gymDao.getGymById(gymId);
		GymStatusResult result = new GymStatusResult();
		result.setGymId(gymId);
		result.setStatus(gym.getStatus());
		result.setUpdateTime(gym.getUpdateTime());
			
		// 修改es
		if (status.intValue() == 0) {
			List<Double> location = new ArrayList<Double>();
			location.add(gym.getLongitude());
			location.add(gym.getLatitude());
				
			GymESIndexDto esGym = new GymESIndexDto();
			esGym.setGymId(gymId);
			esGym.setGymName(gym.getGymName());
			esGym.setAddress(gym.getAddress());
			esGym.setAvatar(gym.getAvatar());
			esGym.setMinSpendAmount(gym.getMinSpendAmount());
			esGym.setLocation(location);
			ElasticSearchManager.getInstance().createDocument(Constants.ES_INDEX, Constants.ES_GYM_TYPE, gymId+"", esGym.toString());
		}
		else {
			ElasticSearchManager.getInstance().deleteDocument(Constants.ES_INDEX, Constants.ES_GYM_TYPE, gymId+"");
		}
		resp.success(result);
		
		return resp;
	}

	@Override
	public List<GymDto> getGymList(Page<Gym> pageParam) {
		
		List<Gym> gymList = gymDao.getGymList(pageParam);
		List<GymDto> gymDtoList = new ArrayList<GymDto>(); 
		for (Gym gym : gymList) {
			GymDto gymDto = new GymDto();
			gymDto.setGymId(gym.getGymId());
			gymDto.setGymName(gym.getGymName());
			gymDto.setProvince(gym.getProvince());
			gymDto.setCity(gym.getCity());
			gymDto.setRegion(gym.getRegion());
			gymDto.setAddress(gym.getAddress());
			gymDto.setLatitude(gym.getLatitude());
			gymDto.setLongitude(gym.getLongitude());
			gymDto.setStatus(gym.getStatus());
			gymDto.setMinSpendAmount(gym.getMinSpendAmount());
			gymDto.setTel(gym.getTel());
			gymDto.setAvatar(gym.getAvatar());
			gymDto.setCompanyName(gym.getCompanyName());
			gymDto.setLegalPerson(gym.getLegalPerson());
			gymDto.setIntroduction(gym.getIntroduction());
			gymDto.setImgIntroductions(gym.getImgIntroductions());
			gymDto.setLoginId(gym.getLoginId());
			
			gymDtoList.add(gymDto);
		}
		
		return gymDtoList;
	}

	@Override
	public GymDto getGymById(Integer gymId) {
		
		Gym gym = gymDao.getGymById(gymId);
		if (gym != null) {
			GymDto gymDto = new GymDto();
			gymDto.setGymId(gym.getGymId());
			gymDto.setGymName(gym.getGymName());
			gymDto.setProvince(gym.getProvince());
			gymDto.setCity(gym.getCity());
			gymDto.setRegion(gym.getRegion());
			gymDto.setAddress(gym.getAddress());
			gymDto.setLatitude(gym.getLatitude());
			gymDto.setLongitude(gym.getLongitude());
			gymDto.setStatus(gym.getStatus());
			gymDto.setTel(gym.getTel());
			gymDto.setMinSpendAmount(gym.getMinSpendAmount());
			gymDto.setPassword(gym.getPassword());
			gymDto.setAvatar(gym.getAvatar());
			gymDto.setCompanyName(gym.getCompanyName());
			gymDto.setLegalPerson(gym.getLegalPerson());
			gymDto.setIntroduction(gym.getIntroduction());
			gymDto.setImgIntroductions(gym.getImgIntroductions());
			gymDto.setPlainPassword(gym.getPlainPassword());
			gymDto.setLoginId(gym.getLoginId());
			
			return gymDto;
		}
		
		
		return null;
	}

	@Override
	public List<GymItemDto> getGymItemByGymId(Integer gymId) {
		List<GymItem> gymItemList = gymItemService.getGymItemByGymId(gymId);
		if (gymItemList != null) {
			List<GymItemDto> gymItemDtoList = new ArrayList<GymItemDto>(); 
			for (GymItem gymItem : gymItemList) {
				GymItemDto gymItemDto = new GymItemDto();
				gymItemDto.setGymId(gymItem.getGymId());
				gymItemDto.setGymItemId(gymItem.getItemId());
				gymItemDto.setGymItemName(gymItem.getItemName());
				gymItemDto.setGymItemPrice(gymItem.getItemPrice());
				gymItemDto.setStatus(gymItem.getStatus());
				
				gymItemDtoList.add(gymItemDto);
			}
			
			return gymItemDtoList;
		}
		
		return null;
	}

	@Override
	public Response updateGym(GymUpdateParam param) {
		Response resp = new Response();
		
		Gym gym = new Gym();
		Integer gymId = param.getGymId();
		gym.setGymId(gymId);
		if (StringUtil.isNotEmpty(param.getGymName())) gym.setGymName(param.getGymName());
		if (StringUtil.isNotEmpty(param.getAvatar())) gym.setAvatar(param.getAvatar());
		if (StringUtil.isNotEmpty(param.getPassword())) gym.setPassword(param.getPassword()); 
		if (StringUtil.isNotEmpty(param.getProvince())) gym.setProvince(param.getProvince());
		if (StringUtil.isNotEmpty(param.getCity())) gym.setCity(param.getCity());
		if (StringUtil.isNotEmpty(param.getRegion())) gym.setRegion(param.getRegion());
		if (StringUtil.isNotEmpty(param.getAddress())) gym.setAddress(param.getAddress());
		if (param.getLongitude() != null) gym.setLongitude(param.getLongitude());
		if (param.getLatitude() != null) gym.setLatitude(param.getLatitude());
		if (StringUtil.isNotEmpty(param.getTel())) gym.setTel(param.getTel());
		if (StringUtil.isNotEmpty(param.getCompanyName())) gym.setCompanyName(param.getCompanyName());
		if (StringUtil.isNotEmpty(param.getLegalPerson())) gym.setLegalPerson(param.getLegalPerson());
		if (StringUtil.isNotEmpty(param.getIntroduction())) gym.setIntroduction(param.getIntroduction());
		if (param.getMinSpendAmount() != 0L) gym.setMinSpendAmount(param.getMinSpendAmount()); 
		if (StringUtil.isNotEmpty(param.getImgIntroductions())) gym.setImgIntroductions(param.getImgIntroductions());
		if (param.getStatus() != null) gym.setStatus(param.getStatus());
		gymService.updateGym(gym);
		
		List<GymItemUpdateDto> itemUpdateList = param.getGymItemUpdateDtoList();
		if (itemUpdateList != null) {
		for(GymItemUpdateDto itemUpdate : itemUpdateList) {
			if (StringUtil.isEmpty(itemUpdate.getCmd())) continue;
			String cmd = itemUpdate.getCmd();
			if (cmd.equalsIgnoreCase("U")) {
				String gymItemName = itemUpdate.getGymItemName();
				Integer gymItemId = itemUpdate.getGymItemId();
				
				GymItem gymItemQuery = new GymItem();
				gymItemQuery.setGymId(gymId);
				gymItemQuery.setStatus(0);
				List<GymItem> list = gymItemService.getGymItembyGymItemSelective(gymItemQuery);
				
				for(GymItem gymItem : list) {
					if (gymItem.getItemId().intValue() == gymItemId) continue;
					if (gymItem.getItemName().equals(gymItemName)) {
						// 显式的处理事务回滚
						TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
						resp.failure(ErrorCode.ERROR_GYM_UPDATE_DUPLICATE_GYM_NAME);
						return resp;
					}
				}

				GymItem gymItem = new GymItem();
				gymItem.setItemId(itemUpdate.getGymItemId());
				gymItem.setItemName(itemUpdate.getGymItemName());
				gymItem.setItemPrice(itemUpdate.getGymItemPrice());
				gymItemService.updateGymItem(gymItem);
				
			} else if (cmd.equalsIgnoreCase("A")) {
				String gymItemName = itemUpdate.getGymItemName();
				if (StringUtil.isNotEmpty(gymItemName)) {
					GymItem gymItemQuery = new GymItem();
					gymItemQuery.setItemName(gymItemName);
					gymItemQuery.setGymId(gymId);
					gymItemQuery.setStatus(0);
					List<GymItem> list = gymItemService.getGymItembyGymItemSelective(gymItemQuery);
					if (list != null && list.size() > 0) {
						// 显式的处理事务回滚
						TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
						resp.failure(ErrorCode.ERROR_GYM_UPDATE_DUPLICATE_GYM_NAME);
						return resp;
					}
				}
				
				GymItem gymItem = new GymItem();
				gymItem.setItemName(itemUpdate.getGymItemName());
				gymItem.setItemPrice(itemUpdate.getGymItemPrice());
				gymItem.setStatus(0);
				gymItem.setGymId(gymId);
				
				gymItemService.insertGymItem(gymItem);
			} else if (cmd.equalsIgnoreCase("D")) {
				GymItem gymItemUpdate = new GymItem();
				gymItemUpdate.setItemId(itemUpdate.getGymItemId());
				gymItemUpdate.setStatus(1);
				gymItemService.updateGymItem(gymItemUpdate);
			} else {
				String message = "更新健身馆信息--不支持的修改类型！";
				throw new RuntimeException(message);
			}
		}
		}
		
		// 修改es
		Gym gymInDb = gymService.getGymById(gymId);
		List<Double> location = new ArrayList<Double>();
		location.add(gymInDb.getLongitude());
		location.add(gymInDb.getLatitude());
	
		GymESIndexDto esGym = new GymESIndexDto();
		esGym.setGymId(gymId);
		esGym.setGymName(gymInDb.getGymName());
		esGym.setAddress(gymInDb.getAddress());
		esGym.setAvatar(gymInDb.getAvatar());
		esGym.setMinSpendAmount(gymInDb.getMinSpendAmount());
		esGym.setLocation(location);
		//ElasticSearchManager.getInstance().updateDocument(Constants.ES_INDEX, Constants.ES_GYM_TYPE, gymId+"", esGym.toString());
		ElasticSearchManager.getInstance().createDocument(Constants.ES_INDEX, Constants.ES_GYM_TYPE, gymId+"", JsonUtil.toJson(esGym));
		
		resp.success();
		return resp;
	}

	@Override
	public List<GymAccountDto> getGymAccountList(Page<Gym> pageParam) {
		List<GymAccountDto> result = new ArrayList<GymAccountDto>();
		
		List<Gym> gymList = gymService.getGymList(pageParam);
		for (Gym gym : gymList) {
			GymAccountDto gymAccountDto = new GymAccountDto();
			
			GymAccount account = accountService.getGymAccountByGymId(gym.getGymId());
			gymAccountDto.setGymId(gym.getGymId());
			gymAccountDto.setGymName(gym.getGymName());
			gymAccountDto.setWaitBalance(account.getWaitBalance());
			gymAccountDto.setAvailableBalance(account.getAvailableBalance());
			gymAccountDto.setFrozenBalance(account.getFrozenBalance());
			gymAccountDto.setTotalBalance(account.getAvailableBalance() + account.getFrozenBalance() + account.getWaitBalance());
			gymAccountDto.setLoginId(gym.getLoginId());
			
			result.add(gymAccountDto);
		}
		
		return result;
	}

	@Override
	public List<GymAccountSettleDto> getGymAccountSettleList(Page<GymAccount> pageParam) {
		List<GymAccountSettleDto> result = new ArrayList<GymAccountSettleDto>();
		
		List<GymAccount> gymAccountList = null;
		if (pageParam.getExtraParam() != null && StringUtil.isNotEmpty((String)pageParam.getExtraParam().get("gym_name"))) {
			gymAccountList = accountService.getGymAccountListByGymName(pageParam);
		} else {
			gymAccountList = accountService.getGymAccountList(pageParam);
		}
		
		Integer serviceChargeRate = Integer.parseInt(ConfigProperties.getValue("gym_account_settle_service_rate"));
		
		for(GymAccount account : gymAccountList) {
			GymAccountSettleDto gymAccountSettleDto = new GymAccountSettleDto();
				
			Gym gym = gymService.getGymById(account.getGymId());
			
			Long waitBalance = account.getWaitBalance();
			Long serviceCharge = waitBalance * serviceChargeRate / 100;
			Long actualSettleAmount = waitBalance - serviceCharge;
			gymAccountSettleDto.setGymId(account.getGymId());
			gymAccountSettleDto.setGymName(gym.getGymName());
			gymAccountSettleDto.setWaitBalance(waitBalance);
			gymAccountSettleDto.setServiceChargeRate(serviceChargeRate);
			gymAccountSettleDto.setActualSettleBalance(actualSettleAmount);
			gymAccountSettleDto.setLoginId(gym.getLoginId());
				
			result.add(gymAccountSettleDto);
		}
		return result;
	}

	@Override
	public Response getGymAccountSettleTotal() {
		Response resp = new Response();
		
		Integer serviceChargeRate = Integer.parseInt(ConfigProperties.getValue("gym_account_settle_service_rate"));
		GymAccountTotalDto totalAccount = accountService.getGymAccountTotal();
		Long totalWaitBalance = totalAccount.getTotalWaitBalance();
		Long totalServiceCharge = totalWaitBalance * serviceChargeRate / 100;
		Long totalActualSettle = totalWaitBalance - totalServiceCharge;
		
		GymAccountSettleTotalResult result = new GymAccountSettleTotalResult();
		result.setTotalActualSettle(totalActualSettle);
		result.setTotalServiceCharge(totalServiceCharge);
		result.setTotalWaitBalance(totalWaitBalance);
		resp.success(result);
		
		return resp;
	}

	@Override
	public Response settleGymAccount(GymAccountSettleParam param) {
		Response resp = new Response();
		
		Integer gymId = param.getGymId();
		Long wilSettleAmount = param.getSettleAmount();
		
		if (wilSettleAmount.longValue() <= 0 ) {
			resp.failure(ErrorCode.ERROR_PARAMETER_BUSINESS_CHECK_ERROR);
			return resp;
		}
		
		Gym gym = gymService.getGymById(gymId);
		if (gym == null) {
			resp.failure(ErrorCode.ERROR_GYM_INDB_IS_NULL);
			return resp;
		}
		
		GymAccount account = accountService.getGymAccountByGymId(gymId);
		Long waitBalance = account.getWaitBalance();
		Long settledBalance = account.getSettledBalance();
		Long availableBalance = account.getAvailableBalance();
		Long gymPreAvailableBalance = availableBalance;
		if (wilSettleAmount > waitBalance) {
			resp.failure(ErrorCode.ERROR_GYM_ACCOUNT_SETTLEAMOUNT_GT_WAITBALANCE);
			return resp;
		}
		waitBalance -= wilSettleAmount;
		settledBalance += wilSettleAmount;
		availableBalance += wilSettleAmount;
		Long gymPostAvailableBalance = availableBalance;
		
		// 写账户表
		GymAccount accountForUpdate = new GymAccount();
		accountForUpdate.setAccountId(account.getAccountId());
		accountForUpdate.setWaitBalance(waitBalance);
		accountForUpdate.setSettledBalance(settledBalance);
		accountForUpdate.setAvailableBalance(availableBalance);
		accountService.updateGymAccountBalance(accountForUpdate);
		
		// 写账户交易流水表
		GymAccountTransaction gymAccountTransaction = new GymAccountTransaction(); 
		gymAccountTransaction.setTransNo(CommonUtil.getTransactionNo(gym));
		gymAccountTransaction.setGymId(gym.getGymId());
		gymAccountTransaction.setOperateType(Constants.GYM_ACCOUNT_TRANSACTION_OPERATE_TYPE_SYSTEM_SETTLE);
		gymAccountTransaction.setAmount(wilSettleAmount);
		gymAccountTransaction.setPreBalance(gymPreAvailableBalance);
		gymAccountTransaction.setPostBalance(gymPostAvailableBalance);
		accountTransactionService.insertGymAccountTransaction(gymAccountTransaction);
		
		resp.success();
		return resp;
	}

	@Override
	public Response getGymAccountTotal() {
		Response resp = new Response();
		
		GymAccountTotalDto totalAccount = accountService.getGymAccountTotal();
		Long totalBalance = totalAccount.getTotalWaitBalance() + totalAccount.getTotalSettledBalance();
		totalAccount.setTotalAvailableBalance(null);
		totalAccount.setTotalFrozenBalance(null);
		totalAccount.setTotalBalance(totalBalance);
		resp.success(totalAccount);
		
		return resp;
	}

	@Override
	public Response gymMemberStatList(Page<Gym> pageParam) {
		Response resp = new Response();
		
		Date beginTime = DateUtil.getMonthBeginDate();
		List<Gym> gymList = gymService.getGymList(pageParam);
		List<GymMemberStatResult> resultList = new ArrayList<GymMemberStatResult>();
		for (Gym gym : gymList) {
			GymMemberStatResult result = new GymMemberStatResult();
			Long memberTotalCount = orderService.getGymMemberCount(gym.getGymId());
			Long memberActiveCount = orderService.getGymActiveMemberCount(gym.getGymId(), beginTime);
			Long memberFrozenCount = orderService.getGymFrozenMemberCount(gym.getGymId());
			
			result.setMemberTotalCount(memberTotalCount);
			result.setMemberActiveCount(memberActiveCount);
			result.setMemberFrozenCount(memberFrozenCount);
			result.setGymId(gym.getGymId());
			result.setGymName(gym.getGymName());
			result.setLoginId(gym.getLoginId());
			
			resultList.add(result);
		}
		resp.success(resultList);
		
		return resp;
	}

	@Override
	public Response getMemberStatTotal() {
		Response resp = new Response();
		
		Date beginTime = DateUtil.getMonthBeginDate();	// 月活
		Long memberTotal = memberService.getMemberTotalCount();
		Long memberActive = memberService.getMemberActiveCount(beginTime);
		Long memberFrozen = memberService.getMemberFrozenCount();
		
		// 返回
		MemberStatTotalResult result = new MemberStatTotalResult();
		result.setMemberTotalCount(memberTotal);
		result.setMemberActiveCount(memberActive);
		result.setMemberFrozenCount(memberFrozen);
		resp.success(result);
		
		return resp;
	}

	@Override
	public Response resetGymPasswd(GymPasswdResetParam param) {
		Response resp = new Response();
		
		Gym gym = gymService.getGymById(param.getGymId());
		if (gym == null) {
			resp.failure(ErrorCode.ERROR_GYM_INDB_IS_NULL);
			return resp;
		}
		
		if (!gym.getPassword().equals(param.getOldPassword())) {
			resp.failure(ErrorCode.ERROR_PASSWORD_INCORRECT);
			return resp;
		}
		
		Gym gymForUpdate = new Gym();
		gymForUpdate.setGymId(gym.getGymId());
		gymForUpdate.setPlainPassword(param.getNewPlainPassword());
		gymForUpdate.setPassword(param.getNewPassword());
		gymService.updateGym(gymForUpdate);
		
		return resp;
	}
}
