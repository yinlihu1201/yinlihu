package club.yinlihu.datasource.normal;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

/**
 *  数据源master配置
 * @author Administrator
 *
 */
// @Configuration
// @MapperScan(basePackages = "club.yinlihu.mapper", sqlSessionFactoryRef = "masterSqlSessionFactory")
public class MasterDatasourceConfig {
	
	/**
	 * 数据源配置
	 * @return
	 */
	/*@Bean(name="masterDatasource")
	@ConfigurationProperties(prefix = "spring.datasource.master")
	@Primary*/
	public DataSource masterDatasource() {
		return DataSourceBuilder.create().build();
	}
	
	/**
	 * session控制
	 * @param dataSource
	 * @return
	 * @throws Exception
	 */
	/*@Bean(name = "masterSqlSessionFactory")
	@Primary*/
	public SqlSessionFactory masterSqlSessionFactory(@Qualifier("masterDatasource") DataSource dataSource) throws Exception{
		SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
		bean.setDataSource(dataSource);
		bean.setMapperLocations(
                // 设置mybatis的xml所在位置
                new PathMatchingResourcePatternResolver().getResources("classpath*:mybatis/mapper/*.xml"));
		return bean.getObject();
	}
	
	/**
	 * 事务配置
	 * @return
	 */
	/*@Bean(name = "masterTransactionManager")
	public DataSourceTransactionManager masterTransactionManager(@Qualifier("masterDatasource") DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}*/
	
	/**
	 * sqlSessionTemplate:负责管理mybatis的sqlSession，调用mybatis的Sql方法，翻译异常
	 * 使用代理模式
	 * @param dataSource
	 * @return
	 */
	/*@Bean(name = "masterSqlSessionTemplate")
	@Primary*/
	public SqlSessionTemplate masterSqlSessionTemplate(@Qualifier("masterSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
		return new SqlSessionTemplate(sqlSessionFactory);
	}
}
