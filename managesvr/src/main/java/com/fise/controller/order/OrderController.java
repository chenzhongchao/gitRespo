package com.fise.controller.order;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fise.base.Response;
import com.fise.model.param.OrderCreateParam;
import com.fise.service.order.IOrderService;

@RestController
public class OrderController {
	@Resource
	private IOrderService orderService;
	
	@RequestMapping(value = "/member/order/create", method = RequestMethod.POST)
	public Response createOrder(@RequestBody @Valid OrderCreateParam param) {
		Response resp = new Response(); 
		
		resp = orderService.createOrder(param);
		
		return resp;
	}
}
