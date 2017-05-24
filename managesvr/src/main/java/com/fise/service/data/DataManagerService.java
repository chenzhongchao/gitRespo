package com.fise.service.data;

import java.util.List;

import com.fise.model.dto.ClientPlaceCount;
import com.fise.model.dto.ClientTypeCount;
import com.fise.model.dto.Devices;
import com.fise.model.dto.Register;
import com.fise.model.dto.XWInfo;
import com.fise.model.param.RegisterTime;



public interface DataManagerService {
	public Devices selectActiveDeviceCount(int departId);
	public XWInfo selectXWCount(int departId);
	public List<ClientTypeCount> findTypeCount(int DepartId);
	public List<ClientPlaceCount> findPlaceCount(int DepartId);
	public int findAllCount(int DepartId);
	public List<Register> getRegisterCount(RegisterTime registerTime);
}
