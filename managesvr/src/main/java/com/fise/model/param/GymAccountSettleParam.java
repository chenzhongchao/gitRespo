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
 * @date 2016-8-13
 * @desc 平台商户结算参数对象
 */

public class GymAccountSettleParam implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@JsonProperty("gym_id")
	@NotNull
	private Integer gymId;
	
	@JsonProperty("settle_amount")
	@NotNull
	private Long settleAmount;
	
	public Integer getGymId() {
		return gymId;
	}
	public void setGymId(Integer gymId) {
		this.gymId = gymId;
	}
	public Long getSettleAmount() {
		return settleAmount;
	}
	public void setSettleAmount(Long settleAmount) {
		this.settleAmount = settleAmount;
	}

	@Override
	public String toString() {
		return JsonUtil.toJson(this);
	}
}
