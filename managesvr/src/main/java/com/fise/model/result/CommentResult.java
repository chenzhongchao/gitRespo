package com.fise.model.result;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fise.base.Page;
import com.fise.model.entity.Gym;
import com.fise.utils.JsonUtil;

public class CommentResult implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@JsonProperty("comment_id")
	private Long commentId;
	
	@JsonProperty("update_time")
	private Date updateTime;
	
	@JsonProperty("order_sn")
	private String orderSn;
	
	@JsonProperty("gym_id")
	private Integer gymId;
	
	@JsonProperty("member_id")
	private Integer memberId;
	
	@JsonProperty("nicke_name")
	private String nickName;
	
	private String avatar;
	
	private Integer score;
	
	private String content;

	public Long getCommentId() {
		return commentId;
	}
	public void setCommentId(Long commentId) {
		this.commentId = commentId;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
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
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
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
