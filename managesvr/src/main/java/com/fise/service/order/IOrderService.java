package com.fise.service.order;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.fise.base.Page;
import com.fise.base.Response;
import com.fise.model.entity.Order;
import com.fise.model.param.OrderCreateParam;
import com.fise.model.param.OrderPayParam;
import com.fise.model.param.OrderSettleParam;

public interface IOrderService {
	// 生成订单
	public Response createOrder(OrderCreateParam param);
	
	// 订单支付（用户端发起）
	public Response orderPay(OrderPayParam param);
	
	// 验证订单
	public Order selectByOrder(Order order);
	
	// 更新订单状态
	public void updateOrderStatus(Order order);
	
	// 商家端获取订单列表
	public Response gymGetOrderList(Page<Order> pageParam);
	
	// 用户端获取订单列表
	public Response memberGetOrderList(Page<Order> pageParam);
	
	// 根据订单条件查订单列表
	public List<Order> getOrderSelective(Order order);
	
	// 根据orderId更新订单字段
	public void updateOrderByPrimaryKeySelective(Order order);
	
	// 获取订单详情
	public Response getOrderDetail(Integer memberId, String orderSn);
	
	// 获取订单状态的日、周统计数据
	public Response gymGetOrderListHeader(Integer status);
	
	// 查商户用户数
	public Long getGymMemberCount(Integer gymId);
		
	// 查商户活跃用户数
	public Long getGymActiveMemberCount(Integer gymId, Date beginTime);
		
	// 查商户关联用户冻结数
	public Long getGymFrozenMemberCount(Integer gymId);
}
