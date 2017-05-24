package com.fise.dao;

import java.util.List;

import com.fise.model.entity.AuthorityInfo;
import com.fise.model.entity.UserAuthority;



public interface AuthorityManagerMapper {
	public void insert_authority(UserAuthority userauth);
	
	public void update_authority(UserAuthority userauth);
	
	public void delete_authority(UserAuthority userauth);
	
	public List<AuthorityInfo> findAllAuthoritys(int AuthRole);
}
