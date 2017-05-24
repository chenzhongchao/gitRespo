package com.fise.service.gym.impl;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fise.base.ErrorCode;
import com.fise.base.HttpContext;
import com.fise.base.Page;
import com.fise.base.Response;
import com.fise.dao.GymItemMapper;
import com.fise.dao.GymMapper;
import com.fise.dao.SuggestionMapper;
import com.fise.framework.config.ConfigProperties;
import com.fise.framework.elasticsearch.ElasticSearchManager;
import com.fise.framework.redis.RedisManager;
import com.fise.model.dto.AppVersionDto;
import com.fise.model.dto.OrderGymItemDto;
import com.fise.model.entity.Gym;
import com.fise.model.entity.GymItem;
import com.fise.model.entity.Member;
import com.fise.model.entity.MemberAccount;
import com.fise.model.entity.Order;
import com.fise.model.entity.OrderItems;
import com.fise.model.entity.Suggestion;
import com.fise.model.param.GymLoginParam;
import com.fise.model.param.GymLogoutParam;
import com.fise.model.param.InitParam;
import com.fise.model.param.OrderCreateParam;
import com.fise.model.param.OrderSettleParam;
import com.fise.model.param.ScanForEnterParam;
import com.fise.model.result.GymLoginResult;
import com.fise.model.result.GymNearbyResult;
import com.fise.model.result.GymProfileResult;
import com.fise.model.result.InitAppResult;
import com.fise.model.result.MemberGymDetailResult;
import com.fise.service.account.IAccountService;
import com.fise.service.comment.ICommentService;
import com.fise.service.gym.IGymService;
import com.fise.service.member.IMemberService;
import com.fise.service.member.impl.MemberServiceImpl.InitCmd;
import com.fise.service.order.IOrderService;
import com.fise.service.orderItems.IOrderItemsService;
import com.fise.utils.CommonUtil;
import com.fise.utils.Constants;
import com.fise.utils.StringUtil;
import com.qq.jutil.j4log.Logger;

import redis.clients.jedis.Jedis;

@Service
public class GymServiceImpl implements IGymService {
	@Autowired
	private GymMapper gymDao;
	
	@Autowired
	private GymItemMapper gymItemDao;
	
	@Autowired
	private SuggestionMapper suggestionDao;
	
	@Resource
	private IAccountService accountService;
	
	@Resource
	private IMemberService memberService;
	
	@Resource
	private IOrderService orderService;
	
	@Resource
	private IOrderItemsService orderItemsService;
	
	@Resource
	private ICommentService commentService;
		
	// 用户登录
	@Override
	public Response login(GymLoginParam param, HttpServletRequest request) {
		Response resp = new Response();
		
		Gym gymInDB = new Gym();
		if (StringUtil.isNotEmpty(param.getGymName())) {
			gymInDB = gymDao.getGymByGymName(param.getGymName());
		} else {
			gymInDB = gymDao.getGymByLoginId(param.getLoginId());
		}
		
		if (gymInDB == null) {
			resp.failure(ErrorCode.ERROR_MEMBER_INDB_IS_NULL);
			return resp;
		}
		
		if (!param.getPassword().equals(gymInDB.getPassword())) {
			resp.failure(ErrorCode.ERROR_PASSWORD_INCORRECT);
			return resp;
		}
		String accessToken = genAccessToken(gymInDB);
		
		Gym gymForUpdate = new Gym();
		gymForUpdate.setLastLogin(new Date());
		gymForUpdate.setGymId(gymInDB.getGymId());
		gymDao.updateGym(gymForUpdate); 
		
		GymLoginResult result = new GymLoginResult();
		result.setGymId(gymInDB.getGymId());
		result.setLastLogin(gymForUpdate.getLastLogin());
		result.setAccessToken(accessToken);
		result.setAvatar(gymInDB.getAvatar());
		result.setGymName(gymInDB.getGymName());
		result.setLoginId(gymInDB.getLoginId());
		
		resp.success(result);
		
		return resp;
	}

	// 用户退出
	@Override
	public Response logout(GymLogoutParam param, HttpServletRequest request) {
		Response resp = new Response();
		
		Integer gymId = param.getGymId();
		String accessToken = request.getHeader(Constants.MANAGER_HEADER_FIELD_NAME_ACCESS_TOKEN);
		
		delAccessToken(gymId, accessToken);
		resp.success();
		
		return resp;
	}

	@Override
	public Gym getGymById(Integer gymId) {
		Gym gym = new Gym();
		gym = gymDao.getGymById(gymId);
		
		return gym;
	}

