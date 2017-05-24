package com.fise.model.param;

import java.io.Serializable;
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
 * @date 2016-7-29
 * @desc 用户状态对象
 */

public class MemberStatusParam implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@JsonProperty("member_id")
	private Integer memberId;
	
	public Integer getMemberId() {
		return memberId;
	}
	
	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}


	@Override
	public String toString() {
		return JsonUtil.toJson(this);
	}
}
