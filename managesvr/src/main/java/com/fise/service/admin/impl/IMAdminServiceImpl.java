package com.fise.service.admin.impl;

import java.util.List;
import java.util.Map;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fise.dao.IMAdminMapper;
import com.fise.model.dto.UsertAuthCode;
import com.fise.model.entity.IMAdmin;
import com.fise.model.result.UpdatePwd;
import com.fise.service.admin.IMAdminService;


@Service
public class IMAdminServiceImpl implements IMAdminService{
	
	@Autowired
	IMAdminMapper imAdminDao;

	@SuppressWarnings("null")
	@Override
	public IMAdmin user_login(Map map) {
		if(map.get("UserName").toString().length()==0){
			IMAdmin imAdmin=new IMAdmin();
			imAdmin.setStatus(1);
			imAdmin.setErrorInfo("用户账号未填写！！！");
			return imAdmin;
		}
		
		IMAdmin imAdmin=imAdminDao.user_login(map);
		if(imAdmin==null){
			imAdmin.setStatus(1);
			imAdmin.setErrorInfo("用户账号/密码输入有误");
			return imAdmin;
		}
		return imAdmin;
	}

	@Override
	public void update_pwd(Map map) {
		 imAdminDao.update_pwd(map);	
	}

	@Override
	public void update_nickname(Map map) {
		 imAdminDao.update_nickname(map);		
	}

	@Override
	public UpdatePwd selectOneByName(String UserName) {
		 return imAdminDao.selectOneByName(UserName);		
	}

	@Override
	public List<UsertAuthCode> getPrivateAuth(Map map) {
		return imAdminDao.getPrivateAuth(map);
	}
}
