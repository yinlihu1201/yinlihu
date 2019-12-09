package club.yinlihu.mapper.read;

import club.yinlihu.entity.User;

public interface UserReadMapper {
	User findByName(String name);
}