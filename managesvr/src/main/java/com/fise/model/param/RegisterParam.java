package com.fise.model.param;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fise.framework.annotation.MaxLength;
import com.fise.framework.annotation.NotEmpty;
import com.fise.utils.JsonUtil;

/** 
 * @author 大表哥
 * @email xingyuanzuo@gmail.com
 * @date 2016-7-25
 * @desc Register参数对象
 */

public class RegisterParam implements Serializable {
	private static final long serialVersionUID = 1L;

	@NotEmpty
	@MaxLength(value = 20)
	private String mobile;
	
	@NotEmpty
	@MaxLength(value = 50)
	private String password;
	
	@NotEmpty
	@MaxLength(value = 4)
	@JsonProperty("verify_code")
	private String verifyCode;
	
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getVerifyCode() {
		return verifyCode;
	}
	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}
	
	@Override
	public String toString() {
		return JsonUtil.toJson(this);
	}
}
