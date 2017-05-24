package com.fise.service.order.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fise.base.ErrorCode;
import com.fise.base.Page;
import com.fise.base.Response;
import com.fise.dao.OrderMapper;
import com.fise.model.dto.OrderGymItemDto;
import com.fise.model.entity.Gym;
import com.fise.model.entity.GymAccount;
import com.fise.model.entity.GymAccountTransaction;
import com.fise.model.entity.Member;
import com.fise.model.entity.MemberAccount;
import com.fise.model.entity.MemberAccountTransaction;
import com.fise.model.entity.Order;
import com.fise.model.entity.OrderItems;
import com.fise.model.param.OrderCreateParam;
import com.fise.model.param.OrderPayParam;
import com.fise.model.result.OrderDetailResult;
import com.fise.model.result.OrderForGymResult;
import com.fise.model.result.OrderForMemberResult;
import com.fise.service.account.IAccountService;
import com.fise.service.account.IAccountTransactionService;
import com.fise.service.gym.IGymService;
import com.fise.service.member.IMemberService;
import com.fise.service.order.IOrderService;
import com.fise.service.orderItems.IOrderItemsService;
import com.fise.utils.CommonUtil;
import com.fise.utils.Constants;
import com.fise.utils.DateUtil;
import com.fise.utils.StringUtil;
import com.qq.jutil.j4log.Logger;

@Service
public class OrderServiceImpl implements IOrderService {
	@Resource
	private IGymService gymService;
	
	@Resource
	private IMemberService memberService;
	
	@Resource
	private IOrderItemsService orderItemsService;
	
	@Resource
	private IAccountService accountService;
	
	@Resource
	private IAccountTransactionService accountTransactionService;
	
	@Autowired
	private OrderMapper orderDao;
	
	@Override
	public Response createOrder(OrderCreateParam param) {
		Response resp = new Response();
		
		Member member = memberService.getMemberById(param.getMemberId());
		if (member == null) {
			resp.failure(ErrorCode.ERROR_MEMBER_INDB_IS_NULL);
			return resp;
		}
		
		Gym gym = gymService.getGymById(param.getGymId());
		if (gym == null) {
			resp.failure(ErrorCode.ERROR_GYM_INDB_IS_NULL);
			return resp;
		}
		
		// 生成订单表
		Order order = new Order();
		order.setGymId(gym.getGymId());
		order.setMemberId(member.getMemberId());
		order.setSn(createSn());		// TODO 订单号如何防止重复
		order.setIsDisabled(0);
		order.setStatus(Constants.ORDER_STATUS_NOT_CLEARING);
		order.setPayStatus(Constants.PAY_NOT);					// 支付状态：暂时不用 
		order.setOrderBillFlag(Constants.ORDER_BILL_FLAG_NO);	// 结算状态（主要是订单完成三级代理分润）   暂时不用
		order.setOrderAmount(param.getOrderAmount());						// 订单金额
		order.setNeedPayAmount(param.getOrderAmount());						// 订单需要支付金额
		order.setDepositAmount(param.getDepositAmount());					// 订单押金
		orderDao.insertOrder(order);
		
		// 生成订单子项表
		List<OrderGymItemDto> orderGymItemDtoList = param.getOrderGymItemList();
		List<OrderItems> orderItemsList = new ArrayList<OrderItems>();
		for(OrderGymItemDto gymItemDto : orderGymItemDtoList) {
			OrderItems orderItems = new OrderItems();
			orderItems.setOrderId(order.getOrderId());
			orderItems.setOrderSn(order.getSn());
			orderItems.setGymItemId(gymItemDto.getGymItemId());
			orderItems.setCount(gymItemDto.getCount());
			orderItems.setGymItemName(gymItemDto.getGymItemName());
			orderItems.setPrice(gymItemDto.getGymItemPrice());
			orderItems.setState(0);		// 这个字段要废弃
			orderItems.setIsCommented(0);
			orderItems.setGymId(gym.getGymId());
			
			orderItemsList.add(orderItems);
		}
		orderItemsService.saveOrderItems(orderItemsList);
		
		// 生成交易流水
		MemberAccountTransaction memberAccountTransaction = new MemberAccountTransaction(); 
		memberAccountTransaction.setTransNo(CommonUtil.getTransactionNo(member));
		memberAccountTransaction.setMemberId(member.getMemberId());
		memberAccountTransaction.setOrderId(order.getOrderId());
		memberAccountTransaction.setOperateType(Constants.ACCOUNT_TRANSACTION_OPERATE_TYPE_FROZEN_DEPOSIT);
		memberAccountTransaction.setPayMethod(Constants.PAY_METHOD_BALANCE_PAY);
		memberAccountTransaction.setAmount(param.getDepositAmount());
		memberAccountTransaction.setPreBalance(param.getPreBalance());
		memberAccountTransaction.setPostBalance(param.getPostBalance());
		accountTransactionService.insertMemberAccountTransaction(memberAccountTransaction);
		
		resp.success();
		return resp;
	}
	
