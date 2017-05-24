package com.fise.model.param;

import java.io.Serializable;

import com.fise.framework.annotation.MaxLength;
import com.fise.framework.annotation.NotEmpty;
import com.fise.utils.JsonUtil;

/** 
 * @author 大表哥
 * @email xingyuanzuo@gmail.com
 * @date 2016-8-5
 * @desc SmsFetch参数对象
 */

public class SmsFetchParam implements Serializable {
	private static final long serialVersionUID = 1L;

	@NotEmpty
	@MaxLength(value = 20)
	private String mobile;
	
	@NotEmpty
	@MaxLength(value = 50)
	private String cmd;
	
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
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
