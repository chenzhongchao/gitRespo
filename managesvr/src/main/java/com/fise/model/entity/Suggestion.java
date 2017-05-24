package com.fise.model.entity;

import java.io.Serializable;
import java.util.Date;

import com.fise.utils.JsonUtil;

public class Suggestion implements Serializable {
	private static final long serialVersionUID = -686302463878332854L;
	
	private Integer sugId;
    private Date createTime;
    private Date updateTime;
    private Short sourceSystemId;
    private Integer sourceUserId;
    private String suggestion;
    private Byte state;
    
    public Integer getSugId() {
        return sugId;
    }
    public void setSugId(Integer sugId) {
        this.sugId = sugId;
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
    public Short getSourceSystemId() {
        return sourceSystemId;
    }
    public void setSourceSystemId(Short sourceSystemId) {
        this.sourceSystemId = sourceSystemId;
    }
    public Integer getSourceUserId() {
        return sourceUserId;
    }
    public void setSourceUserId(Integer sourceUserId) {
        this.sourceUserId = sourceUserId;
    }
    public String getSuggestion() {
        return suggestion;
    }
    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }
    public Byte getState() {
        return state;
    }
    public void setState(Byte state) {
        this.state = state;
    }
    
    @Override
    public String toString() {
    	return JsonUtil.toJson(this);
    }
}