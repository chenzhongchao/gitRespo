package com.fise.service.sms.impl;

import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fise.dao.IMSmsPlatMapper;
import com.fise.model.result.SmsInfo;
import com.fise.service.sms.IMSmsPlatService;

@Service
public class IMSmsPlatServiceImpl implements IMSmsPlatService{
	
	@Autowired
	IMSmsPlatMapper imsPlatDao;
	
	@Override
	public void insert_user(SmsInfo smsInfo) {
		imsPlatDao.insert_user(smsInfo);		
	}

	@Override
	public void delete_user(Map map) {
		imsPlatDao.delete_user(map);		
	}

	@Override
	public void update_user(SmsInfo smsInfo) {
		imsPlatDao.update_user(smsInfo);
	}

	@Override
	public List<SmsInfo> queryAllSmsPlat() {
		return imsPlatDao.queryAllSmsPlat();
	}

}
