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
 * @desc 修改账户支付密码参数对象
 */

public class AccountPasswdResetParam implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@JsonProperty("old_passwd")
	@MaxLength(value = 50)
	private String oldPassword;
	
	@JsonProperty("new_passwd")
	@NotEmpty
	@MaxLength(value = 50)
	private String newPassword;
	
	@JsonProperty("sms_code")
	@MaxLength(value = 10)
	private String smsCode;

	@NotEmpty
	@MaxLength(value = 6)
	private String cmd;			// "reset" 重置支付密码	"newset" 设置支付密码
	
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
	public String getSmsCode() {
		return smsCode;
	}
	public void setSmsCode(String smsCode) {
		this.smsCode = smsCode;
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
