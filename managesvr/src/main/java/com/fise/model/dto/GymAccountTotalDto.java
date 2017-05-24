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

public class GymAccountTotalDto implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@JsonProperty("total_frozen_balance")
	private Long totalFrozenBalance;			// 冻结金额
	
	@JsonProperty("total_available_balance")
    private Long totalAvailableBalance;			// 余额
	
	@JsonProperty("total_wait_balance")
    private Long totalWaitBalance;				// 待结算金额
	
	@JsonProperty("total_settled_balance")
    private Long totalSettledBalance;			// 已结算金额
	
	@JsonProperty("total_balance")
	private Long totalBalance;					// 总结额
	
	public Long getTotalFrozenBalance() {
		return totalFrozenBalance;
	}
	public void setTotalFrozenBalance(Long totalFrozenBalance) {
		this.totalFrozenBalance = totalFrozenBalance;
	}
	public Long getTotalAvailableBalance() {
		return totalAvailableBalance;
	}
	public void setTotalAvailableBalance(Long totalAvailableBalance) {
		this.totalAvailableBalance = totalAvailableBalance;
	}
	public Long getTotalWaitBalance() {
		return totalWaitBalance;
	}
	public void setTotalWaitBalance(Long totalWaitBalance) {
		this.totalWaitBalance = totalWaitBalance;
	}
	public Long getTotalBalance() {
		return totalBalance;
	}
	public void setTotalBalance(Long totalBalance) {
		this.totalBalance = totalBalance;
	}
	public Long getTotalSettledBalance() {
		return totalSettledBalance;
	}
	public void setTotalSettledBalance(Long totalSettledBalance) {
		this.totalSettledBalance = totalSettledBalance;
	}
	
	@Override
	public String toString(){
		return JsonUtil.toJson(this);
	}
}
