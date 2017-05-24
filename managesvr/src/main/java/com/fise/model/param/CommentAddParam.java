package com.fise.model.param;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fise.framework.annotation.MaxLength;
import com.fise.framework.annotation.NotEmpty;
import com.fise.utils.JsonUtil;

/** 
 * @author 大表哥
 * @email xingyuanzuo@gmail.com
 * @date 2016-8-8
 * @desc 提交评价参数对象
 */

public class CommentAddParam implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@NotNull
	@JsonProperty("order_sn")
	private String orderSn;
	
	@NotNull
	@JsonProperty("gym_id")
	private Integer gymId;
	
	@NotNull
	@JsonProperty("member_id")
	private Integer memberId;
	
	@NotNull
	private Integer score;
	
	@NotEmpty
	@MaxLength(value = 1024)
	private String content;

	public String getOrderSn() {
		return orderSn;
	}
	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}
	public Integer getGymId() {
		return gymId;
	}
	public void setGymId(Integer gymId) {
		this.gymId = gymId;
	}
	public Integer getMemberId() {
		return memberId;
	}
	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}
	public Integer getScore() {
		return score;
	}
	public void setScore(Integer score) {
		this.score = score;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	@Override
	public String toString() {
		return JsonUtil.toJson(this);
	}
}
