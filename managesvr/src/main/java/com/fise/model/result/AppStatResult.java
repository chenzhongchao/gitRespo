package com.fise.model.result;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fise.model.dto.ChargeStatDto;
import com.fise.model.dto.RegisterStatDto;
import com.fise.utils.JsonUtil;

public class AppStatResult implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@JsonProperty("reg_stat_list")
	private List<RegisterStatDto> regStat;
	
	@JsonProperty("charge_stat_list")
	private List<ChargeStatDto> chargeStat;
	
	@JsonProperty("cost_stat_list")
	private List<ChargeStatDto> costStat;
	
	@JsonProperty("reg_total_count")
	private Integer regTotalCount;
	
	@JsonProperty("charget_total_amount")
	private Long chargeTotalAmount;
	
	@JsonProperty("cost_total_amount")
	private Long costTotalAmount;
	
	public Integer getRegTotalCount() {
		return regTotalCount;
	}
	public void setRegTotalCount(Integer regTotalCount) {
		if (regTotalCount == null) { 
			this.regTotalCount = 0;
		}
		else {
			this.regTotalCount = regTotalCount;
		}
	}
	public Long getChargeTotalAmount() {
		return chargeTotalAmount;
	}
	public void setChargeTotalAmount(Long chargeTotalAmount) {
		if (chargeTotalAmount == null)
		{
			this.chargeTotalAmount = 0L;
		} else {
			this.chargeTotalAmount = chargeTotalAmount;
		}
	}
	public Long getCostTotalAmount() {
		return costTotalAmount;
	}
	public void setCostTotalAmount(Long costTotalAmount) {
		if (costTotalAmount == null) {
			this.costTotalAmount = 0L;
		} else {
			this.costTotalAmount = costTotalAmount;
		}
	}
	public List<RegisterStatDto> getRegStat() {
		return regStat;
	}
	public void setRegStat(List<RegisterStatDto> regStat) {
		this.regStat = regStat;
	}
	public List<ChargeStatDto> getChargeStat() {
		return chargeStat;
	}
	public void setChargeStat(List<ChargeStatDto> chargeStat) {
		this.chargeStat = chargeStat;
	}
	public List<ChargeStatDto> getCostStat() {
		return costStat;
	}
	public void setCostStat(List<ChargeStatDto> costStat) {
		this.costStat = costStat;
	}
	
	@Override
	public String toString() {
		return JsonUtil.toJson(this);
	}
}
