package com.fise.model.param;

import java.io.Serializable;
import java.util.Date;

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

public class AppStatParam implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@MaxLength(value = 10)
	private String cmd;
	
	@NotNull
	@JsonProperty("stat_type")
	private Integer statType;
	
	@NotNull
	@JsonProperty("start_time")
	private Date startTime;
	
	@NotNull
	@JsonProperty("end_time")
	private Date endTime;
	
	@JsonProperty("compare_start_time")
	private Date compareStartTime;
	
	@JsonProperty("compare_end_time")
	private Date compareEndTime;

	public String getCmd() {
		return cmd;
	}
	public void setCmd(String cmd) {
		this.cmd = cmd;
	}
	public Integer getStatType() {
		return statType;
	}
	public void setStatType(Integer statType) {
		this.statType = statType;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public Date getCompareStartTime() {
		return compareStartTime;
	}
	public void setCompareStartTime(Date compareStartTime) {
		this.compareStartTime = compareStartTime;
	}
	public Date getCompareEndTime() {
		return compareEndTime;
	}
	public void setCompareEndTime(Date compareEndTime) {
		this.compareEndTime = compareEndTime;
	}
	
	@Override
	public String toString() {
		return JsonUtil.toJson(this);
	}
}
