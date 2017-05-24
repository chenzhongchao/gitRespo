package com.fise.utils;

import org.omg.CORBA.INTERNAL;

public interface Constants {
	// url长度限制
	public Integer MAX_URL_LENGTH = 1024;

	// access token失效时间  15天
	public Integer ACCESS_TOKEN_EXPIRE_SECONDS = 1296000;
	
	// oss上传文件url失效时长 10分钟
	public Integer ALIYUN_OSS_UPLOAD_URL_EXPIRE_MILLISECONDS = 600000;
	
	// 登录授权至绑定的最长间隔时间
	public Long THIRDPARTY_LOGIN_AUTH_BIND_TIMEINTERVAL_MILLISECONDS = 600000L;
	
	// 用户端修改手机号每个步骤存在时长
	public Integer MEMBER_MOBILE_CHANGE_STEP_EXPIRE_SECONDS = 300;
	
	// 用户端:自定义header中存access token的字段名
	public String HEADER_FIELD_NAME_ACCESS_TOKEN = "FIT-AccessToken";
	
	// 后台管理系统:自定义header中存access token的字段名
	public String MANAGER_HEADER_FIELD_NAME_ACCESS_TOKEN = "FIT-ManagerAccessToken";
	
	// 健身馆端:自定义header中存access token的字段名
	public String GYM_HEADER_FIELD_NAME_ACCESS_TOKEN = "FIT-GymAccessToken";
	
	// 自定义UA
	public String HEADER_FIELD_FIT_USER_AGENT = "FIT-UA";
	
	// 默认page size大小
	public static final int DEFAULT_PAGE_SIZE = 20;
	
	// 入场默认的押金(单位:分)
	public static final int DEFAULT_BALANCE_FROZEN_AMOUNT = 5000;
	
	// 验证码失效时间(单位：秒)
	public static final int VERIFY_CODE_EXPIRE_TIME = 600;
	
	// 默认头像地址
	public static final String DEFAULT_AVATAR_URL = "http://xx.com/avatar.png";
	
	// 订单状态
	public static final int ORDER_STATUS_DEPOSIT_NOT_FROZEN = 0;	// 押金未冻结，系统设计该状态永远不会出现(押金足够才会生成order表项)
	public static final int ORDER_STATUS_NOT_CLEARING = 1;			// 已入场、待结算
	public static final int ORDER_STATUS_NOT_PAY = 2;				// 点结算、待付款
	public static final int ORDER_STATUS_NOT_COMMENTED = 3;			// 订单已完成，待评价
	public static final int ORDER_STATUS_HAVA_COMMENTD = 4;			// 订单已完成，已评价
	
	// 订单付款状态
	public static final int PAY_NOT = 0;					// 未付款
	public static final int PAY_YES = 1;					// 已付款带确认
	public static final int PAY_CONFIRM = 2;				// 已确认付款
	public static final int PAY_CANCEL = 3;					// 已退款
	public static final int PAY_PARTIAL_REFUND = 4;			// 部分退款
	public static final int PAY_PARTIAL_PAYED = 5;			// 部分付款
	
	// 结算状态
	public static final int ORDER_BILL_FLAG_NO = 0;			// 未结算
	public static final int ORDER_BILL_FLAG_YES = 1;		// 已结算
	
	// 订单项目状态
	public static final int ORDER_ITEM_STATE_OK = 0; 			// 正常
	public static final int ORDER_ITEM_STATE_CANCEL = 1;		// 取消
	
	// 用户账户交易操作类型
	public static final int ACCOUNT_TRANSACTION_OPERATE_TYPE_FROZEN_DEPOSIT = 1;		// 冻结押金
	public static final int ACCOUNT_TRANSACTION_OPERATE_TYPE_RETURN_DEPOSIT = 2;		// 退还押金
	public static final int ACCOUNT_TRANSACTION_OPERATE_TYPE_ORDER_PAYMENT = 3;			// 订单付款
	public static final int ACCOUNT_TRANSACTION_OPERATE_TYPE_ACCOUNT_CHARGE = 4;		// 充值
	public static final int ACCOUNT_TRANSACTION_OPERATE_TYPE_PARTIAL_ACCOUNT_FROZEN = 5;		// 平台冻结部分余额
	public static final int ACCOUNT_TRANSACTION_OPERATE_TYPE_DEDUCT_DEPOSIT = 6;		// 扣除押金
	