	// 生成订单号
	private String createSn() {
		StringBuffer sn = new StringBuffer(DateUtil.getDateline() + "");
		sn.append(getRandom());
		return sn.toString();
	}

	// 获取随机数
	private int getRandom() {
		Random random = new Random();
		int num = Math.abs(random.nextInt()) % 100;
		if (num < 10) {
			num = getRandom();
		}
		return num;
	}

	@Override
	public Response orderPay(OrderPayParam param) {
		Logger logger = Logger.getLogger("order_pay");
		Response resp = new Response();
		
		// 用户
		Member member = memberService.getMemberById(param.getMemberId());
		if (member == null) {
			resp.failure(ErrorCode.ERROR_MEMBER_ACCOUNT_INDB_IS_NULL);
			return resp;
		}
		
		// 商户 
		Gym gym = gymService.getGymById(param.getGymId());
		if (gym == null) {
			resp.failure(ErrorCode.ERROR_GYM_INDB_IS_NULL);
			return resp;
		}
		
		// 用户账户
		MemberAccount memberAccount =  accountService.getMemberAccountByMemberId(param.getMemberId());
		if (memberAccount == null) {
			resp.failure(ErrorCode.ERROR_MEMBER_ACCOUNT_INDB_IS_NULL);
			return resp;
		}
		if (StringUtil.isEmpty(memberAccount.getPassword())) {
			resp.failure(ErrorCode.ERROR_ACCOUNT_PASSWORD_IS_EMPTY);
			return resp;
		}
		if (!param.getPayPassword().equals(memberAccount.getPassword())) {
			resp.failure(ErrorCode.ERROR_PASSWORD_INCORRECT);
			return resp;
		}
		
		// 商户账户
		GymAccount gymAccount = accountService.getGymAccountByGymId(param.getGymId());
		if (gymAccount == null) {
			resp.failure(ErrorCode.ERROR_GYM_ACCOUNT_INDB_IS_NULL);
			return resp;
		}
		
		// 订单信息
		Order orderParam = new Order();
		orderParam.setSn(param.getOrderSn());
		orderParam.setGymId(param.getGymId());
		orderParam.setMemberId(param.getMemberId());
		orderParam.setStatus(Constants.ORDER_STATUS_NOT_PAY);
		List<Order> orderList = getOrderSelective(orderParam);
		if (orderList.size() != 1) {
			resp.failure(ErrorCode.ERROR_ORDER_NOT_EXIST);
			return resp;
		}
		Order order = orderList.get(0);
		String remark = order.getRemark();
		boolean useDeposit = false;
		if (StringUtil.isNotEmpty(remark) && remark.equals("useDeposit=true")) {
			useDeposit = true;
		}
		
		// 支付逻辑
		Long memberPreAvailableBalanceReturnDeposit = 0L;		// 退还押金前账户余额
		Long memberPostAvailableBalanceReturnDeposit = 0L;		// 退还押金后账户余额
		Long memberPreAvailableBalanceBalancePay = 0L;			// 余额支付前账户余额
		Long memberPostAvailableBalanceBalancePay = 0L;			// 余额支付后账户余额
		Long gymPreAvailableBalance = 0L;						// 商户：结算前余额
		Long gymPostAvailableBalance = 0L;						// 商户：结算后余额

		Long needPayAmount = order.getNeedPayAmount();							// 需支付金额
		Long orderDepositAmount = order.getDepositAmount();						// 押金
		Long memberAvailableBalance =  memberAccount.getAvailableBalance();		// 账户余额
		Long memberFrozenBalance = memberAccount.getFrozenBalance();			// 冻结金额
		Long gymWaitBalance = gymAccount.getWaitBalance();						// 商户待结算金额
		
		logger.debug("memberAccount=" + memberAccount.toString());
		logger.debug("order=" + order.toString());
		
		if (!useDeposit) {
			if (needPayAmount > memberAvailableBalance + orderDepositAmount) {
				resp.failure(ErrorCode.ERROR_MEMBER_ACCOUNT_AVAILABLEBALANCE_LACK);
				return resp;
			}
			
			// 退还押金
			memberPreAvailableBalanceReturnDeposit = memberAvailableBalance;
			memberFrozenBalance -= orderDepositAmount;
			memberAvailableBalance += orderDepositAmount;
			memberPostAvailableBalanceReturnDeposit = memberAvailableBalance;
			
			// 消费支付
			memberPreAvailableBalanceBalancePay = memberAvailableBalance;
			memberAvailableBalance -= needPayAmount;				// 减去本次订单消费金额
			gymWaitBalance += needPayAmount;						// 商家待入账金额
			memberPostAvailableBalanceBalancePay = memberAvailableBalance;
			
			// 更新账户余额
			memberAccount.setAvailableBalance(memberAvailableBalance);
			memberAccount.setFrozenBalance(memberFrozenBalance);
			gymAccount.setWaitBalance(gymWaitBalance);
			accountService.updateMemberAccountBalance(memberAccount);
			accountService.updateGymAccountBalance(gymAccount);
			
			// 更新订单状态
			Date date = new Date(System.currentTimeMillis());
			Order orderForUpdate = new Order();
			orderForUpdate.setOrderId(order.getOrderId());
			orderForUpdate.setStatus(Constants.ORDER_STATUS_NOT_COMMENTED);
			orderForUpdate.setPayStatus(Constants.PAY_CONFIRM);
			orderForUpdate.setPaymentId(6);					// 余额支付
			orderForUpdate.setPaymentName("余额支付");		// TODO： 放配置
			orderForUpdate.setPaymentTime(date);
			orderForUpdate.setPaymentType("fitness.balance.pay");
			orderForUpdate.setCompleteTime(date);
			orderForUpdate.setBalancePayAmount(needPayAmount);
			orderForUpdate.setPayAmount(needPayAmount);
			orderDao.updateByPrimaryKeySelective(orderForUpdate);
			
			// 用户押金退款交易流水
			MemberAccountTransaction memberAccountTransaction = new MemberAccountTransaction(); 
			memberAccountTransaction.setTransNo(CommonUtil.getTransactionNo(member));
			memberAccountTransaction.setMemberId(member.getMemberId());
			memberAccountTransaction.setOrderId(order.getOrderId());
			memberAccountTransaction.setOperateType(Constants.ACCOUNT_TRANSACTION_OPERATE_TYPE_RETURN_DEPOSIT);
			memberAccountTransaction.setAmount(orderDepositAmount);
			memberAccountTransaction.setPreBalance(memberPreAvailableBalanceReturnDeposit);
			memberAccountTransaction.setPostBalance(memberPostAvailableBalanceReturnDeposit);
			accountTransactionService.insertMemberAccountTransaction(memberAccountTransaction);
			
			// 用户余额支付流水
			MemberAccountTransaction memberAccountTransactionBP = new MemberAccountTransaction(); 
			memberAccountTransactionBP.setTransNo(CommonUtil.getTransactionNo(member));
			memberAccountTransactionBP.setMemberId(member.getMemberId());
			memberAccountTransactionBP.setOrderId(order.getOrderId());
			memberAccountTransactionBP.setOperateType(Constants.ACCOUNT_TRANSACTION_OPERATE_TYPE_ORDER_PAYMENT);
			memberAccountTransactionBP.setPayMethod(Constants.PAY_METHOD_BALANCE_PAY);
			memberAccountTransactionBP.setAmount(needPayAmount);
			memberAccountTransactionBP.setPreBalance(memberPreAvailableBalanceBalancePay);
			memberAccountTransactionBP.setPostBalance(memberPostAvailableBalanceBalancePay);
			accountTransactionService.insertMemberAccountTransaction(memberAccountTransactionBP);
			
			// 商户待入账流水
			GymAccountTransaction gymAccountTransaction = new GymAccountTransaction(); 
			gymAccountTransaction.setTransNo(CommonUtil.getTransactionNo(gym));
			gymAccountTransaction.setGymId(gym.getGymId());
			gymAccountTransaction.setOrderId(order.getOrderId());
			gymAccountTransaction.setOperateType(Constants.GYM_ACCOUNT_TRANSACTION_OPERATE_TYPE_SALES_INCOME);
			gymAccountTransaction.setAmount(needPayAmount);
			accountTransactionService.insertGymAccountTransaction(gymAccountTransaction);
		} else {
			if (needPayAmount > memberAvailableBalance) {
				resp.failure(ErrorCode.ERROR_MEMBER_ACCOUNT_AVAILABLEBALANCE_LACK);
				return resp;
			}

			// 扣除押金
			memberFrozenBalance -= orderDepositAmount;
			gymWaitBalance += orderDepositAmount;
			
			// 消费支付
			memberPreAvailableBalanceBalancePay = memberAvailableBalance;
			memberAvailableBalance -= needPayAmount;						// 减去本次订单消费金额
			gymWaitBalance += needPayAmount ;								// 商家待入账金额
			memberPostAvailableBalanceBalancePay = memberAvailableBalance;
			
			// 更新账户
			memberAccount.setAvailableBalance(memberAvailableBalance);
			memberAccount.setFrozenBalance(memberFrozenBalance);
			gymAccount.setWaitBalance(gymWaitBalance);
			accountService.updateMemberAccountBalance(memberAccount);
			accountService.updateGymAccountBalance(gymAccount);
						
			// 更新订单状态
			Long payAmount = needPayAmount + orderDepositAmount;
			Date date = new Date(System.currentTimeMillis());
			Order orderForUpdate = new Order();
			orderForUpdate.setOrderId(order.getOrderId());
			orderForUpdate.setStatus(Constants.ORDER_STATUS_NOT_COMMENTED);
			orderForUpdate.setPayStatus(Constants.PAY_CONFIRM);
			orderForUpdate.setPaymentId(6);					// 余额支付
			orderForUpdate.setPaymentName("余额支付");		// TODO： 放配置
			orderForUpdate.setPaymentTime(date);
			orderForUpdate.setPaymentType("fitness.balance.pay");
			orderForUpdate.setCompleteTime(date);
			orderForUpdate.setBalancePayAmount(needPayAmount);
			orderForUpdate.setPayAmount(payAmount);
			orderDao.updateByPrimaryKeySelective(orderForUpdate);
			
			// 用户扣除押金交易流水
			MemberAccountTransaction memberAccountTransaction = new MemberAccountTransaction(); 
			memberAccountTransaction.setTransNo(CommonUtil.getTransactionNo(member));
			memberAccountTransaction.setMemberId(member.getMemberId());
			memberAccountTransaction.setOrderId(order.getOrderId());
			memberAccountTransaction.setOperateType(Constants.ACCOUNT_TRANSACTION_OPERATE_TYPE_DEDUCT_DEPOSIT);
			memberAccountTransaction.setAmount(orderDepositAmount);
			accountTransactionService.insertMemberAccountTransaction(memberAccountTransaction);
						
			// 用户余额支付流水
			MemberAccountTransaction memberAccountTransactionBP = new MemberAccountTransaction(); 
			memberAccountTransactionBP.setTransNo(CommonUtil.getTransactionNo(member));
			memberAccountTransactionBP.setMemberId(member.getMemberId());
			memberAccountTransactionBP.setOrderId(order.getOrderId());
			memberAccountTransactionBP.setOperateType(Constants.ACCOUNT_TRANSACTION_OPERATE_TYPE_ORDER_PAYMENT);
			memberAccountTransactionBP.setPayMethod(Constants.PAY_METHOD_BALANCE_PAY);
			memberAccountTransactionBP.setAmount(needPayAmount);
			memberAccountTransactionBP.setPreBalance(memberPreAvailableBalanceBalancePay);
			memberAccountTransactionBP.setPostBalance(memberPostAvailableBalanceBalancePay);
			accountTransactionService.insertMemberAccountTransaction(memberAccountTransactionBP);
						
			// 商户扣除押金流水
			GymAccountTransaction gymAccountTransactionDE = new GymAccountTransaction(); 
			gymAccountTransactionDE.setTransNo(CommonUtil.getTransactionNo(gym));
			gymAccountTransactionDE.setGymId(gym.getGymId());
			gymAccountTransactionDE.setOrderId(order.getOrderId());
			gymAccountTransactionDE.setOperateType(Constants.GYM_ACCOUNT_TRANSACTION_OPERATE_TYPE_DEPOSIT_INCOME);
			gymAccountTransactionDE.setAmount(orderDepositAmount);
			accountTransactionService.insertGymAccountTransaction(gymAccountTransactionDE);
			
			// 商户销售收入流水
			GymAccountTransaction gymAccountTransaction = new GymAccountTransaction(); 
			gymAccountTransaction.setTransNo(CommonUtil.getTransactionNo(gym));
			gymAccountTransaction.setGymId(gym.getGymId());
			gymAccountTransaction.setOrderId(order.getOrderId());
			gymAccountTransaction.setOperateType(Constants.GYM_ACCOUNT_TRANSACTION_OPERATE_TYPE_SALES_INCOME);
			gymAccountTransaction.setAmount(needPayAmount);
			accountTransactionService.insertGymAccountTransaction(gymAccountTransaction);
		}
		return resp;
	}

