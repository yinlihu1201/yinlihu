package club.yinlihu.controller;

import club.yinlihu.entity.ResultMap;
import club.yinlihu.openapi.OpenApi;
import club.yinlihu.schedule.execute.ScheduleTrigger;
import club.yinlihu.schedule.init.ScheduleInitialization;
import club.yinlihu.schedule.init.impl.DefaultScheduleInitialization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import club.yinlihu.service.UserServiceImpl;

import java.util.Map;

@RestController
public class LoginController {

	public LoginController(){
		ScheduleInitialization init = new DefaultScheduleInitialization();
		init.init("club.yinlihu.schedule.process.demonstration");
	}
	
	@Autowired
	private UserServiceImpl userServiceImpl;
	
	
	@RequestMapping("login")
	@OpenApi("login")
	public ResultMap login(Map<String,Object> map) {
		userServiceImpl.getUser();
		return ResultMap.ok();
	}

	@RequestMapping("bbb")
	public ResultMap bbb(Map<String,Object> map) {
		ScheduleTrigger.getInstance().startTrigger("123", "schedule1");
		return ResultMap.ok();
	}
	@RequestMapping("ccc")
	public ResultMap ccc(Map<String,Object> map) {
		ScheduleTrigger.getInstance().continueTrigger("123");
		return ResultMap.ok();
	}

	@RequestMapping("ddd")
	public ResultMap ddd(Map<String,Object> map) {
		ScheduleTrigger.getInstance().skipTrigger("123");
		return ResultMap.ok();
	}
	@RequestMapping("e")
	public ResultMap e(Map<String,Object> map) {
		ScheduleTrigger.getInstance().terminateSchedule("123");
		return ResultMap.ok();
	}
}
