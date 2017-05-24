package com.fise.model.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fise.utils.JsonUtil;

/** 
 * @author 大表哥
 * @email xingyuanzuo@gmail.com
 * @date 2016-8-20
 * @desc 健身馆存es传输类
 */

public class GymESIndexDto implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer gymId;
	
	private String gymName;
	
	private Long minSpendAmount;
	
	private String address;
	
	private String avatar;
	
	private List<Double> location;
	
	public Integer getGymId() {
		return gymId;
	}
	public void setGymId(Integer gymId) {
		this.gymId = gymId;
	}
	public String getGymName() {
		return gymName;
	}
	public void setGymName(String gymName) {
		this.gymName = gymName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Long getMinSpendAmount() {
		return minSpendAmount;
	}
	public void setMinSpendAmount(Long minSpendAmount) {
		this.minSpendAmount = minSpendAmount;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public List<Double> getLocation() {
		return location;
	}
	public void setLocation(List<Double> location) {
		this.location = location;
	}
	
	@Override
	public String toString(){
		return JsonUtil.toJson(this);
	}
}
