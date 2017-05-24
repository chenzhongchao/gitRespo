package com.fise.model.param;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fise.framework.annotation.MaxLength;
import com.fise.framework.annotation.NotEmpty;

/** 
 * @author 大表哥
 * @email xingyuanzuo@gmail.com
 * @date 2016-7-27
 * @desc 后台管理系统Logout参数对象
 */

public class ManagerLogoutParam implements Serializable {
	private static final long serialVersionUID = 1L;

	@JsonProperty("manager_id")
	private Integer managerId;
	
	public Integer getManagerId() {
		return managerId;
	}
	public void setManagerId(Integer managerId) {
		this.managerId = managerId;
	}
}
