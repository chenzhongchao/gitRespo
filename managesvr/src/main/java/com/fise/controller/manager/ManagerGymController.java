package com.fise.controller.manager;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fise.base.ErrorCode;
import com.fise.base.Page;
import com.fise.base.Response;
import com.fise.model.dto.GymAccountDto;
import com.fise.model.dto.GymAccountSettleDto;
import com.fise.model.dto.GymDto;
import com.fise.model.dto.GymItemDto;
import com.fise.model.entity.Gym;
import com.fise.model.entity.GymAccount;
import com.fise.model.param.GymAccountSettleParam;
import com.fise.model.param.GymAddParam;
import com.fise.model.param.GymPasswdResetParam;
import com.fise.model.param.GymStatusParam;
import com.fise.model.param.GymUpdateParam;
import com.fise.model.param.ManagerLoginParam;
import com.fise.model.param.ManagerLogoutParam;
import com.fise.model.param.PasswdResetParam;
import com.fise.model.result.GymDetailResult;
import com.fise.model.result.GymMemberStatResult;
import com.fise.model.result.GymNearbyResult;
import com.fise.service.gym.impl.GymServiceImpl;
import com.fise.service.manager.IManagerGymService;
import com.fise.service.manager.IManagerService;
import com.fise.utils.Constants;

@RestController
@RequestMapping("/manage/gym")
public class ManagerGymController {
	@Resource
	IManagerGymService managerGymService;

	// 添加健身馆信息
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public Response add(@RequestBody @Valid GymAddParam param) {
		System.out.println("--------- GymAddParam = " + param.toString());

		Response resp = new Response();

		resp = managerGymService.addGym(param);

		return resp;
	}

	// 冻结健身馆
	@RequestMapping(value = "/lock", method = RequestMethod.POST)
	public Response freeze(@RequestBody @Valid GymStatusParam param) {
		Response resp = new Response();

		Integer status = 1; // 0启用， 1冻结， 2注销
		managerGymService.setGymStatus(param.getGymId(), status);

		return resp;
	}

	// 注销健身馆
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public Response delete(@RequestBody @Valid GymStatusParam param) {
		Response resp = new Response();

		Integer status = 2; // 0启用， 1冻结， 2注销
		managerGymService.setGymStatus(param.getGymId(), status);

		return resp;
	}

	// 重新开启健身馆
	@RequestMapping(value = "/unlock", method = RequestMethod.POST)
	public Response logout(@RequestBody @Valid GymStatusParam param) {
		Response resp = new Response();

		Integer status = 0; // 0启用， 1冻结， 2注销
		managerGymService.setGymStatus(param.getGymId(), status);

		return resp;
	}

	// 获取健身馆列表
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public Response getGymList(@RequestBody @Valid Page<Gym> pageParam) {
		Response resp = new Response();

		List<GymDto> gymDtoList = managerGymService.getGymList(pageParam);

		Page<GymDto> page = new Page<GymDto>();
		page.setTotalCount(pageParam.getTotalCount());
		page.setPageNo(pageParam.getPageNo());
		page.setPageSize(pageParam.getPageSize());
		page.setResult(gymDtoList);
		resp.success(page);

		return resp;
	}

	// 获取健身馆详细信息
	@RequestMapping(value = "/list/{id}", method = RequestMethod.POST)
	public Response getGymById(@PathVariable String id) {
		Response resp = new Response();

		Integer gymId = Integer.parseInt(id);
		GymDto gymDto = managerGymService.getGymById(gymId);
		if (gymDto == null) {
			resp.failure(ErrorCode.ERROR_GYM_INDB_IS_NULL);
			return resp;
		}
		List<GymItemDto> gymItemDtoList = managerGymService
				.getGymItemByGymId(gymId);
		GymDetailResult result = new GymDetailResult();

		result.setGymId(gymDto.getGymId());
		result.setGymName(gymDto.getGymName());
		result.setPassword(gymDto.getPlainPassword());
		result.setMinSpendAmount(gymDto.getMinSpendAmount());
		result.setProvince(gymDto.getProvince());
		result.setCity(gymDto.getCity());
		result.setRegion(gymDto.getRegion());
		result.setAddress(gymDto.getAddress());
		result.setLongitude(gymDto.getLongitude());
		result.setLatitude(gymDto.getLatitude());
		result.setTel(gymDto.getTel());
		result.setStatus(gymDto.getStatus());
		result.setGymItemDtoList(gymItemDtoList);
		result.setAvatar(gymDto.getAvatar());
		result.setCompanyName(gymDto.getCompanyName());
		result.setLegalPerson(gymDto.getLegalPerson());
		result.setIntroduction(gymDto.getIntroduction());
		result.setImgIntroductions(gymDto.getImgIntroductions());
		result.setLoginId(gymDto.getLoginId());

		resp.success(result);
		return resp;
	}

