package club.yinlihu.datasource;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

/**
 *  数据源read配置
 * @author Administrator
 *
 */
@Configuration
@MapperScan(basePackages = "club.yinlihu.mapper.read", sqlSessionFactoryRef = "readSqlSessionFactory")
public class ReadDatasourceConfig {
	
	/**
	 * 数据源配置
	 * @return
	 */
	@Bean(name="readDatasource")
	@ConfigurationProperties(prefix = "spring.datasource.read")
	public DataSource readDatasource() {
		return DataSourceBuilder.create().build();
	}
	
	/**
	 * session控制
	 * @param dataSource
	 * @return
	 * @throws Exception
	 */
	@Bean(name = "readSqlSessionFactory")
	public SqlSessionFactory readSqlSessionFactory(@Qualifier("readDatasource") DataSource dataSource) throws Exception{
		SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
		bean.setDataSource(dataSource);
		bean.setMapperLocations(
                // 设置mybatis的xml所在位置
                new PathMatchingResourcePatternResolver().getResources("classpath*:mybatis/mapper/read/*.xml"));
		return bean.getObject();
	}
	
	/**
	 * 事务配置
	 * @return
	 */
	@Bean(name = "readTransactionManager")
	public DataSourceTransactionManager readTransactionManager(@Qualifier("readDatasource") DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}
	
	/**
	 * sqlSessionTemplate:负责管理mybatis的sqlSession，调用mybatis的Sql方法，翻译异常
	 * 使用代理模式
	 * @param dataSource
	 * @return
	 */
	@Bean(name = "readSqlSessionTemplate")
	public SqlSessionTemplate readSqlSessionTemplate(@Qualifier("readSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
		return new SqlSessionTemplate(sqlSessionFactory);
	}
}
