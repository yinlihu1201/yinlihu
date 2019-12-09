package club.yinlihu.mapper;

import club.yinlihu.datasource.dynamic.DatasourceType;
import club.yinlihu.datasource.dynamic.YinlihuDatasource;
import club.yinlihu.entity.User;

public interface UserMapper {
	User findByName(String name);

	@YinlihuDatasource(DatasourceType.CLUSTER)
	User findByName2(String name);
}