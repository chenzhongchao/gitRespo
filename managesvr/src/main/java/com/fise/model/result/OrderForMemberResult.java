package com.fise.model.result;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fise.base.Page;
import com.fise.model.dto.GymDto;
import com.fise.model.dto.GymItemDto;
import com.fise.model.dto.OrderGymItemDto;
import com.fise.model.entity.Gym;
import com.fise.model.entity.GymItem;
import com.fise.model.entity.OrderItems;
import com.fise.utils.JsonUtil;

public class OrderForMemberResult implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@JsonProperty("order_sn")
	private String orderSn;
	
	private Integer status;
	
	@JsonProperty("gym_id")
	private Integer gymId;
	
	@JsonProperty("gym_avatar")
	private String avatar;
	
	@JsonProperty("gym_name")
	private String gymName;
	
	@JsonProperty("create_time")
	private Date createTime;
	
	@JsonProperty("order_amount")
	private Long orderAmount;
	
	@JsonProperty("order_item_list")
	private List<OrderGymItemDto> orderItemList;
	
	public String getOrderSn() {
		return orderSn;
	}
	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getGymId() {
		return gymId;
	}
	public void setGymId(Integer gymId) {
		this.gymId = gymId;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public String getGymName() {
		return gymName;
	}
	public void setGymName(String gymName) {
		this.gymName = gymName;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Long getOrderAmount() {
		return orderAmount;
	}
	public void setOrderAmount(Long orderAmount) {
		this.orderAmount = orderAmount;
	}
	public List<OrderGymItemDto> getOrderItemList() {
		return orderItemList;
	}
	public void setOrderItemList(List<OrderGymItemDto> orderItemList) {
		this.orderItemList = orderItemList;
	}
	
	@Override
	public String toString() {
		return JsonUtil.toJson(this);
	}
}
