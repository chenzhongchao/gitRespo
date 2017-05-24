package com.fise.model.param;

import java.io.Serializable;

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

public class PasswdResetParam implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@JsonProperty("old_passwd")
	@MaxLength(value = 50)
	private String oldPassword;
	
	@JsonProperty("new_passwd")
	@NotEmpty
	@MaxLength(value = 50)
	private String newPassword;
	
	@MaxLength(value = 20)
	private String mobile;
	
	@MaxLength(value = 4)
	@JsonProperty("verify_code")
	private String verifyCode;
	
	@MaxLength(value = 10)
	private String cmd;				// 命令字  "modify":修改密码   "reset":找回密码(重置)
	
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
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getVerifyCode() {
		return verifyCode;
	}
	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}
	public String getCmd() {
		return cmd;
	}
	public void setCmd(String cmd) {
		this.cmd = cmd;
	}

	@Override
	public String toString() {
		return JsonUtil.toJson(this);
	}
}
