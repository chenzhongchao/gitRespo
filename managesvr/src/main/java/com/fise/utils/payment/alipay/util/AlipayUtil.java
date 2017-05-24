package com.fise.utils.payment.alipay.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.fise.model.entity.Order;
import com.fise.utils.payment.alipay.config.AlipayConfig;
import com.fise.utils.payment.alipay.sign.RSA;

/**
 * 支付宝支付工具类
 * 
 * @author 水墨
 * @version 2015-09-10
 *
 */
public class AlipayUtil {
	public static String genPayInfo(String transNo, String totalFee) throws UnsupportedEncodingException {
		
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("partner", AlipayConfig.partner);
		paramsMap.put("seller_id", AlipayConfig.seller_id);
		// 使用交易流水号作为外部订单号，交易流水号在支付发起时生成
		paramsMap.put("out_trade_no", transNo);
		
		paramsMap.put("subject", "铁馆充值");
		paramsMap.put("body", "本次充值：" + totalFee + "元");
		
		paramsMap.put("total_fee", totalFee);
		paramsMap.put("service", "mobile.securitypay.pay");
		paramsMap.put("payment_type", String.valueOf(1));
		// paramsMap.put("it_b_pay", "45m");
		paramsMap.put("_input_charset", AlipayConfig.input_charset);
		paramsMap.put("sign_type", AlipayConfig.sign_type);
		paramsMap.put("return_url", "");
		paramsMap.put("notify_url", AlipayConfig.notify_url);

		Map<String, String> params = AlipayCore.paraFilter(paramsMap);
		AlipayCore.paraFilter(params);
		String payInfo = AlipayCore.createLinkString(params,false);
		String sign = RSA.sign(payInfo, AlipayConfig.private_pkcs8_key,AlipayConfig.input_charset);
		
		payInfo += "&sign=" + URLEncoder.encode(sign, AlipayConfig.input_charset) + "&sign_type=RSA";
		
		return payInfo;
		
	}
	
	@SuppressWarnings("unchecked")
	public static boolean verify(HttpServletRequest request) {
		Map<String,String[]> requestParams = request.getParameterMap();
		Map<String,String> params = new HashMap<String, String>();
		Iterator<String> iterator = requestParams.keySet().iterator();
		while(iterator.hasNext()){
			String key = iterator.next();
			String[] values = requestParams.get(key);
			String valueTemp = Arrays.toString(values);
			valueTemp = valueTemp.substring(1, valueTemp.length() - 1);
			try {
				params.put(key, URLDecoder.decode(valueTemp,AlipayConfig.input_charset));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return AlipayNotify.verify(params);
	}
}
