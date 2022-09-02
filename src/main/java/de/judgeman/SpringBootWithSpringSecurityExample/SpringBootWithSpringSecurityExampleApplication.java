package de.judgeman.SpringBootWithSpringSecurityExample;

import de.judgeman.SpringBootWithSpringSecurityExample.Exceptions.UsernameAlreadyExistsException;
import de.judgeman.SpringBootWithSpringSecurityExample.Services.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SpringBootWithSpringSecurityExampleApplication {

	public static void main(String[] args) throws UsernameAlreadyExistsException {
		ConfigurableApplicationContext context = SpringApplication.run(SpringBootWithSpringSecurityExampleApplication.class, args);

		context.getBean(UserService.class).init();
	}

}
