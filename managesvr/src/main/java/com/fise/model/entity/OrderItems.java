package com.fise.model.entity;

import java.io.Serializable;
import java.util.Date;

import com.fise.utils.JsonUtil;

/** 
 * @author 大表哥
 * @email xingyuanzuo@gmail.com
 * @date 2016-8-2
 * @desc 订单子项实体类
 */

public class OrderItems implements Serializable {
	private static final long serialVersionUID = 8920155568347257146L;
	
	private Long id;
	private Date createTime;
	private Date updateTime;
    private Long orderId;
    private String orderSn;
    private Integer gymItemId;
    private Integer count;
    private String gymItemName;
    private Long price;
    private Integer state;
    private Integer isCommented;
    private Integer gymId;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
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
    public Long getOrderId() {
        return orderId;
    }
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
    public String getOrderSn() {
        return orderSn;
    }
    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }
    public Integer getGymItemId() {
        return gymItemId;
    }
    public void setGymItemId(Integer gymItemId) {
        this.gymItemId = gymItemId;
    }
    public Integer getCount() {
        return count;
    }
    public void setCount(Integer count) {
        this.count = count;
    }
    public String getGymItemName() {
        return gymItemName;
    }
    public void setGymItemName(String gymItemName) {
        this.gymItemName = gymItemName;
    }
    public Long getPrice() {
        return price;
    }
    public void setPrice(Long price) {
        this.price = price;
    }
    public Integer getState() {
        return state;
    }
    public void setState(Integer state) {
        this.state = state;
    }
    public Integer getIsCommented() {
        return isCommented;
    }
    public void setIsCommented(Integer isCommented) {
        this.isCommented = isCommented;
    }
    public Integer getGymId() {
        return gymId;
    }
    public void setGymId(Integer gymId) {
        this.gymId = gymId;
    }

    @Override
    public String toString() {
    	return JsonUtil.toJson(this);
    }
}