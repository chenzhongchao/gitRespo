package com.fise.model.param;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fise.framework.annotation.MaxLength;
import com.fise.framework.annotation.NotEmpty;
import com.fise.model.dto.GymImgsDescDto;
import com.fise.utils.JsonUtil;

/** 
 * @author 大表哥
 * @email xingyuanzuo@gmail.com
 * @date 2016-7-28
 * @desc 添加健身馆参数对象
 */

public class GymStatusParam implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@JsonProperty("gym_id")
	private Integer gymId;
	
	public Integer getGymId() {
		return gymId;
	}
	
	public void setGymId(Integer gymId) {
		this.gymId = gymId;
	}


	@Override
	public String toString() {
		return JsonUtil.toJson(this);
	}
}
