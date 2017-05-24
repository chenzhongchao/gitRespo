package com.fise.model.entity;

public class FiseDevice {
	private int DeviceId;
	private String DeviceIME;
	private String DeviceXW;
	private int DepartId;
	private int ProductType;
	private int ProductName;
	private String PhoneNo;
	private int status;
	
	
	
	public int getDeviceId() {
		return DeviceId;
	}
	public void setDeviceId(int deviceId) {
		DeviceId = deviceId;
	}
	public int getProductName() {
		return ProductName;
	}
	public void setProductName(int productName) {
		ProductName = productName;
	}
	public String getPhoneNo() {
		return PhoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		PhoneNo = phoneNo;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getDeviceIME() {
		return DeviceIME;
	}
	public void setDeviceIME(String deviceIME) {
		DeviceIME = deviceIME;
	}
	public String getDeviceXW() {
		return DeviceXW;
	}
	public void setDeviceXW(String deviceXW) {
		DeviceXW = deviceXW;
	}
	public int getDepartId() {
		return DepartId;
	}
	public void setDepartId(int departId) {
		DepartId = departId;
	}
	public int getProductType() {
		return ProductType;
	}
	public void setProductType(int productType) {
		ProductType = productType;
	}
	
	public FiseDevice(String deviceIME, String deviceXW, int departId,
			int productType) {
		super();
		
		DeviceIME = deviceIME;
		DeviceXW = deviceXW;
		DepartId = departId;
		ProductType = productType;
	}
	public FiseDevice() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
