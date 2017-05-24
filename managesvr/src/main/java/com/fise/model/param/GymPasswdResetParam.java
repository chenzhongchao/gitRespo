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
 * @desc 修改密码参数对象
 */

public class GymPasswdResetParam implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@JsonProperty("gym_id")
	@NotNull
	private Integer gymId;
	
	@JsonProperty("old_passwd")
	@MaxLength(value = 50)
	private String oldPassword;
	
	@JsonProperty("new_passwd")
	@NotEmpty
	@MaxLength(value = 50)
	private String newPassword;
	
	@JsonProperty("new_plain_passwd")
	@NotEmpty
	@MaxLength(value = 50)
	private String newPlainPassword;
	
	public Integer getGymId() {
		return gymId;
	}
	public void setGymId(Integer gymId) {
		this.gymId = gymId;
	}
	public String getOldPassword() {
		return oldPassword;
	}
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	public String getNewPlainPassword() {
		return newPlainPassword;
	}
	public void setNewPlainPassword(String newPlainPassword) {
		this.newPlainPassword = newPlainPassword;
	}

	@Override
	public String toString() {
		return JsonUtil.toJson(this);
	}
}
