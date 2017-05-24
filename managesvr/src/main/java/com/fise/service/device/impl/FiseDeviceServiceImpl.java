package com.fise.service.device.impl;

import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fise.dao.FiseDeviceMapper;
import com.fise.model.dto.DeviceTypeInfo;
import com.fise.model.dto.Devices;
import com.fise.model.entity.DeviceInfo;
import com.fise.model.entity.FiseDevice;
import com.fise.service.device.FiseDeviceService;



@Service
public class FiseDeviceServiceImpl implements FiseDeviceService{

	@Autowired
	FiseDeviceMapper fiseDeviceDao;
	
	@Override
	public void insert_FiseDevices(List<FiseDevice> list) {
		fiseDeviceDao.insert_FiseDevices(list);		
	}

	@Override
	public DeviceInfo query_deviceInfo(Map map) {
		return fiseDeviceDao.query_deviceInfo(map);
	}

	@Override
	public void delete_device(int DeviceId) {
		fiseDeviceDao.delete_device(DeviceId);	
	}

	@Override
	public void update_deviceInfo(DeviceInfo deviceInfo) {
		fiseDeviceDao.update_deviceInfo(deviceInfo);
	}

	@Override
	public List<FiseDevice> findpartdeviceinfo(Map map) {
		return fiseDeviceDao.findpartdeviceinfo(map);
	}

	@Override
	public List<DeviceTypeInfo> findAllDeviceInfo() {
		return fiseDeviceDao.findAllDeviceInfo();
	}

	@Override
	public List<Devices> findAllDeviceCount() {
		return fiseDeviceDao.findAllDeviceCount();
	}

	@Override
	public Devices getCompanyDeviceInfo(int DepartId) {
		return fiseDeviceDao.getCompanyDeviceInfo(DepartId);
	}
	
}
