package com.fise.controller.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;


import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fise.model.dto.AssociateUser;
import com.fise.model.dto.UserRelation;
import com.fise.model.entity.UserInfo;
import com.fise.model.result.User;
import com.fise.service.user.UserService;




@RestController
public class UserController {
	
	@Resource
	UserService userService;
	

	@RequestMapping("/UserManage/QueryUser")
	public User QueryUser(@RequestBody Paran4 param){
		Map<String,String> map=new HashMap<String,String>();
		map.put("UserName", param.UserName);
		map.put("AuthenticCode", param.AuthenticCode);
		map.put("XWNo", param.XWNo);
		UserInfo userInfo=userService.findUserInfo(map);
		User user=new User(param.UserName,param.AuthenticCode);
		List<UserInfo> list=new ArrayList<UserInfo>();
		list.add(userInfo);
		user.setUserInfo(list);
		return user;
	}
	

	@RequestMapping("/UserManage/QueryUserFriend")
	public User QueryUserFriend(@RequestBody Paran4 param){
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("XWNo", param.XWNo);
		map.put("Page", param.Page);
		map.put("PageSize", param.PageSize);
		AssociateUser associateuser=userService.findUserFriend(map);
		associateuser.setName(param.XWNo);
		List<UserRelation> list=associateuser.getUseRelation();
		List<UserInfo> list1=new ArrayList<UserInfo>();
		UserInfo userInfo=null;
		for(UserRelation u:list){	
			if(u.getSmallId()==associateuser.getId()){
				userInfo=userService.findUserById(u.getBigId());
				list1.add(userInfo);
			}else{
				userInfo=userService.findUserById(u.getSmallId());
				list1.add(userInfo);
			}
		}
		User user=new User(param.UserName,param.AuthenticCode);
		user.setFriendCount(list1.size());
		user.setUserInfo(list1);
		return user;
	}
}

class Paran4{
	public String UserName;
	public String AuthenticCode;
	public String XWNo;
	public int Page;
	public int PageSize;
}
