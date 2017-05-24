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

public class OrderDetailResult implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@JsonProperty("order_sn")
	private String orderSn;
	
	private Integer status;
	
	@JsonProperty("gym_name")
	private String gymName;
	
	@JsonProperty("pay_amount")
	private Long payAmount;
	
	@JsonProperty("payment_time")
	private Date paymentTime;
	
	@JsonProperty("payment_name")
	private String paymentName;
	
	@JsonProperty("order_item_list")
	private List<OrderGymItemDto> orderItemList;
	
	@JsonProperty("deposit_amount")
	private Long depositAmount;
	
	@JsonProperty("user_deposit")
	private boolean userDeposit;
	
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
	public String getGymName() {
		return gymName;
	}
	public void setGymName(String gymName) {
		this.gymName = gymName;
	}
	public Long getPayAmount() {
		return payAmount;
	}
	public void setPayAmount(Long payAmount) {
		this.payAmount = payAmount;
	}
	public Date getPaymentTime() {
		return paymentTime;
	}
	public void setPaymentTime(Date paymentTime) {
		this.paymentTime = paymentTime;
	}
	public String getPaymentName() {
		return paymentName;
	}
	public void setPaymentName(String paymentName) {
		this.paymentName = paymentName;
	}
	public List<OrderGymItemDto> getOrderItemList() {
		return orderItemList;
	}
	public void setOrderItemList(List<OrderGymItemDto> orderItemList) {
		this.orderItemList = orderItemList;
	}
	public Long getDepositAmount() {
		return depositAmount;
	}
	public void setDepositAmount(Long depositAmount) {
		this.depositAmount = depositAmount;
	}
	public boolean getUserDeposit() {
		return userDeposit;
	}
	public void setUserDeposit(boolean userDeposit) {
		this.userDeposit = userDeposit;
	}
	
	@Override
	public String toString() {
		return JsonUtil.toJson(this);
	}
}
