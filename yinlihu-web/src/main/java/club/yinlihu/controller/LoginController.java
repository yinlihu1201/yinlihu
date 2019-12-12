package club.yinlihu.controller;

import club.yinlihu.entity.ResultMap;
import club.yinlihu.openapi.OpenApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import club.yinlihu.service.UserServiceImpl;

import java.util.Map;

@RestController
public class LoginController {
	
	@Autowired
	private UserServiceImpl userServiceImpl;
	
	
	@RequestMapping("login")
	@OpenApi("login")
	public ResultMap login(Map<String,Object> map) {
		userServiceImpl.getUser();
		return ResultMap.ok();
	}
	
}
