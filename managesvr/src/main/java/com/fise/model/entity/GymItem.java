package com.fise.model.entity;

import java.io.Serializable;
import java.util.Date;

/** 
 * @author 大表哥
 * @email xingyuanzuo@gmail.com
 * @date 2016-8-2
 * @desc 健身馆健身项目实体类
 */

public class GymItem implements Serializable {
	private static final long serialVersionUID = -6594219356496019380L;
	
	private Integer itemId;
    private Date createTime;
    private Date updateTime;
    private String itemName;
    private Long itemPrice;
    private Integer gymId;
    private Integer status;			// 0. 生效	1.删除

    public Integer getItemId() {
        return itemId;
    }
    public void setItemId(Integer itemId) {
        this.itemId = itemId;
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
    public String getItemName() {
        return itemName;
    }
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
    public Long getItemPrice() {
        return itemPrice;
    }
    public void setItemPrice(Long itemPrice) {
        this.itemPrice = itemPrice;
    }
    public Integer getGymId() {
        return gymId;
    }
    public void setGymId(Integer gymId) {
        this.gymId = gymId;
    }
    public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
}