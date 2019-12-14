package club.yinlihu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.freemarker.FreeMarkerAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

//@MapperScan("club.yinlihu.mapper")
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class,FreeMarkerAutoConfiguration.class})
public class YinlihuApp extends SpringBootServletInitializer {
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		// 注意这里要指向原先用main方法执行的Application启动类
		return builder.sources(YinlihuApp.class);
	}
	public static void main(String[] args) {
		SpringApplication.run(YinlihuApp.class, args);
	}
}
