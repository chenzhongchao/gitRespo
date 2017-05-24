package com.fise.model.entity;

import java.util.Date;

public class GymAccountTransaction {
    private Integer transId;
    private Date createTime;
    private Date updateTime;
    private String transNo;
    private Integer gymId;
    private Long orderId;
    private Integer operateType;
    private Integer payMethod;
    private Long amount;
    private Long preBalance;
    private Long postBalance;
    private String thirdpartyPaymentAccount;
    private String thirdpartyPaymentName;
    private String bankName;
    private String bankAddress;
    private String bankCode;
    private String bankCardNo;
    private String bankCardName;
    private String withdrawDepositRemark;
    private Long refundAmount;
    private Integer state;

    public Integer getTransId() {
        return transId;
    }
    public void setTransId(Integer transId) {
        this.transId = transId;
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
    public String getTransNo() {
        return transNo;
    }
    public void setTransNo(String transNo) {
        this.transNo = transNo;
    }
    public Integer getGymId() {
        return gymId;
    }
    public void setGymId(Integer gymId) {
        this.gymId = gymId;
    }
    public Long getOrderId() {
        return orderId;
    }
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
    public Integer getOperateType() {
        return operateType;
    }
    public void setOperateType(Integer operateType) {
        this.operateType = operateType;
    }
    public Integer getPayMethod() {
        return payMethod;
    }
    public void setPayMethod(Integer payMethod) {
        this.payMethod = payMethod;
    }
    public Long getAmount() {
        return amount;
    }
    public void setAmount(Long amount) {
        this.amount = amount;
    }
    public Long getPreBalance() {
        return preBalance;
    }
    public void setPreBalance(Long preBalance) {
        this.preBalance = preBalance;
    }
    public Long getPostBalance() {
        return postBalance;
    }
    public void setPostBalance(Long postBalance) {
        this.postBalance = postBalance;
    }
    public String getThirdpartyPaymentAccount() {
        return thirdpartyPaymentAccount;
    }
    public void setThirdpartyPaymentAccount(String thirdpartyPaymentAccount) {
        this.thirdpartyPaymentAccount = thirdpartyPaymentAccount;
    }
    public String getThirdpartyPaymentName() {
        return thirdpartyPaymentName;
    }
    public void setThirdpartyPaymentName(String thirdpartyPaymentName) {
        this.thirdpartyPaymentName = thirdpartyPaymentName;
    }
    public String getBankName() {
        return bankName;
    }
    public void setBankName(String bankName) {
        this.bankName = bankName;
    }
    public String getBankAddress() {
        return bankAddress;
    }
    public void setBankAddress(String bankAddress) {
        this.bankAddress = bankAddress;
    }
    public String getBankCode() {
        return bankCode;
    }
    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }
    public String getBankCardNo() {
        return bankCardNo;
    }
    public void setBankCardNo(String bankCardNo) {
        this.bankCardNo = bankCardNo;
    }
    public String getBankCardName() {
        return bankCardName;
    }
    public void setBankCardName(String bankCardName) {
        this.bankCardName = bankCardName;
    }
    public String getWithdrawDepositRemark() {
        return withdrawDepositRemark;
    }
    public void setWithdrawDepositRemark(String withdrawDepositRemark) {
        this.withdrawDepositRemark = withdrawDepositRemark;
    }
    public Long getRefundAmount() {
        return refundAmount;
    }
    public void setRefundAmount(Long refundAmount) {
        this.refundAmount = refundAmount;
    }
    public Integer getState() {
        return state;
    }
    public void setState(Integer state) {
        this.state = state;
    }
}