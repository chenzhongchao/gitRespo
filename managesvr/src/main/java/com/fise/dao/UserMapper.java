package com.fise.dao;

import java.util.Map;

import com.fise.model.dto.AssociateUser;
import com.fise.model.entity.UserInfo;


public interface UserMapper {
	public UserInfo findUserInfo(Map map);
	
	public AssociateUser findUserFriend(Map map);
	
	public UserInfo findUserById(int id);
}
