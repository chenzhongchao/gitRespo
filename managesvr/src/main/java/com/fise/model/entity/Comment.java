package com.fise.model.entity;

import java.util.Date;

public class Comment {
    
    private Long commentId;
    private Date createTime;
    private Date updateTime;
    private String orderSn;
    private Integer gymId;
    private Integer memberId;
    private String content;
    private Short status;
    private Integer score;
    private Short isTop;
    private Integer gymItemId;
    private Integer orderItemId;
    private String imgs;

    public Long getCommentId() {
        return commentId;
    }
    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }
    public Date getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public Date getUpdateTime() {
        return updateTime;
    }
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
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
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public Short getStatus() {
        return status;
    }
    public void setStatus(Short status) {
        this.status = status;
    }
    public Integer getScore() {
        return score;
    }
    public void setScore(Integer score) {
        this.score = score;
    }
    public Short getIsTop() {
        return isTop;
    }
    public void setIsTop(Short isTop) {
        this.isTop = isTop;
    }
    public Integer getGymItemId() {
        return gymItemId;
    }
    public void setGymItemId(Integer gymItemId) {
        this.gymItemId = gymItemId;
    }
    public Integer getOrderItemId() {
        return orderItemId;
    }
    public void setOrderItemId(Integer orderItemId) {
        this.orderItemId = orderItemId;
    }
    public String getImgs() {
        return imgs;
    }
    public void setImgs(String imgs) {
        this.imgs = imgs;
    }
	public String getOrderSn() {
		return orderSn;
	}
	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}
	
}