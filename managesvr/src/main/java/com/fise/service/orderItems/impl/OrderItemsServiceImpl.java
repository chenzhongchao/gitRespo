package com.fise.service.orderItems.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fise.dao.OrderItemsMapper;
import com.fise.model.entity.Order;
import com.fise.model.entity.OrderItems;
import com.fise.service.orderItems.IOrderItemsService;

@Service
public class OrderItemsServiceImpl implements IOrderItemsService {
	@Autowired
	private OrderItemsMapper orderItemsDao;

	@Override
	public void saveOrderItems(List<OrderItems> orderItemsList) {
		for(OrderItems orderItems : orderItemsList) {
			orderItemsDao.insertOrderItems(orderItems);
		}
		
		return ;
	}

	@Override
	public List<OrderItems> getOrderItemsByOrderId(Long orderId) {
		return orderItemsDao.selectByOrderId(orderId);
	}

	@Override
	public void updateOrderItems(List<OrderItems> orderItemsList) {
		for (OrderItems orderItems : orderItemsList) {
			orderItemsDao.updateByPrimaryKeySelective(orderItems);
		}
	}

}
