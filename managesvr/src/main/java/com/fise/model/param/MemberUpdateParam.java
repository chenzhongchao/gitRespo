package com.fise.model.param;

import java.io.Serializable;
import java.util.Date;
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
 * @date 2016-8-12
 * @desc 修改用户信息参数对象
 */

public class MemberUpdateParam implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@JsonProperty("member_id")
	@NotNull
	private Integer memberId;
	
	@JsonProperty("nick_name")
	private String nickName;
	
	private Integer sex;
	private Date birthday;
	private Double height;
	private Double weight;
	private String avatar;
	
	public Integer getMemberId() {
		return memberId;
	}	
	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public Double getHeight() {
		return height;
	}
	public void setHeight(Double height) {
		this.height = height;
	}
	public Double getWeight() {
		return weight;
	}
	public void setWeight(Double weight) {
		this.weight = weight;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	
	@Override
	public String toString() {
		return JsonUtil.toJson(this);
	}
}
