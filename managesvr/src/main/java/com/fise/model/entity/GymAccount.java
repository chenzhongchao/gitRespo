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

public class GymAccount implements Serializable {
	private static final long serialVersionUID = 5701196769300943704L;
	
	private Integer accountId;
    private Date createTime;
    private Date updateTime;
    private Integer gymId;
    private Long frozenBalance;			// 冻结金额
    private Long availableBalance;		// 账户余额
    private Long waitBalance;			// 待结算金额
    private Long settledBalance;		// 已结算金额

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
    public Integer getGymId() {
        return gymId;
    }
    public void setGymId(Integer gymId) {
        this.gymId = gymId;
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
	public Long getWaitBalance() {
		return waitBalance;
	}
	public void setWaitBalance(Long waitBalance) {
		this.waitBalance = waitBalance;
	}
	public Long getSettledBalance() {
		return settledBalance;
	}
	public void setSettledBalance(Long settledBalance) {
		this.settledBalance = settledBalance;
	}
    
	@Override
	public String toString() {
		return JsonUtil.toJson(this);
	}
}