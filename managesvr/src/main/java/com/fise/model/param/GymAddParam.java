package com.fise.model.param;

import java.io.Serializable;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fise.framework.annotation.MaxLength;
import com.fise.framework.annotation.NotEmpty;
import com.fise.model.dto.GymAddGymItemDto;
import com.fise.model.dto.GymImgsDescDto;
import com.fise.utils.JsonUtil;

/** 
 * @author 大表哥
 * @email xingyuanzuo@gmail.com
 * @date 2016-7-28
 * @desc 添加健身馆参数对象
 */

public class GymAddParam implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@JsonProperty("gym_name")
	@NotEmpty
	@MaxLength(value = 255)
	private String gymName;
	
	
	// @NotEmpty
	// @MaxLength(value = 50)
	private String password;
	
	@NotEmpty
	@MaxLength(value = 255)
	private String address;
	
	@NotNull
	private Double longitude;
	
	private Double latitude;
	
	@MaxLength(value = 20)
	private String tel;
	
	@JsonProperty("min_spend_amount")
	private Long minSpendAmount;
	
	@MaxLength(value = 102400)
	@JsonProperty("img_introductions")
	private String imgIntroductions;
	
	@MaxLength(value = 255)
	private String avatar;
	
	@MaxLength(value = 255)
	@JsonProperty("company_name")
	private String companyName;
	
	@MaxLength(value = 50)
	@JsonProperty("legal_person")
	private String legalPerson;
	
	@MaxLength(value = 1024)
	private String introduction;
	
	@JsonProperty("imgs_and_desc")
	private List<GymImgsDescDto> imgsAndDesc;
	
	@JsonProperty("gym_item_list")
	@Valid
	private List<GymAddGymItemDto> gymItemList;
	
	public String getGymName() {
		return gymName;
	}
	public void setGymName(String gymName) {
		this.gymName = gymName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
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
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public List<GymImgsDescDto> getImgsAndDesc() {
		return imgsAndDesc;
	}
	public void setImgsAndDesc(List<GymImgsDescDto> imgsAndDesc) {
		this.imgsAndDesc = imgsAndDesc;
	}
	public Long getMinSpendAmount() {
		return minSpendAmount;
	}
	public void setMinSpendAmount(Long minSpendAmount) {
		this.minSpendAmount = minSpendAmount;
	}
	public List<GymAddGymItemDto> getGymItemList() {
		return gymItemList;
	}
	public void setGymItemList(List<GymAddGymItemDto> gymItemList) {
		this.gymItemList = gymItemList;
	}
	public String getImgIntroductions() {
		return imgIntroductions;
	}
	public void setImgIntroductions(String imgIntroductions) {
		this.imgIntroductions = imgIntroductions;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getLegalPerson() {
		return legalPerson;
	}
	public void setLegalPerson(String legalPerson) {
		this.legalPerson = legalPerson;
	}
	public String getIntroduction() {
		return introduction;
	}
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	
	@Override
	public String toString() {
		return JsonUtil.toJson(this);
	}
}
