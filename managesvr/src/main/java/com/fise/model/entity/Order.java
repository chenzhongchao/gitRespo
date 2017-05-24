package com.fise.model.entity;

import java.io.Serializable;
import java.util.Date;

import com.fise.utils.JsonUtil;

/** 
 * @author 大表哥
 * @email xingyuanzuo@gmail.com
 * @date 2016-8-1
 * @desc 订单实体类
 */

public class Order implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long orderId ;
	private String sn;
	private Integer memberId;		// 用户id
	private Integer gymId;			// 商户id
	private Integer status;			// 订单状态
	private Integer payStatus;		// 支付状态
	private Integer rightsStatus;	// 维权状态
	private Integer refundStatus; 	// 退款状态
	private Integer paymentId; 		// 订单支付方式
	private String paymentName; 	// 订单支付方式名称
	private String paymentType; 	// 订单支付方式返回值
	private Date paymentTime;		// 支付时间
	private Long payAmount;			// 实际支付金额（单位：分）
	private String transactionId;	// 第三方支付交易单号
	private String transactionSn;	// 第三方支付交易流水号
	private Long refundAmount;		// 退款金额（单位：分）
	private Date createTime;
	private Date updateTime;
	private Long  orderAmount;		// 订单金额
	private String remark;			// 备注
	private Integer isDisabled;		// 是否删除（0.未删除， 1.已删除）
	private Date completeTime;		// 订单完成时间
	private Long needPayAmount;		// 订单需要支付的金额
	private Integer isCommented;	// 是否备注（0.未评价，1.已评价）
	private Integer afterSaleStatus;// 维权状态 ### 和rightsStatus是否冲突？？？
	private String orderSource;		// 订单来源
	private String refundReason;	// 退款原因
	private Date refundTime;		// 退款时间
	private String refundApproveRemark;	// 退款申请审批备注
	private String refundRejectRemark;	// 退款申请驳回备注
	private Long couponAmount;			// 优惠券抵扣金额
	private Integer couponId;			// 优惠券id
	private Long balancePayAmount;		// 余额支付金额
	private Date orderBillTime;			// 订单结算时间
	private Integer orderBillFlag;			// 订单结算标志（0.未结算，1.已结算）
	private Long depositAmount;			// 押金
	
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	public Integer getMemberId() {
		return memberId;
	}
	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}
	public Integer getGymId() {
		return gymId;
	}
	public void setGymId(Integer gymId) {
		this.gymId = gymId;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getPayStatus() {
		return payStatus;
	}
	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}
	public Integer getRightsStatus() {
		return rightsStatus;
	}
	public void setRightsStatus(Integer rightsStatus) {
		this.rightsStatus = rightsStatus;
	}
	public Integer getRefundStatus() {
		return refundStatus;
	}
	public void setRefundStatus(Integer refundStatus) {
		this.refundStatus = refundStatus;
	}
	public Integer getPaymentId() {
		return paymentId;
	}
	public void setPaymentId(Integer paymentId) {
		this.paymentId = paymentId;
	}
	public String getPaymentName() {
		return paymentName;
	}
	public void setPaymentName(String paymentName) {
		this.paymentName = paymentName;
	}
	public String getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	public Date getPaymentTime() {
		return paymentTime;
	}
	public void setPaymentTime(Date paymentTime) {
		this.paymentTime = paymentTime;
	}
	public Long getPayAmount() {
		return payAmount;
	}
	public void setPayAmount(Long payAmount) {
		this.payAmount = payAmount;
	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public String getTransactionSn() {
		return transactionSn;
	}
	public void setTransactionSn(String transactionSn) {
		this.transactionSn = transactionSn;
	}
	public Long getRefundAmount() {
		return refundAmount;
	}
	public void setRefundAmount(Long refundAmount) {
		this.refundAmount = refundAmount;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Long getOrderAmount() {
		return orderAmount;
	}
	public void setOrderAmount(Long orderAmount) {
		this.orderAmount = orderAmount;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getIsDisabled() {
		return isDisabled;
	}
	public void setIsDisabled(Integer isDisabled) {
		this.isDisabled = isDisabled;
	}
	public Date getCompleteTime() {
		return completeTime;
	}
	public void setCompleteTime(Date completeTime) {
		this.completeTime = completeTime;
	}
	public Long getNeedPayAmount() {
		return needPayAmount;
	}
	public void setNeedPayAmount(Long needPayAmount) {
		this.needPayAmount = needPayAmount;
	}
	public Integer getIsCommented() {
		return isCommented;
	}
	public void setIsCommented(Integer isCommented) {
		this.isCommented = isCommented;
	}
	public Integer getAfterSaleStatus() {
		return afterSaleStatus;
	}
	public void setAfterSaleStatus(Integer afterSaleStatus) {
		this.afterSaleStatus = afterSaleStatus;
	}
	public String getOrderSource() {
		return orderSource;
	}
	public void setOrderSource(String orderSource) {
		this.orderSource = orderSource;
	}
	public String getRefundReason() {
		return refundReason;
	}
	public void setRefundReason(String refundReason) {
		this.refundReason = refundReason;
	}
	public Date getRefundTime() {
		return refundTime;
	}
	public void setRefundTime(Date refundTime) {
		this.refundTime = refundTime;
	}
	public String getRefundApproveRemark() {
		return refundApproveRemark;
	}
	public void setRefundApproveRemark(String refundApproveRemark) {
		this.refundApproveRemark = refundApproveRemark;
	}
	public String getRefundRejectRemark() {
		return refundRejectRemark;
	}
	public void setRefundRejectRemark(String refundRejectRemark) {
		this.refundRejectRemark = refundRejectRemark;
	}
	public Long getCouponAmount() {
		return couponAmount;
	}
	public void setCouponAmount(Long couponAmount) {
		this.couponAmount = couponAmount;
	}
	public Integer getCouponId() {
		return couponId;
	}
	public void setCouponId(Integer couponId) {
		this.couponId = couponId;
	}
	public Long getBalancePayAmount() {
		return balancePayAmount;
	}
	public void setBalancePayAmount(Long balancePayAmount) {
		this.balancePayAmount = balancePayAmount;
	}
	public Date getOrderBillTime() {
		return orderBillTime;
	}
	public void setOrderBillTime(Date orderBillTime) {
		this.orderBillTime = orderBillTime;
	}
	public Integer getOrderBillFlag() {
		return orderBillFlag;
	}
	public void setOrderBillFlag(Integer orderBillFlag) {
		this.orderBillFlag = orderBillFlag;
	}
	public Long getDepositAmount() {
		return depositAmount;
	}
	public void setDepositAmount(Long depositAmount) {
		this.depositAmount = depositAmount;
	}
	
	@Override
	public String toString() {
		return JsonUtil.toJson(this);
	}
}
