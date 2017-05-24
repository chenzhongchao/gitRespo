package com.fise.model.dto;

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
 * @date 2016-8-3
 * @desc 健身项目传输实体类
 */

public class GymItemUpdateDto implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@JsonProperty("gym_item_id")
	private Integer gymItemId;

	@NotEmpty
	@MaxLength(value = 255)
	@JsonProperty("gym_item_name")
    private String gymItemName;
	
	@JsonProperty("gym_item_price")
	@NotNull
    private Long gymItemPrice;
	
	@NotEmpty
	@MaxLength(value = 20)
	private String cmd;			// "U".修改 "A".新增 "D".删除
	
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
	public String getCmd() {
		return cmd;
	}
	public void setCmd(String cmd) {
		this.cmd = cmd;
	}
	
	@Override
	public String toString(){
		return JsonUtil.toJson(this);
	}
}
