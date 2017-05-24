package com.fise.model.result;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fise.utils.JsonUtil;

public class GymMemberStatResult implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@JsonProperty("gym_id")
	private Integer gymId;
	
	@JsonProperty("gym_name")
	private String gymName;
	
	@JsonProperty("member_total_count")
	private Long memberTotalCount;
	
	@JsonProperty("member_active_count")
	private Long memberActiveCount;
	
	@JsonProperty("member_frozen_count")
	private Long memberFrozenCount;
	
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
	public Long getMemberTotalCount() {
		return memberTotalCount;
	}
	public void setMemberTotalCount(Long memberTotalCount) {
		this.memberTotalCount = memberTotalCount;
	}
	public Long getMemberActiveCount() {
		return memberActiveCount;
	}
	public void setMemberActiveCount(Long memberActiveCount) {
		this.memberActiveCount = memberActiveCount;
	}
	public Long getMemberFrozenCount() {
		return memberFrozenCount;
	}
	public void setMemberFrozenCount(Long memberFrozenCount) {
		this.memberFrozenCount = memberFrozenCount;
	}
	public Integer getLoginId() {
		return loginId;
	}
	public void setLoginId(Integer loginId) {
		this.loginId = loginId;
	}
	
	@Override
	public String toString() {
		return JsonUtil.toJson(this);
	}
}
