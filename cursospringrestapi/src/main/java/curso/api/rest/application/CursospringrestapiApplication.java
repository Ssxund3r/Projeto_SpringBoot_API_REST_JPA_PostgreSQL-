package curso.api.rest.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.Ordered;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EntityScan(basePackages = {"curso.api.rest.model"})
@ComponentScan(basePackages = {"curso.*"})
@EnableJpaRepositories(basePackages = {"curso.api.rest.repository"})
@EnableTransactionManagement
@EnableWebMvc
@RestController
@EnableAutoConfiguration
@EnableCaching
public class CursospringrestapiApplication implements WebMvcConfigurer{

	public static void main(String[] args) {
		SpringApplication.run(CursospringrestapiApplication.class, args);
		System.out.println(new BCryptPasswordEncoder().encode("123"));
	}
	
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/login").setViewName("/login");
		registry.setOrder(Ordered.LOWEST_PRECEDENCE);
		
	}
	
	//Mapeamento global que refletem em todo o sistema
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/usuario/**")
		.allowedMethods("*")
		.allowedOrigins("*");
	}

}

