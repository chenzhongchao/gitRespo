package com.fise.model.result;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginResult implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@JsonProperty("member_id")
	private Integer memberId;
	
	@JsonProperty("last_login")
	private Date lastLogin;
	
	@JsonProperty("access_token")
	private String accessToken;
	
	private String mobile;
	
	@JsonProperty("available_balance")
	private Long availableBalance;
	
	private String avatar;
	
	@JsonProperty("nick_name")
	private String nickName;
	
	@JsonProperty("has_login_passwd")
	private boolean hasLoginPasswd;
	
	@JsonProperty("has_account_passwd")
	private boolean hasAccountPasswd;
	
	private Date birthday;
	
	private Double height;
	
	private Double weight;
	
	private Integer sex;
	
	public Long getAvailableBalance() {
		return availableBalance;
	}
	public void setAvailableBalance(Long availableBalance) {
		this.availableBalance = availableBalance;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public boolean isHasLoginPasswd() {
		return hasLoginPasswd;
	}
	public void setHasLoginPasswd(boolean hasLoginPasswd) {
		this.hasLoginPasswd = hasLoginPasswd;
	}
	public boolean isHasAccountPasswd() {
		return hasAccountPasswd;
	}
	public void setHasAccountPasswd(boolean hasAccountPasswd) {
		this.hasAccountPasswd = hasAccountPasswd;
	}
	public Integer getMemberId() {
		return memberId;
	}
	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
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
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public Double getHeight() {
		return height;
	}
	public void setHeight(Double height) {
		this.height = height;
	}
	public Double getWeight() {
		return weight;
	}
	public void setWeight(Double weight) {
		this.weight = weight;
	}
	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}
}
