package com.fise.model.param;

import java.io.Serializable;

import com.fise.framework.annotation.MaxLength;
import com.fise.framework.annotation.NotEmpty;
import com.fise.utils.JsonUtil;

/** 
 * @author 大表哥
 * @email xingyuanzuo@gmail.com
 * @date 2016-8-5
 * @desc app初始化参数对象
 */

public class InitParam implements Serializable {
	private static final long serialVersionUID = 1L;

	@NotEmpty
	@MaxLength(value = 50)
	private String cmd;

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
