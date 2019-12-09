package club.yinlihu.mapper;

import club.yinlihu.entity.User;

public interface UserMapper {
	User findByName(String name);
}