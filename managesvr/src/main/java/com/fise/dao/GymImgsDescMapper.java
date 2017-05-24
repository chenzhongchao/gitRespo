package com.fise.dao;

import java.util.List;

import com.fise.model.entity.GymImgsDesc;

/** 
 * @author 大表哥
 * @email xingyuanzuo@gmail.com
 * @date 2016-7-28
 * @desc 访问GymImgsDesc表的接口
 */
public interface GymImgsDescMapper {
	// 新增健身馆图文详情
	public void insertGymImgsDesc(GymImgsDesc gymImgsDesc);
	
	// 获取健身馆图文详情
	public List<GymImgsDesc> getGymImgsDescByGymId(Integer gymId);
}
