package com.fise.service.data.impl;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fise.dao.DataManagerMapper;
import com.fise.model.dto.ClientPlaceCount;
import com.fise.model.dto.ClientTypeCount;
import com.fise.model.dto.Devices;
import com.fise.model.dto.Register;
import com.fise.model.dto.XWInfo;
import com.fise.model.param.RegisterTime;
import com.fise.service.data.DataManagerService;



@Service
public class DataManagerServiceImpl implements DataManagerService{

	@Autowired
	DataManagerMapper dataManagerDao;
	
	@Override
	public Devices selectActiveDeviceCount(int departId) {
		return dataManagerDao.selectActiveDeviceCount(departId);
	}

	@Override
	public XWInfo selectXWCount(int departId) {
		return dataManagerDao.selectXWCount(departId);
	}

	@Override
	public List<ClientTypeCount> findTypeCount(int DepartId) {
		return dataManagerDao.findTypeCount(DepartId);
	}

	@Override
	public List<ClientPlaceCount> findPlaceCount(int DepartId) {
		return dataManagerDao.findPlaceCount(DepartId);
	}

	@Override
	public int findAllCount(int DepartId) {
		return dataManagerDao.findAllCount(DepartId);
	}

	@Override
	public List<Register> getRegisterCount(RegisterTime registerTime) {
		return dataManagerDao.getRegisterCount(registerTime);
	}

}
