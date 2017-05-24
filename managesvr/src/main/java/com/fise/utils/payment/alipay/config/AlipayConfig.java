package com.fise.utils.payment.alipay.config;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *版本：3.3
 *日期：2012-08-10
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
	
 *提示：如何获取安全校验码和合作身份者ID
 *1.用您的签约支付宝账号登录支付宝网站(www.alipay.com)
 *2.点击“商家服务”(https://b.alipay.com/order/myOrder.htm)
 *3.点击“查询合作者身份(PID)”、“查询安全校验码(Key)”

 *安全校验码查看时，输入支付密码后，页面呈灰色的现象，怎么办？
 *解决方法：
 *1、检查浏览器配置，不让浏览器做弹框屏蔽设置
 *2、更换浏览器或电脑，重新登录查询。
 */

public class AlipayConfig {
	
	//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
	// 合作身份者ID，以2088开头由16位纯数字组成的字符串
	public static String partner = "2088711090608345";
	public static String partner_key = "f29s3iq16dff5t6i608uyyuivi4tdly1";
	public static String app_sercert = "8525dc15adc94290b5b0eb6ae9a8bf5c";
	
	// 商户的私钥
	public static String private_key = "MIICXgIBAAKBgQCnljD2556G4Ymb2RBAZfLu9Xw5GwDFKhd++0r/vFUJy2fjejwoLPouWh0uEjbBjxoUdhFYfKROBbUQsiEYEicSAZZLCARTGugwjnwTFWpTzEFtgIw5dXB0QcgWRfKI2W1GO9CbcY7xamcym9YfleOx5PTS7jHQZYhmG3KCwAsH6QIDAQABAoGBAKVkhch3SK3nUq7bp71WoE4TOlkFfFoZs3yftyHzFZ4H/+yxy0L/OJ2cU+YPFbkraNq+t1m0EVfrLAphMmYWLDVxBI6QfZUt379bcxjF6cklKg5iaMbdDuoyiY6IutqtnNXD9DI2QBucp8WaAgokj3vX+eFMRtgzxqTZ2k7ugIdNAkEA1mHVv9rrynjcAZrmT1fG6IaTaDm3wNcQCEGCcPdinmfuvUS5LgXUrreknUrsqaGnD6sAI7nKwgatVDacrrlClwJBAMgewg4f+A+KKB/2GZnhDBytSiT1ppU3jnNurL4WHhpYcU8tWJ5Mo2zTK/idtsD7i21RCasFmyJYfR4DPp/32X8CQQCz1d440qx5bGkh747DgB6bcEi3ZbN/UOliA5IosV1JBGaA3uV4deuzpNmyGOfuZGx/+DJlxbUQjglYW3eUGlSBAkEAvCTmzv7RalK6vKnUke6aQ4/MaIknj5yG6hHAiIokzSIfjZAVg+aLszGqYnhsrG0SPbl1vHttydGl+ASW6TDNIwJAChmTNsC//S+AjFQqN7YOkePWQWxy6xnNI627uaSQmwOSrwj/PEvdTzpH/r4uUQzVNpwIEnG30Y8bJ9ol2rKSFA==";
	public static String private_pkcs8_key = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAKeWMPbnnobhiZvZEEBl8u71fDkbAMUqF377Sv+8VQnLZ+N6PCgs+i5aHS4SNsGPGhR2EVh8pE4FtRCyIRgSJxIBlksIBFMa6DCOfBMValPMQW2AjDl1cHRByBZF8ojZbUY70JtxjvFqZzKb1h+V47Hk9NLuMdBliGYbcoLACwfpAgMBAAECgYEApWSFyHdIredSrtunvVagThM6WQV8WhmzfJ+3IfMVngf/7LHLQv84nZxT5g8VuSto2r63WbQRV+ssCmEyZhYsNXEEjpB9lS3fv1tzGMXpySUqDmJoxt0O6jKJjoi62q2c1cP0MjZAG5ynxZoCCiSPe9f54UxG2DPGpNnaTu6Ah00CQQDWYdW/2uvKeNwBmuZPV8bohpNoObfA1xAIQYJw92KeZ+69RLkuBdSut6SdSuypoacPqwAjucrCBq1UNpyuuUKXAkEAyB7CDh/4D4ooH/YZmeEMHK1KJPWmlTeOc26svhYeGlhxTy1YnkyjbNMr+J22wPuLbVEJqwWbIlh9HgM+n/fZfwJBALPV3jjSrHlsaSHvjsOAHptwSLdls39Q6WIDkiixXUkEZoDe5Xh167Ok2bIY5+5kbH/4MmXFtRCOCVhbd5QaVIECQQC8JObO/tFqUrq8qdSR7ppDj8xoiSePnIbqEcCIiiTNIh+NkBWD5ouzMapieGysbRI9uXW8e23J0aX4BJbpMM0jAkAKGZM2wL/9L4CMVCo3tg6R49ZBbHLrGc0jrbu5pJCbA5KvCP88S91POkf+vi5RDNU2nAgScbfRjxsn2iXaspIU";
	// 商户收款账户
	public static String seller_id = "2769459998@qq.com";
	
	// 支付宝的手机网页支付网关、公钥
	public static String alipay_gateway = "https://mapi.alipay.com/gateway.do";
	public static String alipay_public_key  = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";

	//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑
	// 移动客户端支付
	public static String service_mobileSecuritypayPay = "mobile.securitypay.pay";
	// 移动客户端网页支付
	public static String service_alipayWapDirectPay = "alipay.wap.create.direct.pay.by.user";

	// 调试用，创建TXT日志文件夹路径
	public static String log_path = "D:\\";

	// 字符编码格式 目前支持 gbk 或 utf-8
	public static String input_charset = "utf-8";
	
	// 签名方式 不需修改
	public static String sign_type = "RSA";
	
	// 支付回调通知地址
	public static String notify_url = "http://120.76.221.66/dev/api/fitness/member/alipay/callback";

}
