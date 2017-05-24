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

public class OrderForGymResult implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@JsonProperty("order_sn")
	private String orderSn;
	
	private Integer status;
	
	@JsonProperty("member_id")
	private Integer memberId;
	
	private String mobile;
	
	private String avatar;
	
	@JsonProperty("nick_name")
	private String nickName;
	
	@JsonProperty("create_time")
	private Date createTime;
	
	@JsonProperty("order_amount")
	private Long orderAmount;
	
	@JsonProperty("deposit_amount")
	private Long depositAmount;
	
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
	public Integer getMemberId() {
		return memberId;
	}
	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
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
	public Long getDepositAmount() {
		return depositAmount;
	}
	public void setDepositAmount(Long depositAmount) {
		this.depositAmount = depositAmount;
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
