package club.yinlihu.mapper;

import club.yinlihu.datasource.dynamic.DatasourceType;
import club.yinlihu.datasource.dynamic.YinlihuDatasource;
import club.yinlihu.entity.User;
@YinlihuDatasource(DatasourceType.CLUSTER)
public interface UserMapper {
	User findByName(String name);


	User findByName2(String name);
}