	@Override
	public Response insertScanForEnter(ScanForEnterParam param) {
		Response resp = new Response();
		
		Member member = memberService.getMemberById(param.getMemberId());
		if (member == null) {
			resp.failure(ErrorCode.ERROR_MEMBER_INDB_IS_NULL);
			return resp;
		}
		
		Gym gym = getGymById(param.getGymId());
		if (gym == null) {
			resp.failure(ErrorCode.ERROR_GYM_INDB_IS_NULL);
			return resp;
		}
		
		// 在本店就未结算的就不能再扫码入场了(先分别查两次状态，后面优化mybatis配置解决)
		Order order = new Order();
		order.setGymId(gym.getGymId());
		order.setMemberId(member.getMemberId());
		order.setStatus(Constants.ORDER_STATUS_NOT_CLEARING);
		List<Order> orderListInDb =  orderService.getOrderSelective(order);
		if (orderListInDb.size() > 0) {
			resp.failure(ErrorCode.ERROR_GYM_NOT_COMPLETE_ORDER_EXIST);
			return resp;
		}
		order.setStatus(Constants.ORDER_STATUS_NOT_PAY);
		orderListInDb =  orderService.getOrderSelective(order);
		if (orderListInDb.size() > 0) {
			resp.failure(ErrorCode.ERROR_GYM_NOT_COMPLETE_ORDER_EXIST);
			return resp;
		}
		
		Long orderAmount = 0L;	// 订单金额
		List<OrderGymItemDto> orderGymItemDtoList = param.getOrderGymItemList();
		for(OrderGymItemDto gymItemDto : orderGymItemDtoList) {
			GymItem gymItem = gymItemDao.selectByPrimaryKey(gymItemDto.getGymItemId());
			if (gymItem == null) {
				// TODO log
				resp.failure(ErrorCode.ERROR_GYM_ITEM_NOT_EXIST);
				return resp;
			}
			orderAmount += (gymItemDto.getGymItemPrice() * gymItemDto.getCount());
		}
		
		// 单点部署，加锁
		// TODO: 分布式部署，加唯一索引表
		Long depositAmount = 0L;
		Long preAvailableBalance = 0L;
		Long postAvailableBalance = 0L;
		synchronized (member) {
			MemberAccount memberAccount = accountService.getMemberAccountByMemberId(param.getMemberId());
			if (memberAccount == null) {
				resp.failure(ErrorCode.ERROR_MEMBER_ACCOUNT_INDB_IS_NULL);
				return resp;
			}
			
			Long availableBalance = memberAccount.getAvailableBalance();
			preAvailableBalance = availableBalance;
			Long frozenBalance = memberAccount.getFrozenBalance();
			depositAmount = gym.getMinSpendAmount() + Constants.DEFAULT_BALANCE_FROZEN_AMOUNT;		// 本次押金
			if (availableBalance - depositAmount < 0) {
				resp.failure(ErrorCode.ERROR_MEMBER_ACCOUNT_AVAILABLEBALANCE_LACK);
				return resp;
			}
			availableBalance -= depositAmount;
			postAvailableBalance = availableBalance; 
			frozenBalance += depositAmount;
			memberAccount.setAvailableBalance(availableBalance);
			memberAccount.setFrozenBalance(frozenBalance);
			accountService.updateMemberAccountBalance(memberAccount);
		}
		// 生成订单
		OrderCreateParam orderCreateParam = new OrderCreateParam();
		orderCreateParam.setMemberId(param.getMemberId());
		orderCreateParam.setGymId(param.getGymId());
		orderCreateParam.setDepositAmount(depositAmount);
		orderCreateParam.setOrderAmount(orderAmount);
		orderCreateParam.setPreBalance(preAvailableBalance);
		orderCreateParam.setPostBalance(postAvailableBalance);
		orderCreateParam.setOrderGymItemList(orderGymItemDtoList);
		orderService.createOrder(orderCreateParam);
		
		return resp;
	}	
	
	// 生成access token
	private String genAccessToken(Gym gym) {
		Jedis jedis = null;
		String accessToken = null;
		try {
			jedis = RedisManager.getInstance().getResource(Constants.REDIS_POOL_NAME_GYM);
			accessToken = CommonUtil.getAccessToken();
			String key = Constants.REDIS_KEY_PREFIX_GYM_ACCESS_TOKEN + accessToken;
			String value = gym.getGymId() + "";
			String ret = jedis.setex(key, Constants.ACCESS_TOKEN_EXPIRE_SECONDS, value);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			RedisManager.getInstance().returnResource(Constants.REDIS_POOL_NAME_GYM, jedis);
		}
		
		return accessToken;
	}
	
