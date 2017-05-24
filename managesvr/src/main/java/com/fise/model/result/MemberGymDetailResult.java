package com.fise.model.result;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fise.base.Page;
import com.fise.model.dto.GymDto;
import com.fise.model.dto.GymItemDto;
import com.fise.model.entity.Gym;
import com.fise.model.entity.GymItem;
import com.fise.utils.JsonUtil;

public class MemberGymDetailResult implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@JsonProperty("gym_id")
	private Integer gymId ;
	
	@JsonProperty("gym_name")
	private String gymName;
	
	@JsonProperty("min_spend_amount")
	private Long minSpendAmount;
	
	private String address;
	
	private Double longitude;
	
	private Double latitude;
	
	private String tel;
	
	private Integer status;
	
	private String avatar;
	
	private String introduction;
	
	private String broadcast;
	
	private Float score;
	
	@JsonProperty("img_introductions")
	private String imgIntroductions;
	
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
	public Long getMinSpendAmount() {
		return minSpendAmount;
	}
	public void setMinSpendAmount(Long minSpendAmount) {
		this.minSpendAmount = minSpendAmount;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public String getIntroduction() {
		return introduction;
	}
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	public String getBroadcast() {
		return broadcast;
	}
	public void setBroadcast(String broadcast) {
		this.broadcast = broadcast;
	}
	public Float getScore() {
		return score;
	}
	public void setScore(Float score) {
		if (score == null) {
			this.score = 5.0F;
		} else {
			this.score = score;
		}
	}
	public String getImgIntroductions() {
		return imgIntroductions;
	}
	public void setImgIntroductions(String imgIntroductions) {
		this.imgIntroductions = imgIntroductions;
	}
	
	@Override
	public String toString() {
		return JsonUtil.toJson(this);
	}
}
