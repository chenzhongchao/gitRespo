package com.fise.model.param;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fise.framework.annotation.MaxLength;
import com.fise.framework.annotation.NotEmpty;
import com.fise.utils.JsonUtil;

/** 
 * @author 大表哥
 * @email xingyuanzuo@gmail.com
 * @date 2016-7-26
 * @desc 商户端Login参数对象
 */

public class GymLoginParam implements Serializable {
	private static final long serialVersionUID = 1L;

	@JsonProperty("gym_name")
	@MaxLength(value = 255)
	private String gymName;
	
	@NotEmpty
	@MaxLength(value = 50)
	private String password;
	
	@JsonProperty("login_id")
	private Integer loginId;
	
	public String getGymName() {
		return gymName;
	}
	public void setGymName(String gymName) {
		this.gymName = gymName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Integer getLoginId() {
		return loginId;
	}
	public void setLoginId(Integer loginId) {
		this.loginId = loginId;
	}
	
	@Override
	public String toString() {
		return JsonUtil.toJson(this);
	}
}
