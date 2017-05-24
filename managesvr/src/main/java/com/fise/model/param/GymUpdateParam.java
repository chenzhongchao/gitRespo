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
import com.fise.model.dto.GymItemDto;
import com.fise.model.dto.GymItemUpdateDto;
import com.fise.utils.JsonUtil;

/** 
 * @author 大表哥
 * @email xingyuanzuo@gmail.com
 * @date 2016-8-11
 * @desc 修改健身馆参数对象
 */

public class GymUpdateParam implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@JsonProperty("gym_id")
	@NotNull
	private Integer gymId ;
	
	@JsonProperty("gym_name")
	private String gymName;
	
	private String password;
	
	@JsonProperty("min_spend_amount")
	private Long minSpendAmount;
	
	private String province;
	
	private String city;
	
	private String region;
	
	private String address;
	
	private Double longitude;
	
	private Double latitude;
	
	private String tel;
	
	private Integer status;
	
	private String avatar;
	
	@JsonProperty("company_name")
	private String companyName;
	
	@JsonProperty("legal_person")
	private String legalPerson;
	
	private String introduction;
	
	@JsonProperty("img_introductions")
	private String imgIntroductions;
	
	@JsonProperty("gym_item_update_list")
	@Valid
	private List<GymItemUpdateDto> gymItemUpdateDtoList;

	public Integer getGymId() {
		return gymId;
	}
	public void setGymId(Integer gymId) {
		this.gymId = gymId;
	}
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
	public Long getMinSpendAmount() {
		return minSpendAmount;
	}
	public void setMinSpendAmount(Long minSpendAmount) {
		this.minSpendAmount = minSpendAmount;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
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
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public List<GymItemUpdateDto> getGymItemUpdateDtoList() {
		return gymItemUpdateDtoList;
	}
	public void setGymItemUpdateDtoList(List<GymItemUpdateDto> gymItemUpdateDtoList) {
		this.gymItemUpdateDtoList = gymItemUpdateDtoList;
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
	public String getImgIntroductions() {
		return imgIntroductions;
	}
	public void setImgIntroductions(String imgIntroductions) {
		this.imgIntroductions = imgIntroductions;
	}
	
	@Override
	public String toString() {
		return JsonUtil.toJson(this);
	}
}
