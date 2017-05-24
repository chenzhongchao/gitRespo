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

public class OrderCreateParam implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@JsonProperty("member_id")
	@NotNull
	private Integer memberId;
	
	@JsonProperty("gym_id")
	@NotNull
	private Integer gymId;
	
	private Long depositAmount;
	
	private Long orderAmount;
	
	private Long preBalance;
	
	private Long postBalance;
	
	@JsonProperty("order_gym_item_list")
	@NotNull
	private List<OrderGymItemDto> orderGymItemList;
		
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
	public Long getDepositAmount() {
		return depositAmount;
	}
	public void setDepositAmount(Long depositAmount) {
		this.depositAmount = depositAmount;
	}
	public Long getOrderAmount() {
		return orderAmount;
	}
	public void setOrderAmount(Long orderAmount) {
		this.orderAmount = orderAmount;
	}
	public Long getPreBalance() {
		return preBalance;
	}
	public void setPreBalance(Long preBalance) {
		this.preBalance = preBalance;
	}
	public Long getPostBalance() {
		return postBalance;
	}
	public void setPostBalance(Long postBalance) {
		this.postBalance = postBalance;
	}
	public List<OrderGymItemDto> getOrderGymItemList() {
		return orderGymItemList;
	}
	public void setOrderGymItemList(List<OrderGymItemDto> orderGymItemList) {
		this.orderGymItemList = orderGymItemList;
	}	

	@Override
	public String toString() {
		return JsonUtil.toJson(this);
	}

}
