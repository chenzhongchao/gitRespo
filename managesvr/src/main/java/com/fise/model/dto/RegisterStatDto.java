package com.fise.model.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fise.utils.JsonUtil;

/** 
 * @author 大表哥
 * @email xingyuanzuo@gmail.com
 * @date 2016-8-14
 * @desc app版本传输类
 */

public class RegisterStatDto implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String period;
	
	private Integer count;	
	
	public String getPeriod() {
		return period;
	}
	public void setPeriod(String period) {
		this.period = period;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	
	@Override
	public String toString(){
		return JsonUtil.toJson(this);
	}
}