	// 商户账户交易操作类型 
	public static final int GYM_ACCOUNT_TRANSACTION_OPERATE_TYPE_SALES_INCOME = 1;			// 销售收入,计入待结算字段
	public static final int GYM_ACCOUNT_TRANSACTION_OPERATE_TYPE_SYSTEM_SETTLE = 2;			// 平台结算,从待结算到余额
	public static final int GYM_ACCOUNT_TRANSACTION_OPERATE_TYPE_DEPOSIT_INCOME = 3;		// 扣除押金,计入待结算字段
	
	// 交易流水状态
	public static final int ACCOUNT_TRANSACTION_STATE_PAY_NOT = 0;						// 充值：待支付
	public static final int ACCOUNT_TRANSACTION_STATE_PAY_YES = 1;						// 充值：支付成功
	public static final int ACCOUNT_TRANSACTION_STATE_SYSTEM_FROZEN = 2;				// 平台冻结
	public static final int ACCOUNT_TRANSACTION_STATE_SYSTEM_UNFROZEN = 3;				// 平台解冻
	
	// 支付方式
	public static final int PAY_METHOD_BALANCE_PAY = 1;						// 余额支付
	public static final int PAY_METHOD_ALIPAY_MOBILE_SECURITY_PAY = 2;		// 阿里手机安全支付
	public static final int PAY_METHOD_ALIPAY_WAP_PAY = 3;					// 阿里网站支付
	public static final int PAY_METHOD_WECHAT_PAY = 4;						// 微信支付

	// redis pool name
	public static final String REDIS_POOL_NAME_MEMBER = "Member";		// 用户redis pool
	public static final String REDIS_POOL_NAME_GYM = "Gym";				// 商家redis pool
	public static final String REDIS_POOL_NAME_SYSTEM = "System";		// 系统redis pool
	
	// redis key prefix
	public static final String REDIS_KEY_PREFIX_MEMBER_VERIFYCODE = "member:verify_code:";
	public static final String REDIS_KEY_PREFIX_GYM_ACCESS_TOKEN = "gym:access_token:";
	public static final String REDIS_KEY_PREFIX_MEMBER_ACCESS_TOKEN = "member:access_token:";
	public static final String REDIS_KEY_PREFIX_MANAGER_ACCESS_TOKEN = "manager:access_token:";
	public static final String REDIS_KEY_PREFIX_MEMBER_CHANGE_MOBILE_STEP = "member:change_mobile_step:";
	
	// 评论状态
	public static final Integer ORDER_COMMENT_STATUS_NOT_COMMENT = 0;		// 未评论
	public static final Integer ORDER_COMMENT_STATUS_HAVE_COMMENTED = 1;	// 已评论
	
	// 第三方登录
	public static final String THIRD_PARTY_LOGIN_WECHAT = "wechat";			// 微信登录
	public static final String THIRD_PARTY_LOGIN_QQ = "qq";					// QQ登录
	public static final String THIRD_PARTY_LOGIN_WEIBO = "weibo";			// 微博登录
	
	// 第三方登录 第三方id
	public static final Integer THIRD_PARTY_LOGIN_ID_WECHAT = 1;			// 微信
	public static final Integer THIRD_PARTY_LOGIN_ID_QQ = 2;				// QQ
	public static final Integer THIRD_PARTY_LOGIN_ID_WEIBO = 3;				// 微博
	
	// 意见反馈系统id
	public static final Integer SUGGUESTION_SOURCE_SYSTEM_MEMBER = 0;		// 用户端
	public static final Integer SUGGUESTION_SOURCE_SYSTEM_GYM = 1;			// 商户端

	// 意见反馈状态
	public static final Byte SUGGUESTION_STATE_CHECKING = 0;				// 审核中
	public static final Byte SUGGUESTION_STATE_CHECK_PASS = 1;				// 审核通过
	public static final Byte SUGGUESTION_STATE_CHECK_REJECT = 2;			// 审核拒绝
	
	// elastic search中的index 和 type
	public static final String ES_INDEX = "fitness";
	public static final String ES_GYM_TYPE = "gym";
}
