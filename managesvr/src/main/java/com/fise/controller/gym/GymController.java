package com.fise.controller.gym;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fise.base.HttpContext;
import com.fise.base.Page;
import com.fise.base.Response;
import com.fise.framework.annotation.IgnoreAuth;
import com.fise.model.dto.GymDto;
import com.fise.model.dto.GymItemDto;
import com.fise.model.entity.Gym;
import com.fise.model.entity.GymItem;
import com.fise.model.entity.Member;
import com.fise.model.entity.Order;
import com.fise.model.entity.Suggestion;
import com.fise.model.param.BroadcastParam;
import com.fise.model.param.FeedbackParam;
import com.fise.model.param.GymLoginParam;
import com.fise.model.param.GymLogoutParam;
import com.fise.model.param.InitParam;
import com.fise.model.param.ManagerLoginParam;
import com.fise.model.param.ManagerLogoutParam;
import com.fise.model.param.OrderSettleParam;
import com.fise.model.param.ScanForEnterParam;
import com.fise.model.result.MemberSimpleInfoResult;
import com.fise.model.result.OrderForGymResult;
import com.fise.service.gym.IGymService;
import com.fise.service.gymitem.IGymItemService;
import com.fise.service.manager.IManagerService;
import com.fise.service.member.IMemberService;
import com.fise.service.order.IOrderService;
import com.fise.service.order.impl.OrderServiceImpl;
import com.fise.utils.Constants;
import com.fise.utils.StringUtil;

@RestController
@RequestMapping("/gym")
public class GymController {
	@Resource
	private IGymService gymService;
	
	@Resource
	private IOrderService orderService;
	
	@Resource
	private IGymItemService gymItemService;
	
	@Resource
	private IMemberService memberService;
	
	// 登录
	@IgnoreAuth
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public Response login(@RequestBody @Valid GymLoginParam param, HttpServletRequest request) {
		Response resp = new Response();
		System.out.println("-------GymController: param = " + param.toString());
		resp = gymService.login(param, request);
		return resp;
	}
	
	// 退出
	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	public Response logout(@RequestBody @Valid GymLogoutParam param, HttpServletRequest request) {
		Response resp = new Response();
		
		resp = gymService.logout(param, request);
		
		return resp;
	}
	
	// 扫码入场
	@RequestMapping(value = "/scan/enter", method = RequestMethod.POST)
	public Response scanForEnter(@RequestBody @Valid ScanForEnterParam param) {
		Response resp = new Response();
		
		resp = gymService.insertScanForEnter(param);
		
		return resp;
	}
	
	// 订单列表（待结算、已结算通过入参来判断）
	@RequestMapping(value = "/order/list", method = RequestMethod.POST)
	public Response getOrderList(@RequestBody @Valid Page<Order> pageParam) {
		System.out.println("+++++++++++++gym getOrderList: pageParam=" + pageParam.toString());
		
		Response resp = new Response();
		resp = orderService.gymGetOrderList(pageParam);
		
		System.out.println("+++++++++++++gym getOrderList: resp=" + resp.toString());

		List<OrderForGymResult> orderList = new ArrayList<OrderForGymResult>();
		List<?> data = (List<?>)resp.getData();
		for (Object order : data) {
			if (order instanceof OrderForGymResult) {
				orderList.add((OrderForGymResult) order);
			}
		}
		
		Page<OrderForGymResult> page = new Page<OrderForGymResult>();
		page.setTotalCount(pageParam.getTotalCount());
		page.setPageNo(pageParam.getPageNo());
		page.setPageSize(pageParam.getPageSize());
		page.setResult(orderList);
		resp.success(page);
		
		return resp;
	}
	
	// 订单列表头部总览
	@RequestMapping(value = "/order/list/header/{status}", method = RequestMethod.POST)
	public Response getOrderListHeader(@PathVariable String status) {
		Response resp = new Response();
		
		Integer orderStatus = Integer.parseInt(status);
		resp = orderService.gymGetOrderListHeader(orderStatus);
		
		return resp;
	}
	
