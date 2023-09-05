
package net.javaguides.springboot;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.context.annotation.Bean;



//@SpringBootApplication(exclude = {UserDetailsServiceAutoConfiguration.class })
@SpringBootApplication
public class SpringbootBackendApplication {
	@Bean
	public ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration()
				.setMatchingStrategy(MatchingStrategies.STANDARD);
		return modelMapper;
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringbootBackendApplication.class, args);
	}


}
