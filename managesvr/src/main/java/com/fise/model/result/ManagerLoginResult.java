package com.fise.model.result;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ManagerLoginResult implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@JsonProperty("manager_id")
	private Integer managerId;
	
	@JsonProperty("user_name")
	private String userName;
	
	@JsonProperty("last_login")
	private Date lastLogin;
	
	@JsonProperty("access_token")
	private String accessToken;

	public Integer getManagerId() {
		return managerId;
	}

	public void setManagerId(Integer managerId) {
		this.managerId = managerId;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
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
}
