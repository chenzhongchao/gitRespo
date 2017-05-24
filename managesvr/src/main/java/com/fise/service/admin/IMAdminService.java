package com.fise.service.admin;

import java.util.List;
import java.util.Map;

import com.fise.model.dto.UsertAuthCode;
import com.fise.model.entity.IMAdmin;
import com.fise.model.result.UpdatePwd;



public interface IMAdminService {
	public IMAdmin user_login(Map map);
	public List<UsertAuthCode> getPrivateAuth(Map map);
	public void update_pwd(Map map);
	public void update_nickname(Map map);
	public UpdatePwd selectOneByName(String UserName);
}
