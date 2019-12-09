package club.yinlihu.datasource.normal;

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
 *  数据源cluster配置
 * @author Administrator
 *
 */
// @Configuration
// @MapperScan(basePackages = "club.yinlihu.mapper.cluster", sqlSessionFactoryRef = "clusterSqlSessionFactory")
public class ClusterDatasourceConfig {
	
	/**
	 * 数据源配置
	 * @return
	 */
	@Bean(name="clusterDatasource")
	@ConfigurationProperties(prefix = "spring.datasource.cluster")
	public DataSource clusterDatasource() {
		return DataSourceBuilder.create().build();
	}
	
	/**
	 * session控制
	 * @param dataSource
	 * @return
	 * @throws Exception
	 */
	@Bean(name = "clusterSqlSessionFactory")
	public SqlSessionFactory clusterSqlSessionFactory(@Qualifier("clusterDatasource") DataSource dataSource) throws Exception{
		SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
		bean.setDataSource(dataSource);
		bean.setMapperLocations(
                // 设置mybatis的xml所在位置
                new PathMatchingResourcePatternResolver().getResources("classpath*:mybatis/mapper/cluster/*.xml"));
		return bean.getObject();
	}
	
	/**
	 * 事务配置
	 * @return
	 */
	@Bean(name = "clusterTransactionManager")
	public DataSourceTransactionManager clusterTransactionManager(@Qualifier("clusterDatasource") DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}
	
	/**
	 * sqlSessionTemplate:负责管理mybatis的sqlSession，调用mybatis的Sql方法，翻译异常
	 * 使用代理模式
	 * @param dataSource
	 * @return
	 */
	@Bean(name = "clusterSqlSessionTemplate")
	public SqlSessionTemplate clusterSqlSessionTemplate(@Qualifier("clusterSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
		return new SqlSessionTemplate(sqlSessionFactory);
	}
}
