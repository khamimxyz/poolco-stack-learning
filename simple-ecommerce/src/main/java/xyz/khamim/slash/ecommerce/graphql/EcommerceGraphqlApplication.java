package xyz.khamim.slash.ecommerce.graphql;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.reactive.config.EnableWebFlux;

@EnableFeignClients
@EnableWebFlux
@SpringBootApplication(exclude = {
	SecurityAutoConfiguration.class,
	WebMvcAutoConfiguration.class
})
public class EcommerceGraphqlApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcommerceGraphqlApplication.class, args);
	}

}
