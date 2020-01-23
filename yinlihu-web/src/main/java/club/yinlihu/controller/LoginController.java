package club.yinlihu.controller;

import club.yinlihu.entity.ResultMap;
import club.yinlihu.openapi.OpenApi;
import club.yinlihu.schedule.execute.ScheduleListener;
import club.yinlihu.schedule.execute.ScheduleTrigger;
import club.yinlihu.schedule.persist.SchedulePersist;
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

	@RequestMapping("aaa")
	public ResultMap aaa(Map<String,Object> map) {
		ScheduleListener.listener();
		SchedulePersist.init();
		ScheduleTrigger.startTrigger("123", "schedule1");
		return ResultMap.ok();
	}
}