	// 删除access token 
	private boolean delAccessToken(Integer gymId, String accessToken) {
		boolean ret = false;
		Jedis jedis = null;
		try {
			
			jedis = RedisManager.getInstance().getResource(Constants.REDIS_POOL_NAME_GYM);
			
			String key = Constants.REDIS_KEY_PREFIX_GYM_ACCESS_TOKEN + accessToken;
			
			String gymIdInRedis = jedis.get(key);
			if (!gymId.toString().equals(gymIdInRedis)) return false;
			jedis.del(key);
			ret = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			RedisManager.getInstance().returnResource(Constants.REDIS_POOL_NAME_GYM, jedis);
		}
		
		return ret;
	}

	@Override
	public Response updateOrderSettls(OrderSettleParam param) {
		Response resp = new Response();
		
		// check订单信息
		Order order = new Order();
		order.setGymId(param.getGymId());
		order.setMemberId(param.getMemberId());
		order.setSn(param.getOrderSn());
		order.setStatus(Constants.ORDER_STATUS_NOT_CLEARING);		// 订单状态必须是已入场、待结算状态
	    Order orderInDb =  orderService.selectByOrder(order);
	    if (orderInDb == null) {
	    	resp.failure(ErrorCode.ERROR_ORDER_NOT_EXIST);
	    	return resp;
	    }
	    
	    // 订单项修改
	    List<OrderItems> orderItemsListForUpdate = new ArrayList<OrderItems>();
	    List<OrderGymItemDto> orderGymItemList = param.getOrderGymItemList();
	    List<OrderItems> orderItemsList =  orderItemsService.getOrderItemsByOrderId(orderInDb.getOrderId());
	    Long needPayAmount = 0L;
	    for (OrderItems orderItem : orderItemsList) {
	    	boolean foundFlag = false;
	    	for(OrderGymItemDto orderGymItemDto : orderGymItemList) {
	    		if (orderGymItemDto.getGymItemId().intValue() == orderItem.getGymItemId().intValue()) {
	    			foundFlag = true;
	    			needPayAmount += orderItem.getPrice() * orderGymItemDto.getCount();
	    			orderItem.setCount(orderGymItemDto.getCount());
	    			orderItem.setState(Constants.ORDER_ITEM_STATE_OK);
	    		}
	    	}
	    	if (!foundFlag) {
	    		orderItem.setState(Constants.ORDER_ITEM_STATE_CANCEL);
	    	}
	    	orderItemsListForUpdate.add(orderItem);
	    }
	    if (orderItemsListForUpdate.size() > 0) {
	    	orderItemsService.updateOrderItems(orderItemsListForUpdate);
	    }
	    
	    Order orderForUpdate = new Order();
	    orderForUpdate.setOrderId(orderInDb.getOrderId());
	    orderForUpdate.setStatus(Constants.ORDER_STATUS_NOT_PAY);		// 把订单状态置为待付款状态
	    orderForUpdate.setNeedPayAmount(needPayAmount);
	    if (param.getUseDepositAmount()) {
	    	orderForUpdate.setRemark("useDeposit=true");
		}
	    orderService.updateOrderByPrimaryKeySelective(orderForUpdate);
	    
		resp.success();
		return resp;
	}

