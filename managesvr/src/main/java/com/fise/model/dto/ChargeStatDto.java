package com.fise.model.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fise.utils.JsonUtil;

/** 
 * @author 大表哥
 * @email xingyuanzuo@gmail.com
 * @date 2016-8-17
 * @desc 充值统计传输类
 */

public class ChargeStatDto implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String period;
	
	private Long amount;	
	
	public String getPeriod() {
		return period;
	}
	public void setPeriod(String period) {
		this.period = period;
	}
	public Long getAmount() {
		return amount;
	}
	public void setAmount(Long amount) {
		this.amount = amount;
	}
	
	@Override
	public String toString(){
		return JsonUtil.toJson(this);
	}
}
