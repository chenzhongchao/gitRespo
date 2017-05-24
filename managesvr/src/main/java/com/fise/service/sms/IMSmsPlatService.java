package com.fise.service.sms;

import java.util.List;
import java.util.Map;

import com.fise.model.result.SmsInfo;

public interface IMSmsPlatService {
	public void insert_user(SmsInfo smsInfo);
	public void delete_user(Map map);
	public void update_user(SmsInfo smsInfo);
	public List<SmsInfo> queryAllSmsPlat();
}
