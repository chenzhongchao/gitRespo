package com.fise.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.fise.base.Page;
import com.fise.model.entity.Order;

public interface OrderMapper {
	// 生成订单
	public void insertOrder(Order order);
	
	// 根据order sn取order
	public Order selectBySn(String sn);
	
	// 根据order_sn, member_id, gym_id查订单
	public Order selectByOrder(Order order);
	
	// 更新订单状态
	public void updateOrderStatus(Order order);
	
	// 更新订单
	public void updateByPrimaryKeySelective(Order order);
	
	// 获取Order列表
	public List<Order> selectListByOrder(@Param("order")Order order, @Param("page")Page<Order> page);
	
	// 根据满足条件的订单数
	public List<Order> selectByOrderSelective(@Param("order") Order order);
	
	// 查询特定条件的订单次数
	public Long selectCount(@Param("status") Integer status, @Param("beginTime") Date todayBegin);
	
	// 查询完成的订单次数
	public Long selectCompleteCount(@Param("status") Integer status, @Param("beginTime") Date todayBegin);
	
	// 查商户用户数
	public Long selectGymMemberCount(@Param("gymId") Integer gymId);
	
	// 查商户活跃用户数
	public Long selectGymActiveMemberCount(@Param("gymId") Integer gymId, @Param("beginTime") Date beginTime);
	
	// 查商户关联用户冻结数
	public Long selectGymFrozenMemberCount(@Param("gymId") Integer gymId);
	
	// 获取Order列表
	public List<Order> selectListUnionMeberByOrderAndMember(@Param("order")Order order, @Param("page")Page<Order> page);
}
