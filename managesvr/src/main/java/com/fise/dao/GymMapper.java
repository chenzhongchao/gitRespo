package com.fise.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.fise.base.Page;
import com.fise.model.entity.Gym;

/** 
 * @author 大表哥
 * @email xingyuanzuo@gmail.com
 * @date 2016-7-28
 * @desc 访问Gym表的接口
 */
public interface GymMapper {
	// 新增健身馆
	public void insertGym(Gym gym);
	
	// 更新健身馆信息
	public void updateGym(Gym gym);
	
	// 根据id获取健身馆信息
	public Gym getGymById(Integer gymId);
	
	// 根据健身馆名字获取健身馆信息
	public Gym getGymByGymName(String gymName);
	
	// 获取健身馆列表
	public List<Gym> getGymList(@Param("page")Page<Gym> pageParam);
	
	// 根据id获取健身馆信息
	public Gym getGymByLoginId(Integer loginId);
}