	@Override
	public Response getGymNearby(Page<Gym> pageParam) {
		Response resp = new Response();
		Logger logger = Logger.getLogger("gym_near_by");
		
		Map<String, Object> extraParamMap = pageParam.getExtraParam();
		if (extraParamMap == null || extraParamMap.get("longitude") == null || extraParamMap.get("latitude") == null || extraParamMap.get("distance") == null) {
			resp.failure(ErrorCode.ERROR_PRRAM_NOT_COMPLETE_EXCEPTION);
			return resp;
		}
		
		/*
		List<Gym> gymList = gymDao.getGymList(pageParam);
		List<GymNearbyResult> gymNearbyResultList = new ArrayList<GymNearbyResult>();
		for (Gym gym : gymList) {
			GymNearbyResult gymNearbyResult = new GymNearbyResult();
			gymNearbyResult.setGymId(gym.getGymId());
			gymNearbyResult.setGymName(gym.getGymName());
			gymNearbyResult.setAvatar(gym.getAvatar());
			gymNearbyResult.setLatitude(gym.getLatitude());
			gymNearbyResult.setLongitude(gym.getLongitude());
			gymNearbyResult.setAddress(gym.getAddress());
			gymNearbyResult.setMinSpendAmount(gym.getMinSpendAmount());

			Float score = commentService.selectAvgScore(gym.getGymId());
			gymNearbyResult.setScore(score);
			
			gymNearbyResultList.add(gymNearbyResult);
		}
		resp.success(gymNearbyResultList);
		*/
		
		List<GymNearbyResult> gymNearbyResultList = new ArrayList<GymNearbyResult>();
		SearchHits searchHits = ElasticSearchManager.getInstance().queryGymNearby(Constants.ES_INDEX, Constants.ES_GYM_TYPE, pageParam);
		for (SearchHit hit : searchHits) {
			
			Double distance = StringUtil.formatDouble(hit.getSortValues()[0] + "");
			logger.debug("distance:" + distance + " " + hit.getSource());
			List<?> location = (List<?>)hit.getSource().get("location");
			Double longitude = Double.parseDouble(location.get(0) + "");
			Double latitude = Double.parseDouble(location.get(1) + "");
			
			GymNearbyResult gymNearbyResult = new GymNearbyResult();
			gymNearbyResult.setGymId((Integer)hit.getSource().get("gymId"));
			gymNearbyResult.setGymName((String)hit.getSource().get("gymName"));
			gymNearbyResult.setAvatar((String)hit.getSource().get("avatar"));
			gymNearbyResult.setLatitude(latitude);
			gymNearbyResult.setLongitude(longitude);
			gymNearbyResult.setAddress((String)hit.getSource().get("address"));
			gymNearbyResult.setMinSpendAmount(Long.parseLong(hit.getSource().get("minSpendAmount")+""));
			gymNearbyResult.setDistance(distance);

			Float score = commentService.selectAvgScore((Integer)hit.getSource().get("gymId"));
			gymNearbyResult.setScore(score);
			
			gymNearbyResultList.add(gymNearbyResult);
			
		}
		resp.success(gymNearbyResultList);
		
		return resp;
	}

	@Override
	public Response getGymDetail(Integer gymId) {
		Response resp = new Response();
		
		Gym gym = getGymById(gymId);
		Float score = commentService.selectAvgScore(gymId);
		
		
		MemberGymDetailResult detail = new MemberGymDetailResult();
		detail.setGymId(gym.getGymId());
		detail.setGymName(gym.getGymName());
		detail.setMinSpendAmount(gym.getMinSpendAmount());
		detail.setAddress(gym.getAddress());
		detail.setLongitude(gym.getLongitude());
		detail.setLatitude(gym.getLatitude());
		detail.setTel(gym.getTel());
		detail.setStatus(gym.getStatus());
		detail.setAvatar(gym.getAvatar());
		detail.setBroadcast(gym.getBroadcast());
		detail.setScore(score);
		detail.setIntroduction(gym.getIntroduction());
		detail.setImgIntroductions(gym.getImgIntroductions());
		
		resp.success(detail);
		
		return resp;
	}

	@Override
	public void updateGym(Gym gym) {
		gymDao.updateGym(gym);
	}

	@Override
	public void postBroadcast(Integer gymId, String content) {
		Gym gym = new Gym();
		gym.setGymId(gymId);
		gym.setBroadcast(content);
		gymDao.updateGym(gym);
		
		return ;
	}

	@Override
	public void postFeedback(Suggestion suggestion) {
		suggestionDao.insert(suggestion);
	}

	@Override
	public Response initApp(Integer gymId, InitParam param) {
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
					latestVersion = ConfigProperties.getValue("gym_ios_latest_version");
					downloadUrl = ConfigProperties.getValue("gym_ios_download_url");
				} else if (platform.contains("android")) {
					latestVersion = ConfigProperties.getValue("gym_android_latest_version");
					downloadUrl = ConfigProperties.getValue("gym_android_download_url");
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
			case gym_profile:
				Response respTmp = new Response();
				respTmp = getGymProfile(gymId);
				System.out.println("restpTmp toString= " + respTmp.toString());
				GymProfileResult result = (GymProfileResult)respTmp.getData();
				map.put("gym_profile", result);
				if (!allFlag) break;
			default:
				initAppResult.setExtraMap(map);
				break;
		}
		
		resp.success(initAppResult);
		return resp;
	}

	@Override
	public Response getGymProfile(Integer gymId) {
		Response resp = new Response();
		
		Gym gym = getGymById(gymId);
		GymProfileResult result = new GymProfileResult();
		result.setGymId(gym.getGymId());
		result.setGymName(gym.getGymName());
		result.setAvatar(gym.getAvatar());
		result.setLastLogin(gym.getLastLogin());
		result.setDepositAmount(gym.getMinSpendAmount() + Constants.DEFAULT_BALANCE_FROZEN_AMOUNT);
		resp.success(result);
		
		return resp;
	}

	@Override
	public List<Gym> getGymList(Page<Gym> pageParam) {
		return gymDao.getGymList(pageParam);
	}
	
	
}
