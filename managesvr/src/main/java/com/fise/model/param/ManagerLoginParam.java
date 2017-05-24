package com.fise.model.param;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fise.framework.annotation.MaxLength;
import com.fise.framework.annotation.NotEmpty;

/** 
 * @author 大表哥
 * @email xingyuanzuo@gmail.com
 * @date 2016-7-26
 * @desc 后台管理Login参数对象
 */

public class ManagerLoginParam implements Serializable {
	private static final long serialVersionUID = 1L;

	@JsonProperty("user_name")
	@NotEmpty
	@MaxLength(value = 32)
	private String userName;
	
	@NotEmpty
	@MaxLength(value = 50)
	private String password;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
