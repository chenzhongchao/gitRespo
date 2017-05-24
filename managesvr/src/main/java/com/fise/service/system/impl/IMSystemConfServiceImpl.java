package com.fise.service.system.impl;

import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fise.dao.IMSystemConfMapper;
import com.fise.model.result.StageConf;
import com.fise.service.system.IMSystemConfService;


@Service
public class IMSystemConfServiceImpl implements IMSystemConfService{

	@Autowired
	IMSystemConfMapper imSystemConfDao;
	
	@Override
	public void insert_backstageconf(StageConf stageConf) {
		imSystemConfDao.insert_backstageconf(stageConf);
	}

	@Override
	public void delete_backstageconf(int BackStageId) {
		imSystemConfDao.delete_backstageconf(BackStageId);	
	}

	@Override
	public List<StageConf> findbackstageinfos(String BackStageConfName) {
		return imSystemConfDao.findbackstageinfos(BackStageConfName);
	}

	@Override
	public void update_backstageconf(StageConf stageConf) {
		imSystemConfDao.update_backstageconf(stageConf);	
	}

	@Override
	public List<StageConf> findallbackstageinfos() {
		return imSystemConfDao.findallbackstageinfos();
	}

}
