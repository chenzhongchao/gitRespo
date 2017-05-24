package com.fise.model.result;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fise.base.Page;
import com.fise.model.entity.Gym;
import com.fise.utils.JsonUtil;

public class GymAccountSettleTotalResult implements Serializable {
	private static final long serialVersionUID = 1L;

	@JsonProperty("total_wait_balance")
	private Long totalWaitBalance;
	
	@JsonProperty("total_service_charge")
	private Long totalServiceCharge;
	
	@JsonProperty("total_actual_settle_balance")
	private Long totalActualSettle;
	
	public Long getTotalWaitBalance() {
		return totalWaitBalance;
	}
	public void setTotalWaitBalance(Long totalWaitBalance) {
		this.totalWaitBalance = totalWaitBalance;
	}
	public Long getTotalServiceCharge() {
		return totalServiceCharge;
	}
	public void setTotalServiceCharge(Long totalServiceCharge) {
		this.totalServiceCharge = totalServiceCharge;
	}
	public Long getTotalActualSettle() {
		return totalActualSettle;
	}
	public void setTotalActualSettle(Long totalActualSettle) {
		this.totalActualSettle = totalActualSettle;
	}

	@Override
	public String toString() {
		return JsonUtil.toJson(this);
	}
}
