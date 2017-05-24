package com.fise.model.param;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fise.framework.annotation.MaxLength;
import com.fise.framework.annotation.NotEmpty;
import com.fise.model.dto.GymAddGymItemDto;
import com.fise.model.dto.GymImgsDescDto;
import com.fise.model.dto.OrderGymItemDto;
import com.fise.utils.JsonUtil;

/** 
 * @author 大表哥
 * @email xingyuanzuo@gmail.com
 * @date 2016-8-1
 * @desc 生成订单参数对象
 */

public class OrderPayParam implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@JsonProperty("member_id")
	@NotNull
	private Integer memberId;
	
	@JsonProperty("gym_id")
	@NotNull
	private Integer gymId;
	
	@JsonProperty("order_sn")
	@NotEmpty
	@MaxLength(value = 50)
	private String orderSn;
	
	@JsonProperty("pay_password")
	@NotEmpty
	@MaxLength(value = 50)
	private String payPassword;
		
	public String getPayPassword() {
		return payPassword;
	}
	public void setPayPassword(String payPassword) {
		this.payPassword = payPassword;
	}
	public Integer getMemberId() {
		return memberId;
	}
	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}
	public Integer getGymId() {
		return gymId;
	}
	public void setGymId(Integer gymId) {
		this.gymId = gymId;
	}
	public String getOrderSn() {
		return orderSn;
	}
	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}
	
	@Override
	public String toString() {
		return JsonUtil.toJson(this);
	}
}