	@Override
	public Order selectByOrder(Order order) {
		return orderDao.selectByOrder(order);
	}

	@Override
	public void updateOrderStatus(Order order) {
		orderDao.updateOrderStatus(order);
	}

	@Override
	public Response gymGetOrderList(Page<Order> pageParam) {
		Response resp = new Response();

		// 取订单信息
		Map<String, Object> extraParamMap = pageParam.getExtraParam();
		if (extraParamMap == null || extraParamMap.get("gym_id") == null || extraParamMap.get("order_status") == null) {
			resp.failure(ErrorCode.ERROR_PARAMETER_BUSINESS_CHECK_ERROR);
			return resp;
		}
		Integer gymId = (Integer)extraParamMap.get("gym_id");
		Integer orderStatus = (Integer)extraParamMap.get("order_status");		
		Integer memberIdParam = 0;
		if (extraParamMap.get("member_id") != null) {
			memberIdParam = (Integer)extraParamMap.get("member_id");
		}
		
		String query = null;
		if (StringUtil.isNotEmpty((String)extraParamMap.get("query"))) {
			query = (String)extraParamMap.get("query");
			if (StringUtil.isNumber(query)) {
				extraParamMap.put("mobile", extraParamMap.get("query"));
			} else {
				extraParamMap.put("nickName", extraParamMap.get("query"));
			}
		} 
		
		List<Order> orderList = null;
		Order orderParam = new Order();
		orderParam.setGymId(gymId);
		if (orderStatus >= 0) orderParam.setStatus(orderStatus); 		// 订单状态入参小于0，表示读取所有订单状态的订单
		
		if (StringUtil.isNotEmpty((String)extraParamMap.get("query"))) {
			orderList = orderDao.selectListUnionMeberByOrderAndMember(orderParam, pageParam);
		} else {
			if (memberIdParam > 0) orderParam.setMemberId(memberIdParam);
			orderList = orderDao.selectListByOrder(orderParam, pageParam);
		}
		
		List<OrderForGymResult> orderForGymResultList = new ArrayList<OrderForGymResult>();
		for(Order order : orderList) {
			OrderForGymResult orderForGymResult = new OrderForGymResult();
			
			Integer memberId = order.getMemberId();
			Member member = memberService.getMemberById(memberId);
			
			List<OrderItems> orderItemsList = orderItemsService.getOrderItemsByOrderId(order.getOrderId());
			List<OrderGymItemDto> orderGymItemDtoList = new ArrayList<OrderGymItemDto>();
			for (OrderItems orderItems : orderItemsList) {
				OrderGymItemDto orderGymItemDto = new OrderGymItemDto();
				orderGymItemDto.setGymItemId(orderItems.getGymItemId());
				orderGymItemDto.setGymItemName(orderItems.getGymItemName());
				orderGymItemDto.setCount(orderItems.getCount());
				orderGymItemDto.setGymItemPrice(orderItems.getPrice());
				
				orderGymItemDtoList.add(orderGymItemDto);
			}
			
			orderForGymResult.setMemberId(memberId);
			orderForGymResult.setAvatar(member.getAvatar());
			orderForGymResult.setNickName(member.getNickName());
			orderForGymResult.setMobile(member.getMobile());
			
			orderForGymResult.setOrderSn(order.getSn());
			orderForGymResult.setStatus(order.getStatus());
			orderForGymResult.setOrderAmount(order.getOrderAmount());
			orderForGymResult.setDepositAmount(order.getDepositAmount());
			orderForGymResult.setCreateTime(order.getCreateTime());
			orderForGymResult.setOrderItemList(orderGymItemDtoList);
			
			orderForGymResultList.add(orderForGymResult);
		}
		resp.success(orderForGymResultList);
		
		return resp;
	}

