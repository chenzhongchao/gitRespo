package com.fise.model.dto;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fise.framework.annotation.MaxLength;
import com.fise.utils.JsonUtil;

/** 
 * @author 大表哥
 * @email xingyuanzuo@gmail.com
 * @date 2016-7-28
 * @desc 健身项目传输实体类
 */

public class GymAddGymItemDto implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@JsonProperty("gym_item_name")
	@NotEmpty
	@MaxLength(value = 255)
    private String gymItemName;
	
	@JsonProperty("gym_item_price")
	@NotNull
    private Long gymItemPrice;
    
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
	
	@Override
	public String toString(){
		return JsonUtil.toJson(this);
	}
}
