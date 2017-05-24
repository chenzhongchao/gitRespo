package com.fise.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import com.fise.model.entity.Gym;
import com.fise.model.entity.Member;


/**
 * 用于获取全局唯一的access_token，可唯一性的标识用户身份
 * 
 * @author Administrator
 * @version 2015-07-29
 */
public class CommonUtil {

	public static String getAccessToken() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString().replaceAll("-", "");
	}

	public static String getTransactionNo(Object obj){
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String id = null;
		String midCode = null;
		String postCode = null;
		synchronized (obj) {
			if(obj instanceof Member) {
				midCode = ((Member)obj).getMemberId() + 2000000000 + "";
			} else if (obj instanceof Gym) {
				midCode = ((Gym)obj).getGymId() + 1000000000 + "";
			} else {
				return null;
			}
			postCode = StringUtil.makeCode(4, true);
		}
		
		id = sdf.format(date) + midCode + postCode;
		
		return id;
	}
	
	// 比较两个版本号大小
	public static int versionComparator(String version1, String version2) {
		int result = 0;
		if (StringUtil.isEmpty(version1))  return -1;
		if (StringUtil.isEmpty(version2))  return 1;
		
		String[] versionList1 = version1.split("\\.");
		String[] versionList2 = version2.split("\\.");
		int loopStep = (versionList1.length < versionList2.length) ? versionList1.length : versionList2.length;
		
		for (int i = 0 ; i < loopStep; i++) {
			int value1 = Integer.parseInt(versionList1[i]);
			int value2 = Integer.parseInt(versionList2[i]);
				
			if (value1 > value2) {
				result = 1; 
				break;
			} else if (value1 < value2) {
				result = -1;
				break;
			} else {
				result = 0;
				continue;
			}
		}
		
		return result;
	}
}
