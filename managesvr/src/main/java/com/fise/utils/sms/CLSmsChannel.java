package com.fise.utils.sms;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.annotation.Retention;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import com.fise.framework.config.ConfigProperties;
import com.qq.jutil.j4log.Logger;


public class CLSmsChannel {
	
	public static boolean sendSMS(String mobile, String content, boolean needStatus) {
		Logger logger = Logger.getLogger("sms_send");
		boolean success = false;
		try {
			
			String clAccount = ConfigProperties.getValue("CL_ACCOUNT");
			String clPasswd = ConfigProperties.getValue("CL_PASSWD");
			
			StringBuilder urlBuilder = new StringBuilder("http://222.73.117.158/msg/HttpBatchSendSM?account=");
			urlBuilder.append(clAccount);
			urlBuilder.append("&pswd=");
			urlBuilder.append(clPasswd);
			urlBuilder.append("&mobile=");
			urlBuilder.append(mobile);
			urlBuilder.append("&msg=");
			urlBuilder.append(URLEncoder.encode(content, "UTF-8"));
			urlBuilder.append("&needstatus=");
			urlBuilder.append(needStatus);
			
			System.out.println("url: " + urlBuilder.toString());
			
			URL url = new URL(urlBuilder.toString());
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();

			connection.setRequestMethod("GET");
			connection.setDoOutput(true);
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			
			Integer index = 0;
			String[] returnList = null;
			String sendTime = null;
			String returnCode = null;
			String currentLine = null;
			StringBuilder totalString = new StringBuilder();
			InputStream urlInputStream = connection.getInputStream();
			BufferedReader inputStreamBufferedReader = new BufferedReader(new InputStreamReader(urlInputStream));
			
			
			while ((currentLine = inputStreamBufferedReader.readLine()) != null) {
				totalString.append(currentLine);
				totalString.append("\r\n");
				
				if (index == 0) {
					returnList = currentLine.split(",");
					if (returnList.length ==2) {
						sendTime = returnList[0];
						returnCode = returnList[1];
					}
					break;
				}
				
				index++;
			}
			if (returnCode != null && returnCode.equals("0")) {
				success = true;
			}
			
			logger.error("request_url=" + urlBuilder.toString() + " returnCode=" + returnCode + " sendTime=" + sendTime + " success=" + success);
			
		} catch (Exception e) {
			e.printStackTrace();
			//throw new RuntimeException(e.getMessage());
			return success;
		}
		
		return success;
	}
	
	public static void main(String[] args) {
		sendSMS("15817457602", "您的注册验证码是：8888.请完成注册", false);
	}
}
