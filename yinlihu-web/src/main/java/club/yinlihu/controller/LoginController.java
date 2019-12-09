package club.yinlihu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import club.yinlihu.service.UserServiceImpl;

@RestController
public class LoginController {
	
	@Autowired
	private UserServiceImpl userServiceImpl;
	
	
	@RequestMapping("login")
	public String login() {
		userServiceImpl.getUser();
		return "login";
	}
	
}
