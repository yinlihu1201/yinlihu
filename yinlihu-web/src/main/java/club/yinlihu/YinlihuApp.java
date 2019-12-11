package club.yinlihu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.freemarker.FreeMarkerAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

//@MapperScan("club.yinlihu.mapper")
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class,FreeMarkerAutoConfiguration.class})
public class YinlihuApp {
	public static void main(String[] args) {
		SpringApplication.run(YinlihuApp.class, args);
	}
}
