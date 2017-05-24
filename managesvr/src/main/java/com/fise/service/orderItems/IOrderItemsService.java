package com.fise.service.orderItems;

import java.util.List;

import com.fise.model.entity.Order;
import com.fise.model.entity.OrderItems;

public interface IOrderItemsService {
	// 生成orderItems
	public void saveOrderItems(List<OrderItems> orderItemsList);
	
	// 查询orderItems
	public List<OrderItems> getOrderItemsByOrderId(Long orderId);
	
	// 更新订单项
	public void updateOrderItems(List<OrderItems> orderItemsList);
}
