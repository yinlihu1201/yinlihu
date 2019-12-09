package club.yinlihu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

//@MapperScan("club.yinlihu.mapper")
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class YinlihuApp {
	public static void main(String[] args) {
		SpringApplication.run(YinlihuApp.class, args);
	}
}