	// 编辑健身馆信息
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public Response updateGym(@RequestBody @Valid GymUpdateParam param) {
		Response resp = new Response();

		System.out.println("--------- GymAddParam = " + param.toString());
		resp = managerGymService.updateGym(param);

		return resp;
	}
	
	// 商户账户列表
	@RequestMapping(value = "/account/list", method = RequestMethod.POST)
	public Response gymAccountList(@RequestBody @Valid Page<Gym> pageParam) {
		Response resp = new Response();
		
		List<GymAccountDto> gymAccountDtoList = managerGymService.getGymAccountList(pageParam);
		Page<GymAccountDto> page = new Page<GymAccountDto>();
		page.setTotalCount(pageParam.getTotalCount());
		page.setPageNo(pageParam.getPageNo());
		page.setPageSize(pageParam.getPageSize());
		page.setResult(gymAccountDtoList);
		resp.success(page);
		
		return resp;
	}
	
	// 服务费列表
	@RequestMapping(value = "/account/settle/list", method = RequestMethod.POST)
	public Response gymAccountSettleList(@RequestBody @Valid Page<GymAccount> pageParam) {
		Response resp = new Response();
		
		List<GymAccountSettleDto> gymAccountSettleDtoList = managerGymService.getGymAccountSettleList(pageParam);
		Page<GymAccountSettleDto> page = new Page<GymAccountSettleDto>();
		page.setTotalCount(pageParam.getTotalCount());
		page.setPageNo(pageParam.getPageNo());
		page.setPageSize(pageParam.getPageSize());
		page.setResult(gymAccountSettleDtoList);
		resp.success(page);
		
		return resp;
	}
	
	// 服务费总揽
	@RequestMapping(value = "/account/settle/total", method = RequestMethod.POST)
	public Response gymAccountSettleTotal() {
		Response resp = new Response();
		resp = managerGymService.getGymAccountSettleTotal();
			
		return resp;
	}
	
	// 商户资金总揽
	@RequestMapping(value = "/account/total", method = RequestMethod.POST)
	public Response gymAccountTotal() {
		Response resp = new Response();
		resp = managerGymService.getGymAccountTotal();
				
		return resp;
	}
	
	// 平台与商户结算
	@RequestMapping(value = "/account/settle", method = RequestMethod.POST)
	public Response gymAccountSettle(@RequestBody @Valid GymAccountSettleParam param) {
		Response resp = new Response();
		
		resp = managerGymService.settleGymAccount(param);
			
		return resp;
	}
	
	// 商户的用户统计
	@RequestMapping(value = "/member/stat/list", method = RequestMethod.POST)
	public Response gymMemberStatList(@RequestBody @Valid Page<Gym> pageParam) {
		Response resp = new Response();

		resp = managerGymService.gymMemberStatList(pageParam);
		List<?> data = (List<?>)resp.getData();
		List<GymMemberStatResult> gymMemberStatResultList = new ArrayList<GymMemberStatResult>();
		for (Object gymMemberStatResult : data) {
			if (gymMemberStatResult instanceof GymMemberStatResult) {
				gymMemberStatResultList.add((GymMemberStatResult) gymMemberStatResult);
			}
		}
		
		Page<GymMemberStatResult> page = new Page<GymMemberStatResult>();
		page.setPageNo(pageParam.getPageNo());
		page.setPageSize(pageParam.getPageSize());
		page.setTotalCount(pageParam.getTotalCount());
		page.setResult(gymMemberStatResultList);
		resp.success(page);
				
		return resp;
	}
	
	// 平台用户数总览
	@RequestMapping(value = "/member/stat/total", method = RequestMethod.POST)
	public Response getMemberStatTotal() {
		Response resp = new Response();
			
		resp = managerGymService.getMemberStatTotal();
				
		return resp;
	}
	
	// 修改商户密码
	@RequestMapping(value = "/passwd/reset", method = RequestMethod.POST)
	public Response resetGymPasswd(@RequestBody @Valid GymPasswdResetParam param) {
		Response resp = new Response();
			
		resp = managerGymService.resetGymPasswd(param);
				
		return resp;
	}
}
