package com.fise.model.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fise.utils.JsonUtil;

/** 
 * @author 大表哥
 * @email xingyuanzuo@gmail.com
 * @date 2016-8-14
 * @desc app版本传输类
 */

public class AppVersionDto implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@JsonProperty("latest_version")
	private String latestVersion;
	
	@JsonProperty("need_update")
	private boolean needUpadate;
	
	@JsonProperty("is_force")
	private boolean isForce;
	
	@JsonProperty("download_url")
	private String downloadUrl;
	
	public String getLatestVersion() {
		return latestVersion;
	}
	public void setLatestVersion(String latestVersion) {
		this.latestVersion = latestVersion;
	}
	public boolean isNeedUpadate() {
		return needUpadate;
	}
	public void setNeedUpadate(boolean needUpadate) {
		this.needUpadate = needUpadate;
	}
	public boolean getIsForce() {
		return isForce;
	}
	public void setIsForce(boolean isForce) {
		this.isForce = isForce;
	}
	public String getDownloadUrl() {
		return downloadUrl;
	}
	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}
	
	@Override
	public String toString(){
		return JsonUtil.toJson(this);
	}
}
