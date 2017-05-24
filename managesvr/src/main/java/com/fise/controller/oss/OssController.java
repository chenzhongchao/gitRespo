package com.fise.controller.oss;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fise.base.Response;
import com.fise.utils.OSSUtil;

@RestController
@RequestMapping("/manage")
public class OssController {
	
	@RequestMapping(value = "/oss/sign", method = RequestMethod.POST)
	public Response genUploadUrl() {
		Response resp = new Response();
		
		resp = OSSUtil.genSign();
		
		return resp;
	}
}