	// 结算（待结算状态->待付款）
	@RequestMapping(value = "/order/settle", method = RequestMethod.POST)
	public Response settleOrder(@RequestBody @Valid OrderSettleParam param) {
		Response resp = new Response();
		
		resp = gymService.updateOrderSettls(param);
		
		return resp;
	}
	
	// 获取场馆健身项目
	@RequestMapping(value = "/item/list", method = RequestMethod.POST)
	public Response getGymItemList(HttpServletRequest request) {
		Response resp = new Response();
		
		Integer gymId = HttpContext.getGymId();
        List<GymItem> gymItemList = gymItemService.getGymItemByGymId(gymId);
        List<GymItemDto> gymItemDtoList = new ArrayList<GymItemDto>();
		if (gymItemList != null) {
			for (GymItem gymItem : gymItemList) {
				if (gymItem.getStatus().intValue() != 0) continue;
				
				GymItemDto gymItemDto = new GymItemDto();
				gymItemDto.setGymId(gymItem.getGymId());
				gymItemDto.setGymItemId(gymItem.getItemId());
				gymItemDto.setGymItemName(gymItem.getItemName());
				gymItemDto.setGymItemPrice(gymItem.getItemPrice());
				gymItemDto.setStatus(gymItem.getStatus());
				
				gymItemDtoList.add(gymItemDto);
			}
		}
        resp.success(gymItemDtoList);
		
		return resp; 
	}
	
	// 发布公告
	@RequestMapping(value = "/broadcast/post", method = RequestMethod.POST)
	public Response postBroadcast(@RequestBody @Valid BroadcastParam param) {
		Response resp = new Response();
		
		Integer gymId = HttpContext.getGymId();
		String content = param.getContent();
		
		gymService.postBroadcast(gymId, content);
		
		return resp;
	}
	
	// 意见反馈
	@RequestMapping(value = "/feedback", method = RequestMethod.POST)
	public Response postFeedback(@RequestBody @Valid FeedbackParam param) {
		Response resp = new Response();
		
		Integer gymId = HttpContext.getGymId();
		String content = param.getContent();
		
		Suggestion suggestion = new Suggestion();
		suggestion.setSourceSystemId(Constants.SUGGUESTION_SOURCE_SYSTEM_GYM.shortValue());
		suggestion.setSuggestion(content);
		suggestion.setSourceUserId(gymId);
		suggestion.setState(Constants.SUGGUESTION_STATE_CHECKING);
		
		gymService.postFeedback(suggestion);
		
		return resp;
	}
	
	// 获取用户昵称及头像
	@RequestMapping(value = "/member/simpleinfo/{id}", method = RequestMethod.POST)
	public Response getMemberSimpleInfo(@PathVariable String id) {
		Response resp = new Response();
		
		Integer memberId = Integer.parseInt(id);
		Member member = memberService.getMemberById(memberId);
		MemberSimpleInfoResult result = new MemberSimpleInfoResult();
		result.setMemberId(member.getMemberId());
		result.setNickName(member.getNickName());
		result.setAvatar(member.getAvatar());
		
		resp.success(result);
		return resp;
	}
	
	// 初始化
	@RequestMapping(value = "/system/init", method = RequestMethod.POST)
	public Response initApp(@RequestBody @Valid InitParam param) {
		Response resp = new Response();
					
		Integer gymId = HttpContext.getGymId();
		resp = gymService.initApp(gymId, param);

		return resp;
	}
	
	// 获取商户画像
	@RequestMapping(value = "/profile", method = RequestMethod.POST)
	public Response getGymProfile() {
		Response resp = new Response();
					
		Integer gymId = HttpContext.getGymId();
		resp = gymService.getGymProfile(gymId);

		return resp;
	}	
}
