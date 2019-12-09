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
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

/**
 *  数据源write配置
 * @author Administrator
 *
 */
@Configuration
@MapperScan(basePackages = "club.yinlihu.mapper", sqlSessionFactoryRef = "writeSqlSessionFactory")
public class WriteDatasourceConfig {
	
	/**
	 * 数据源配置
	 * @return
	 */
	@Bean(name="writeDatasource")
	@ConfigurationProperties(prefix = "spring.datasource.write")
	@Primary
	public DataSource writeDatasource() {
		return DataSourceBuilder.create().build();
	}
	
	/**
	 * session控制
	 * @param dataSource
	 * @return
	 * @throws Exception
	 */
	@Bean(name = "writeSqlSessionFactory")
	@Primary
	public SqlSessionFactory writeSqlSessionFactory(@Qualifier("writeDatasource") DataSource dataSource) throws Exception{
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
	/*@Bean(name = "writeTransactionManager")
	public DataSourceTransactionManager writeTransactionManager(@Qualifier("writeDatasource") DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}*/
	
	/**
	 * sqlSessionTemplate:负责管理mybatis的sqlSession，调用mybatis的Sql方法，翻译异常
	 * 使用代理模式
	 * @param dataSource
	 * @return
	 */
	@Bean(name = "writeSqlSessionTemplate")
	@Primary
	public SqlSessionTemplate writeSqlSessionTemplate(@Qualifier("writeSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
		return new SqlSessionTemplate(sqlSessionFactory);
	}
}
