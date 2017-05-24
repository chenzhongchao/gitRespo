package com.fise.model.result;

import java.io.Serializable;
import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fise.utils.JsonUtil;

public class MemberStatTotalResult implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@JsonProperty("member_total_count")
	private Long memberTotalCount;
	
	@JsonProperty("member_active_count")
	private Long memberActiveCount;
	
	@JsonProperty("member_frozen_count")
	private Long memberFrozenCount;
	
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
	
	@Override
	public String toString() {
		return JsonUtil.toJson(this);
	}
}
