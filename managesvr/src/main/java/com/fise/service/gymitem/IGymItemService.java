package com.fise.service.gymitem;

import java.util.List;

import com.fise.model.entity.GymItem;

public interface IGymItemService {
	// 获取场馆健身项目列表
	public List<GymItem> getGymItemByGymId(Integer gymId);
	
	// 更新健身项目信息
	public void updateGymItem(GymItem gymItem);
	
	// 新增健身项目
	public void insertGymItem(GymItem gymItem);
	
	// 
	public List<GymItem> getGymItembyGymItemSelective(GymItem gymItem);
}
