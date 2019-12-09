package club.yinlihu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import club.yinlihu.entity.User;
import club.yinlihu.mapper.UserMapper;
import club.yinlihu.mapper.read.UserReadMapper;

@Service
public class UserServiceImpl {
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private UserReadMapper userReadMapper;
	
	public void getUser() {
		User user = userMapper.findByName("asdf");
		System.out.println(user.getName());
		User findByName = userReadMapper.findByName("user2");
		System.out.println(findByName.getName());
	}
}
