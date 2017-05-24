package com.fise.service.device;


import java.util.List;
import java.util.Map;

import com.fise.model.dto.DeviceTypeInfo;
import com.fise.model.dto.Devices;
import com.fise.model.entity.DeviceInfo;
import com.fise.model.entity.FiseDevice;


public interface FiseDeviceService {
	public void insert_FiseDevices(List<FiseDevice> list);
	
	public DeviceInfo query_deviceInfo(Map map);
	
	public void delete_device(int DeviceId);
	
	public void update_deviceInfo(DeviceInfo deviceInfo);
	
	public List<FiseDevice> findpartdeviceinfo(Map map);
	
	public List<DeviceTypeInfo> findAllDeviceInfo();
	
	public Devices getCompanyDeviceInfo(int DepartId);
	
	public List<Devices> findAllDeviceCount();

	

}
