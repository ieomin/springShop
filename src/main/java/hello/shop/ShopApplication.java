package hello.shop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Import(AppConfig.class)
//@SpringBootApplication(scanBasePackages = "hello.shop.web")
// 팁: Base클래스 적용하는 어노테이션
@EnableJpaAuditing
@SpringBootApplication
public class ShopApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShopApplication.class, args);
	}

}
