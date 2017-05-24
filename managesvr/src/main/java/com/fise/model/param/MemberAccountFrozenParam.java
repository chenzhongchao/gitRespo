package com.fise.model.param;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fise.framework.annotation.MaxLength;
import com.fise.framework.annotation.NotEmpty;
import com.fise.model.dto.GymImgsDescDto;
import com.fise.utils.JsonUtil;

/** 
 * @author 大表哥
 * @email xingyuanzuo@gmail.com
 * @date 2016-8-12
 * @desc 冻结用户账户金额参数对象
 */

public class MemberAccountFrozenParam implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@JsonProperty("member_id")
	@NotNull
	private Integer memberId;
	
	@JsonProperty("frozen_amount")
	@NotNull
	private Long frozenAmount;
	
	public Integer getMemberId() {
		return memberId;
	}
	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}
	public Long getFrozenAmount() {
		return frozenAmount;
	}
	public void setFrozenAmount(Long frozenAmount) {
		this.frozenAmount = frozenAmount;
	}

	@Override
	public String toString() {
		return JsonUtil.toJson(this);
	}
}
