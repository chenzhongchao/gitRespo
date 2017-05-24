package com.fise.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.fise.model.entity.OrderItems;

public interface OrderItemsMapper {
    // 生成orderItems
	public void insertOrderItems(OrderItems orderItems);
	
	// 通过orderId查询record
	public List<OrderItems> selectByOrderId(@Param("orderId") Long orderId);
	
	// 更新订单项
	public void updateByPrimaryKeySelective(OrderItems orderItems);
}
