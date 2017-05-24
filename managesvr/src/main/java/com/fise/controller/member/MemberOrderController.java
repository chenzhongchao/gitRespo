package com.fise.controller.member;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fise.base.ErrorCode;
import com.fise.base.HttpContext;
import com.fise.base.Page;
import com.fise.base.Response;
import com.fise.framework.annotation.IgnoreAuth;
import com.fise.model.dto.GymDto;
import com.fise.model.entity.Gym;
import com.fise.model.entity.Order;
import com.fise.model.param.LoginParam;
import com.fise.model.param.LogoutParam;
import com.fise.model.param.OrderPayParam;
import com.fise.model.param.RegisterParam;
import com.fise.model.result.OrderForMemberResult;
import com.fise.model.result.RegisterResult;
import com.fise.service.member.IMemberService;
import com.fise.service.order.IOrderService;
import com.fise.utils.Constants;
import com.fise.utils.StringUtil;


@RestController
@RequestMapping("/member")
public class MemberOrderController {
	@Resource
	private IMemberService memberService;
	
	@Resource
	private IOrderService orderService;
	
	// 订单列表（待付款、待评价、已完成通过入参来判断）
	@RequestMapping(value = "/order/list", method = RequestMethod.POST)
	public Response getOrderList(@RequestBody @Valid Page<Order> pageParam) {
		Response resp = new Response();
		resp = orderService.memberGetOrderList(pageParam);
		
		List<OrderForMemberResult> orderList = new ArrayList<OrderForMemberResult>();
		List<?> data = (List<?>)resp.getData();
		for (Object order : data) {
			if (order instanceof OrderForMemberResult) {
				orderList.add((OrderForMemberResult) order);
			}
		}
			
		Page<OrderForMemberResult> page = new Page<OrderForMemberResult>();
		page.setTotalCount(pageParam.getTotalCount());
		page.setPageNo(pageParam.getPageNo());
		page.setPageSize(pageParam.getPageSize());
		page.setResult(orderList);
		resp.success(page);
			
		return resp;
	}
	
	// 付款
	@RequestMapping(value = "/order/pay", method = RequestMethod.POST)
	public Response orderPay(@RequestBody @Valid OrderPayParam param) {
		Response resp = new Response();
		
		resp = orderService.orderPay(param);
		
		return resp;
	}
	
	// 订单详情
	@RequestMapping(value = "/order/detail/{sn}", method = RequestMethod.POST)
	public Response orderDetail(@PathVariable String sn) {
		Response resp = new Response();
		
		Integer memberId = HttpContext.getMemberId();
		resp = orderService.getOrderDetail(memberId, sn);
		
		return resp;
	}
}
