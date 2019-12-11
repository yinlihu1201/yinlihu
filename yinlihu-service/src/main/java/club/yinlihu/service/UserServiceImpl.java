package club.yinlihu.service;

import club.yinlihu.entity.User;
import club.yinlihu.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl {
	@Autowired
	private UserMapper userMapper;

	public void getUser() {
		User user = userMapper.findByName("asdf");
		System.out.println(user.getId());
		User findByName = userMapper.findByName2("user2");
		System.out.println(findByName.getId());
	}
}
