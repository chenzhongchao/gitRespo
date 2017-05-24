package com.fise.base;

import java.io.Serializable;

import com.fise.base.ErrorCode;
import com.fise.utils.JsonUtil;

public class BaseResponse implements Serializable {
    
    private static final long serialVersionUID = 1L;
    // 响应编码
    private Integer code;
    // 响应消息
    private String msg;
    
    public BaseResponse() {
        this.code = ErrorCode.ERROR_OK.getCode();
        this.msg = ErrorCode.ERROR_OK.getMsg();
    }
    
    public BaseResponse(ErrorCode errCode) {
        this.code = errCode.getCode();
        this.msg = errCode.getMsg();
    }
    
    public BaseResponse(Integer code, String message) {
        this.code = code;
        this.msg = message;
    }
    
    public Integer getCode() {
        return code;
    }
    
    public void setCode(Integer code) {
        this.code = code;
    }
    
    public String getMsg() {
        return msg;
    }
    
    public void setMsg(String message) {
        this.msg = message;
    }
    
    public void setErrorCode(ErrorCode errCode) {
        this.code = errCode.getCode();
        this.msg = errCode.getMsg();
    }
    
    @Override
    public String toString() {
    	return JsonUtil.toJson(this);
    }
}
