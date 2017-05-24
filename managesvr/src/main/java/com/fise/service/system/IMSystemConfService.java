package com.fise.service.system;

import java.util.List;

import com.fise.model.result.StageConf;

public interface IMSystemConfService {
	public void insert_backstageconf(StageConf stageConf);
	public void delete_backstageconf(int BackStageId);
	public List<StageConf> findbackstageinfos(String BackStageConfName);
	public void update_backstageconf(StageConf stageConf);
	public List<StageConf> findallbackstageinfos();
}
