package com.fise.model.dto;

import java.io.Serializable;
import java.sql.Date;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fise.framework.annotation.MaxLength;
import com.fasterxml.jackson.annotation.JsonProperty;

/** 
 * @author 大表哥
 * @email xingyuanzuo@gmail.com
 * @date 2016-7-28
 * @txtDesc 健身馆图文传输类
 */

public class GymImgsDescDto implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@JsonProperty("img_id")
	private Integer imgId;
	
	@JsonProperty("img_url")
	@MaxLength(value = 255)
	private String imgUri;
	
	@NotNull
	private Integer width;
	
	private Integer height;
	
	@JsonProperty("txt_desc")
	@MaxLength(value = 600)
	private String txtDesc;
	
	@JsonProperty("is_enable")
	private Integer isEnable;
	
	@JsonProperty("display_order")
	private Integer displayOrder;
	
	public Integer getImgId() {
		return imgId;
	}
	public void setImgId(Integer imgId) {
		this.imgId = imgId;
	}
	public String getImgUri() {
		return imgUri;
	}
	public void setImgUri(String imgUri) {
		this.imgUri = imgUri;
	}
	public Integer getWidth() {
		return width;
	}
	public void setWidth(Integer width) {
		this.width = width;
	}
	public Integer getHeight() {
		return height;
	}
	public void setHeight(Integer height) {
		this.height = height;
	}
	public String getTxtDesc() {
		return txtDesc;
	}
	public void setTxtDesc(String txtDesc) {
		this.txtDesc = txtDesc;
	}
	public Integer getIsEnable() {
		return isEnable;
	}
	public void setIsEnable(Integer isEnable) {
		this.isEnable = isEnable;
	}
	public Integer getDisplayOrder() {
		return displayOrder;
	}
	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}
}
