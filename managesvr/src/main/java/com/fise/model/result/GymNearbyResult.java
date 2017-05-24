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

public class GymNearbyResult implements Serializable {
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

	private String avatar;
	
	private Double distance;
	
	private Float score;
	
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
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public Double getDistance() {
		return distance;
	}
	public void setDistance(Double distance) {
		this.distance = distance;
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
	
	@Override
	public String toString() {
		return JsonUtil.toJson(this);
	}
}
