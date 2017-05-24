package com.fise.model.result;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fise.utils.JsonUtil;

public class GymProfileResult implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@JsonProperty("gym_id")
	private Integer gymId;
	
	@JsonProperty("last_login")
	private Date lastLogin;
	
	@JsonProperty("access_token")
	private String accessToken;
	
	private String avatar;
	
	@JsonProperty("gym_name")
	private String gymName;
	
	@JsonProperty("deposit_amount")
	private Long depositAmount;

	public Integer getGymId() {
		return gymId;
	}
	public void setGymId(Integer gymId) {
		this.gymId = gymId;
	}
	public Date getLastLogin() {
		return lastLogin;
	}
	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public String getGymName() {
		return gymName;
	}
	public void setGymName(String gymName) {
		this.gymName = gymName;
	}
	public Long getDepositAmount() {
		return depositAmount;
	}
	public void setDepositAmount(Long depositAmount) {
		this.depositAmount = depositAmount;
	}
	
	@Override
	public String toString() {
		return JsonUtil.toJson(this);
	}
}
