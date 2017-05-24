package com.fise.model.result;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fise.base.Page;
import com.fise.model.dto.AppVersionDto;
import com.fise.model.entity.Gym;
import com.fise.utils.JsonUtil;

public class InitAppResult implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@JsonProperty("app_version")
	private AppVersionDto appVersionDto;
	
	@JsonProperty("extra")
	private Map<String, Object> extraMap;
	
	public AppVersionDto getAppVersionDto() {
		return appVersionDto;
	}
	public void setAppVersionDto(AppVersionDto appVersionDto) {
		this.appVersionDto = appVersionDto;
	}
	public Map<String, Object> getExtraMap() {
		return extraMap;
	}
	public void setExtraMap(Map<String, Object> extraMap) {
		this.extraMap = extraMap;
	}
	
	@Override
	public String toString() {
		return JsonUtil.toJson(this);
	}
}
