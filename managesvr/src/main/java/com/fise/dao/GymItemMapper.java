package com.fise.dao;

import java.util.List;

import com.fise.model.entity.GymItem;

public interface GymItemMapper {
	// 插入GymItem
	public void insertGymItem(GymItem gymItem);
	
	// 根据gymId查gymItem
	public List<GymItem> selectByGymId(Integer gymId);
	
	// 根据gymItemId查GymItem
	public GymItem selectByPrimaryKey(Integer itemId);
	
	// 根据pk更新健身项目信息
	public void updateByPrimaryKeySelective(GymItem gymItem);
	
	// 按条件查询GymItem
	public List<GymItem> selectGymItemBySelective(GymItem gymItem);
}