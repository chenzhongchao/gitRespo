package com.fise.service.gymitem.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fise.dao.GymItemMapper;
import com.fise.model.entity.GymItem;
import com.fise.service.gymitem.IGymItemService;

@Service
public class GymItemServiceImpl implements IGymItemService {
	@Autowired
	private GymItemMapper gymItemDao;

	@Override
	public List<GymItem> getGymItemByGymId(Integer gymId) {
		return gymItemDao.selectByGymId(gymId);
	}

	@Override
	public void updateGymItem(GymItem gymItem) {
		gymItemDao.updateByPrimaryKeySelective(gymItem);
	}

	@Override
	public void insertGymItem(GymItem gymItem) {
		gymItemDao.insertGymItem(gymItem);
	}

	@Override
	public List<GymItem> getGymItembyGymItemSelective(GymItem gymItem) {
		return gymItemDao.selectGymItemBySelective(gymItem);
	}
}
