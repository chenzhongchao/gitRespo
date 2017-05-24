package com.fise.service.manager;

import java.util.List;

import com.fise.base.Page;
import com.fise.base.Response;
import com.fise.model.dto.GymAccountDto;
import com.fise.model.dto.GymAccountSettleDto;
import com.fise.model.dto.GymDto;
import com.fise.model.dto.GymItemDto;
import com.fise.model.entity.Gym;
import com.fise.model.entity.GymAccount;
import com.fise.model.entity.GymItem;
import com.fise.model.param.GymAccountSettleParam;
import com.fise.model.param.GymAddParam;
import com.fise.model.param.GymPasswdResetParam;
import com.fise.model.param.GymUpdateParam;

public interface IManagerGymService {
	// 添加健身馆
	public Response addGym(GymAddParam param);
	
	// 更新健身馆信息
	public Response setGymStatus(Integer gymId, Integer status);
	
	// 获取健身馆列表
	public List<GymDto> getGymList(Page<Gym> pageParam);
	
	// 根据gymId获取健身馆详细信息
	public GymDto getGymById(Integer gymId);
	
	// 根据gymId获取健身项目数据
	public List<GymItemDto> getGymItemByGymId(Integer gymId);
	
	// 更新商户信息
	public Response updateGym(GymUpdateParam param);
	
	// 获取商户账户列表
	public List<GymAccountDto> getGymAccountList(Page<Gym> pageParam);
	
	// 获取商户账户结算列表
	public List<GymAccountSettleDto> getGymAccountSettleList(Page<GymAccount> pageParam);
	
	// 获取商户服务费结算总揽
	public Response getGymAccountSettleTotal();
	
	// 平台商家鉴权
	public Response settleGymAccount(GymAccountSettleParam param);
	
	// 获取商户资金总览
	public Response getGymAccountTotal();
	
	// 商家用户数据
	public Response gymMemberStatList(Page<Gym> pageParam);
	
	// 平台用户数总览
	public Response getMemberStatTotal();
	
	// 修改商户密码
	public Response resetGymPasswd(GymPasswdResetParam param);
}
