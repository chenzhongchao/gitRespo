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

public class ManagerPasswdResetParam implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@JsonProperty("manager_id")
	@NotNull
	private Integer managerId;
	
	@JsonProperty("old_passwd")
	@MaxLength(value = 50)
	private String oldPassword;
	
	@JsonProperty("new_passwd")
	@NotEmpty
	@MaxLength(value = 50)
	private String newPassword;
	
	public Integer getManagerId() {
		return managerId;
	}
	public void setManagerId(Integer managerId) {
		this.managerId = managerId;
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

	@Override
	public String toString() {
		return JsonUtil.toJson(this);
	}
}
