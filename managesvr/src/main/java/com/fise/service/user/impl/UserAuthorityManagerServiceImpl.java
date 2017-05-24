package com.fise.service.user.impl;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fise.dao.AuthorityManagerMapper;
import com.fise.model.entity.AuthorityInfo;
import com.fise.model.entity.UserAuthority;
import com.fise.service.user.UserAuthorityManagerService;


@Service
public class UserAuthorityManagerServiceImpl implements UserAuthorityManagerService{

	@Autowired
	AuthorityManagerMapper userAuthorityDao;
	
	@Override
	public void insert_authority(UserAuthority userauth) {
		userAuthorityDao.insert_authority(userauth);
	}

	@Override
	public void update_authority(UserAuthority userauth) {
		userAuthorityDao.update_authority(userauth);
	}

	@Override
	public void delete_authority(UserAuthority userauth) {
		userAuthorityDao.delete_authority(userauth);
	}

	@Override
	public List<AuthorityInfo> findAllAuthoritys(int AuthRole) {
		return userAuthorityDao.findAllAuthoritys(AuthRole);
	}

}