	@Override
	public Response memberGetOrderList(Page<Order> pageParam) {
		Response resp = new Response();
		
		Map<String, Object> extraParamMap = pageParam.getExtraParam();
		if (extraParamMap == null || extraParamMap.get("member_id") == null || extraParamMap.get("order_status") == null) {
			resp.failure(ErrorCode.ERROR_PARAMETER_BUSINESS_CHECK_ERROR);
			return resp;
		}
		Integer memberId = (Integer)extraParamMap.get("member_id");
		Integer orderStatus = (Integer)extraParamMap.get("order_status");
		
		Order orderParam = new Order();
		orderParam.setMemberId(memberId);
		if (orderStatus >= 0) orderParam.setStatus(orderStatus);			// 订单状态入参小于0，表示读取所有订单状态的订单
		
		List<Order> orderList = orderDao.selectListByOrder(orderParam, pageParam);
		List<OrderForMemberResult> orderForMemberResultList = new ArrayList<OrderForMemberResult>();
		for (Order order : orderList) {
			OrderForMemberResult orderForMemberResult = new OrderForMemberResult();
			Gym gym = gymService.getGymById(order.getGymId());
			
			List<OrderItems> orderItemsList = orderItemsService.getOrderItemsByOrderId(order.getOrderId());
			List<OrderGymItemDto> orderGymItemDtoList = new ArrayList<OrderGymItemDto>();
			for (OrderItems orderItems : orderItemsList) {
				OrderGymItemDto orderGymItemDto = new OrderGymItemDto();
				orderGymItemDto.setGymItemId(orderItems.getGymItemId());
				orderGymItemDto.setGymItemName(orderItems.getGymItemName());
				orderGymItemDto.setCount(orderItems.getCount());
				orderGymItemDto.setGymItemPrice(orderItems.getPrice());
				
				orderGymItemDtoList.add(orderGymItemDto);
			}
			orderForMemberResult.setAvatar(gym.getAvatar());
			orderForMemberResult.setGymId(gym.getGymId());
			orderForMemberResult.setGymName(gym.getGymName());
			
			orderForMemberResult.setOrderSn(order.getSn());
			orderForMemberResult.setStatus(order.getStatus());
			orderForMemberResult.setOrderAmount(order.getOrderAmount());
			orderForMemberResult.setCreateTime(order.getCreateTime());
			orderForMemberResult.setOrderItemList(orderGymItemDtoList);
			
			orderForMemberResultList.add(orderForMemberResult);
		}
		resp.success(orderForMemberResultList);
		
		return resp;
	}

