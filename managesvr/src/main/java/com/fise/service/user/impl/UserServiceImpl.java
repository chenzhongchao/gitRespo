package com.fise.service.user.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fise.dao.UserMapper;
import com.fise.model.dto.AssociateUser;
import com.fise.model.entity.UserInfo;
import com.fise.service.user.UserService;


@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	UserMapper userDao;

	@Override
	public UserInfo findUserInfo(Map map) {
		return userDao.findUserInfo(map);
	}

	@Override
	public AssociateUser findUserFriend(Map map) {
		return userDao.findUserFriend(map);
	}

	@Override
	public UserInfo findUserById(int id) {
		return userDao.findUserById(id);
	}
	
}
