package com.fise.service.gym;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.fise.base.Page;
import com.fise.base.Response;
import com.fise.model.entity.Gym;
import com.fise.model.entity.Order;
import com.fise.model.entity.Suggestion;
import com.fise.model.param.GymLoginParam;
import com.fise.model.param.GymLogoutParam;
import com.fise.model.param.InitParam;
import com.fise.model.param.OrderSettleParam;
import com.fise.model.param.ScanForEnterParam;
import com.fise.model.result.InitAppResult;


public interface IGymService {
	// 登录
	public Response login(GymLoginParam param, HttpServletRequest request);
	
	// 退出
	public Response logout(GymLogoutParam param, HttpServletRequest request);
	
	// 通过gymId获取gym实体
	public Gym getGymById(Integer gymId);
	
	// 扫码入场
	public Response insertScanForEnter(ScanForEnterParam param);
	
	// 结算
	public Response updateOrderSettls(OrderSettleParam param);
	
	// 获取附近的健身馆
	public Response getGymNearby(Page<Gym> pageParam);
	
	// 获取健身馆详情
	public Response getGymDetail(Integer gymId);
	
	// 更新商户信息
	public void updateGym(Gym gym);
	
	// 发布公告
	public void postBroadcast(Integer gymId, String content);
	
	// 新增意见反馈
	public void postFeedback(Suggestion suggestion);
	
	// app初始化
	public Response initApp(Integer gymId, InitParam param);
	
	// 获取商户信息
	public Response getGymProfile(Integer gymId);
	
	// 商户列表分页
	public List<Gym> getGymList(Page<Gym> pageParam);
}