	@Override
	public List<Order> getOrderSelective(Order order) {
		return orderDao.selectByOrderSelective(order);
	}

	@Override
	public void updateOrderByPrimaryKeySelective(Order order) {
		orderDao.updateByPrimaryKeySelective(order);
		
		return ;
	}

	@Override
	public Response getOrderDetail(Integer memberId, String orderSn) {
		Response resp = new Response();
		
		// 订单信息
		Order orderParam = new Order();
		orderParam.setSn(orderSn);
		orderParam.setMemberId(memberId);
		List<Order> orderList = getOrderSelective(orderParam);
		if (orderList.size() != 1) {
			resp.failure(ErrorCode.ERROR_ORDER_NOT_EXIST);
			return resp;
		}
		Order order = orderList.get(0);
		boolean useDeposit = false;
		if (StringUtil.isNotEmpty(order.getRemark()) && order.getRemark().equals("useDeposit=true")) {
			useDeposit = true;
		}
		
		// 订单项
		List<OrderItems> orderItemsList =  orderItemsService.getOrderItemsByOrderId(order.getOrderId());
		List<OrderGymItemDto> orderGymItemDtolList = new ArrayList<OrderGymItemDto>();
		for (OrderItems orderItem : orderItemsList) {
	    	if (orderItem.getState().intValue() == Constants.ORDER_ITEM_STATE_OK) {
	    		OrderGymItemDto orderGymItemDto = new OrderGymItemDto();
	    		orderGymItemDto.setGymItemName(orderItem.getGymItemName());
	    		orderGymItemDto.setGymItemPrice(orderItem.getPrice() * orderItem.getCount());
	    		//orderGymItemDto.setCount(orderItem.getCount());
	    		//orderGymItemDto.setGymItemId(orderItem.getGymItemId());
	    		orderGymItemDtolList.add(orderGymItemDto);
	    	}
	    }
		
		// 商户信息
		Gym gym = gymService.getGymById(order.getGymId());
		
		// 返回
		OrderDetailResult result = new OrderDetailResult();
		result.setDepositAmount(order.getDepositAmount());
		result.setOrderSn(order.getSn());
		result.setGymName(gym.getGymName());
		result.setPayAmount(order.getPayAmount());
		result.setPaymentTime(order.getPaymentTime());
		result.setPaymentName(order.getPaymentName());
		result.setUserDeposit(useDeposit);
		result.setOrderItemList(orderGymItemDtolList);
		result.setStatus(order.getStatus());
		
		resp.success(result);
		return resp;
	}

