package com.fise.base;

import com.fise.base.BaseResponse;

public class Response extends BaseResponse {
	
	private static final long serialVersionUID = -5951407266077063966L;

	private Object data;
	
	public Response success() {
		super.setErrorCode(ErrorCode.ERROR_OK);
		return this;
	}
	
	public Response success(Object data) {
		super.setErrorCode(ErrorCode.ERROR_OK);
		this.setData(data);
		
		return this;
	}
	
	public Response failure(ErrorCode code) {
		super.setErrorCode(code);
		
		return this;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
