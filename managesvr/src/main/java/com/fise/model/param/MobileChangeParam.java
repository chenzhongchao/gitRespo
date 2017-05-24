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
 * @desc 修改账户支付密码参数对象
 */

public class MobileChangeParam implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@NotNull
	private Integer step;			// step 1.验证原手机短信验证码  step 2.验证支付密码    step 3.验证新手机短信验证码 
	
	@JsonProperty("old_mobile")
	private String oldMobile;
	
	@JsonProperty("account_passwd")
	private String accountPasswd;
	
	@JsonProperty("new_mobile")
	private String newMobile;
	
	@JsonProperty("sms_code")
	private String smsCode;
	
	public Integer getStep() {
		return step;
	}
	public void setStep(Integer step) {
		this.step = step;
	}
	public String getOldMobile() {
		return oldMobile;
	}
	public void setOldMobile(String oldMobile) {
		this.oldMobile = oldMobile;
	}
	public String getAccountPasswd() {
		return accountPasswd;
	}
	public void setAccountPasswd(String accountPasswd) {
		this.accountPasswd = accountPasswd;
	}
	public String getNewMobile() {
		return newMobile;
	}
	public void setNewMobile(String newMobile) {
		this.newMobile = newMobile;
	}
	public String getSmsCode() {
		return smsCode;
	}
	public void setSmsCode(String smsCode) {
		this.smsCode = smsCode;
	}
	
	@Override
	public String toString() {
		return JsonUtil.toJson(this);
	}
}