	@Override
	public Response gymGetOrderListHeader(Integer status) {
		Response resp = new Response();
		
		Date todayBegin = DateUtil.getTodayBeginDate();
		Date weekBegin = DateUtil.getWeekBeginDate();
		Date monthBegin = DateUtil.getMonthBeginDate();
		Long todayCount = 0L;
		Long weekCount = 0L;
		Long monthCount = 0L;
		
		if (status.intValue() == 1) {
			todayCount = orderDao.selectCount(status, todayBegin);
			weekCount = orderDao.selectCount(status, weekBegin);
			monthCount = orderDao.selectCount(status, monthBegin);
		} else {
			todayCount = orderDao.selectCompleteCount(status, todayBegin);
			weekCount = orderDao.selectCompleteCount(status, weekBegin);
			monthCount = orderDao.selectCompleteCount(status, monthBegin);
		}
		
		Map<String, Long> map = new HashMap<String, Long>();
		map.put("month", monthCount);
		map.put("today", todayCount);
		map.put("week", weekCount);
		resp.success(map);
		
		return resp;
	}

	@Override
	public Long getGymMemberCount(Integer gymId) {
		return orderDao.selectGymMemberCount(gymId);
	}

	@Override
	public Long getGymActiveMemberCount(Integer gymId, Date beginTime) {
		return orderDao.selectGymActiveMemberCount(gymId, beginTime);
	}

	@Override
	public Long getGymFrozenMemberCount(Integer gymId) {
		return orderDao.selectGymFrozenMemberCount(gymId);
	}
}
