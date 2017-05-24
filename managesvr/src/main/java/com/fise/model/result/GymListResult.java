package com.fise.model.result;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fise.base.Page;
import com.fise.model.entity.Gym;

public class GymListResult implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@JsonProperty("gym_list")
	Page<Gym> gmyList;

	public Page<Gym> getGmyList() {
		return gmyList;
	}

	public void setGmyList(Page<Gym> gmyList) {
		this.gmyList = gmyList;
	}
}
