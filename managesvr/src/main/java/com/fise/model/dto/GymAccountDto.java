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

public class GymAccountDto implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@JsonProperty("gym_id")
	private Integer gymId ;
	
	@JsonProperty("gym_name")
	private String gymName;

	@JsonProperty("frozen_balance")
	private Long frozenBalance;			// 冻结金额
	
	@JsonProperty("available_balance")
    private Long availableBalance;		// 已结算金额
	
	@JsonProperty("wait_balance")
    private Long waitBalance;			// 待结算金额
	
	@JsonProperty("total_balance")
	private Long totalBalance;			// 总结额
	
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
	public Long getFrozenBalance() {
		return frozenBalance;
	}
	public void setFrozenBalance(Long frozenBalance) {
		this.frozenBalance = frozenBalance;
	}
	public Long getAvailableBalance() {
		return availableBalance;
	}
	public void setAvailableBalance(Long availableBalance) {
		this.availableBalance = availableBalance;
	}
	public Long getWaitBalance() {
		return waitBalance;
	}
	public void setWaitBalance(Long waitBalance) {
		this.waitBalance = waitBalance;
	}
	public Long getTotalBalance() {
		return totalBalance;
	}
	public void setTotalBalance(Long totalBalance) {
		this.totalBalance = totalBalance;
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
