package com.fise.dao;

import com.fise.model.entity.Manager;

/** 
 * @author 大表哥
 * @email xingyuanzuo@gmail.com
 * @date 2016-7-27
 * @desc 访问Manager表的接口
 */
public interface ManagerMapper {
	// 新增管理员
	public void insertManager(Manager manager);
	
	// 更新用户信息
	public void updateManager(Manager manager);
	
	// 根据手机号获取管理员信息
	public Manager getManagerByUserName(String userName);
	
	// 根据manager id获取管理员信息
	public Manager getManagerById(Integer managerId);
}
