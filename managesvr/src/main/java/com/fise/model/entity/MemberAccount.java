package com.fise.model.entity;

import java.io.Serializable;
import java.util.Date;

import com.fise.utils.JsonUtil;

/** 
 * @author 大表哥
 * @email xingyuanzuo@gmail.com
 * @date 2016-8-2
 * @desc 健身馆账户实体类
 */

public class MemberAccount implements Serializable {
	private static final long serialVersionUID = 5701196769300943704L;
	
	private Integer accountId;
    private Date createTime;
    private Date updateTime;
    private Integer memberId;
    private Long frozenBalance;
    private Long availableBalance;
    private String password;

    public Integer getAccountId() {
        return accountId;
    }
    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
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
    public Long getFrozenBalance() {
        return frozenBalance;
    }
    public void setFrozenBalance(Long frozenBalance) {
        this.frozenBalance = frozenBalance;
    }
    public Long getAvailableBalance() {
        return availableBalance;
    }
    public void setAvailableBalance(Long availableBalance) {
        this.availableBalance = availableBalance;
    }
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
    
	@Override
	public String toString() {
		return JsonUtil.toJson(this);
	}
}