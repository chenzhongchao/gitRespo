package com.fise.model.param;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fise.framework.annotation.MaxLength;
import com.fise.framework.annotation.NotEmpty;
import com.fise.utils.JsonUtil;

/** 
 * @author 大表哥
 * @email xingyuanzuo@gmail.com
 * @date 2016-8-9
 * @desc 支付宝充值参数对象
 */

public class AlipayParam implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@NotNull
	@JsonProperty("member_id")
	private Integer memberId;
	
	@NotNull
	@JsonProperty("charge_amount")
	private Long chargeAmount;

	public Integer getMemberId() {
		return memberId;
	}
	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}
	public Long getChargeAmount() {
		return chargeAmount;
	}
	public void setChargeAmount(Long chargeAmount) {
		this.chargeAmount = chargeAmount;
	}
	
	@Override
	public String toString() {
		return JsonUtil.toJson(this);
	}
}
