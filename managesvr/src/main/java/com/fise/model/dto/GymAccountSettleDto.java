package com.fise.model.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fise.utils.JsonUtil;

/** 
 * @author 大表哥
 * @email xingyuanzuo@gmail.com
 * @date 2016-7-28
 * @desc 商户账户传输类
 */

public class GymAccountSettleDto implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@JsonProperty("gym_id")
	private Integer gymId ;
	
	@JsonProperty("gym_name")
	private String gymName;
	
	@JsonProperty("wait_balance")
    private Long waitBalance;			// 待结算金额
	
	@JsonProperty("service_charge_rate")
	private Integer serviceChargeRate;	// 服务费率
	
	@JsonProperty("actual_settle_balance")
	private Long actualSettleBalance;	// 应结算金额
	
	@JsonProperty("login_id")
	private Integer loginId;
	
	public Integer getGymId() {
		return gymId;
	}
	public void setGymId(Integer gymId) {
		this.gymId = gymId;
	}
	public String getGymName() {
		return gymName;
	}
	public void setGymName(String gymName) {
		this.gymName = gymName;
	}
	public Long getWaitBalance() {
		return waitBalance;
	}
	public void setWaitBalance(Long waitBalance) {
		this.waitBalance = waitBalance;
	}
	public Integer getServiceChargeRate() {
		return serviceChargeRate;
	}
	public void setServiceChargeRate(Integer serviceChargeRate) {
		this.serviceChargeRate = serviceChargeRate;
	}
	public Long getActualSettleBalance() {
		return actualSettleBalance;
	}
	public void setActualSettleBalance(Long actualSettleBalance) {
		this.actualSettleBalance = actualSettleBalance;
	}
	public Integer getLoginId() {
		return loginId;
	}
	public void setLoginId(Integer loginId) {
		this.loginId = loginId;
	}
	
	@Override
	public String toString(){
		return JsonUtil.toJson(this);
	}
}
