package com.fise.model.entity;

import java.io.Serializable;
import java.util.Date;

import com.fise.utils.JsonUtil;

public class MemberThirdparty implements Serializable {
	private static final long serialVersionUID = -8522304207617181199L;
	
	private Integer id;
    private Date createTime;
    private Date updateTime;
    private Integer memberId;
    private Integer thirdpartyId;
    private String unionid;

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
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
    public Integer getMemberId() {
        return memberId;
    }
    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }
    public Integer getThirdpartyId() {
        return thirdpartyId;
    }
    public void setThirdpartyId(Integer thirdpartyId) {
        this.thirdpartyId = thirdpartyId;
    }
    public String getUnionid() {
        return unionid;
    }
    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }
    
    @Override
    public String toString() {
    	return JsonUtil.toJson(this);
    }
}