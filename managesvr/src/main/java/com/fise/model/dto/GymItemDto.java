package com.fise.model.dto;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fise.utils.JsonUtil;

/** 
 * @author 大表哥
 * @email xingyuanzuo@gmail.com
 * @date 2016-8-3
 * @desc 健身项目传输实体类
 */

public class GymItemDto implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@JsonProperty("gym_item_id")
	private Integer gymItemId;

	@JsonProperty("gym_item_name")
    private String gymItemName;
	
	@JsonProperty("gym_item_price")
    private Long gymItemPrice;
    
	@JsonProperty("gym_id")
	private Integer gymId;
	
	private Integer status;
	
	public Integer getGymItemId() {
		return gymItemId;
	}
	public void setGymItemId(Integer gymItemId) {
		this.gymItemId = gymItemId;
	}
	public String getGymItemName() {
		return gymItemName;
	}
	public void setGymItemName(String gymItemName) {
		this.gymItemName = gymItemName;
	}
	public Long getGymItemPrice() {
		return gymItemPrice;
	}
	public void setGymItemPrice(Long gymItemPrice) {
		this.gymItemPrice = gymItemPrice;
	}
	public Integer getGymId() {
		return gymId;
	}
	public void setGymId(Integer gymId) {
		this.gymId = gymId;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	@Override
	public String toString(){
		return JsonUtil.toJson(this);
	}
}
