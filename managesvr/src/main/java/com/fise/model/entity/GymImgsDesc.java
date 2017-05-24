package com.fise.model.entity;

import java.io.Serializable;
import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fise.model.dto.GymImgsDescDto;

/** 
 * @author 大表哥
 * @email xingyuanzuo@gmail.com
 * @date 2016-7-28
 * @desc 健身馆图文实体类
 */

public class GymImgsDesc implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer imgId;
	private Integer gymId;
	private String imgUri;
	private Integer width;
	private Integer height;
	private String txtDesc;
	private Integer isEnable;
	private Integer displayOrder;
	
	public GymImgsDesc() {
	}
	
	public GymImgsDesc(GymImgsDescDto dto) {
		this.imgId = dto.getImgId();
		this.imgUri = dto.getImgUri();
		this.width = dto.getWidth();
		this.height = dto.getHeight();
		this.txtDesc = dto.getTxtDesc();
		this.displayOrder = dto.getDisplayOrder();
	}
	
	public Integer getImgId() {
		return imgId;
	}
	public void setImgId(Integer imgId) {
		this.imgId = imgId;
	}
	public Integer getGymId() {
		return gymId;
	}
	public void setGymId(Integer gymId) {
		this.gymId = gymId;
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
