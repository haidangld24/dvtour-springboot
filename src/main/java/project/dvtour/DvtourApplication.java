package project.dvtour;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import lombok.AllArgsConstructor;
import project.dvtour.repository.TourRepository;

@SpringBootApplication
@AllArgsConstructor
public class DvtourApplication{

	TourRepository tourRepository;

	public static void main(String[] args) {
		SpringApplication.run(DvtourApplication.class, args);
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder(){
		return new BCryptPasswordEncoder();
	}

	@Bean
	public WebMvcConfigurer corsConfigurer(){
		return new WebMvcConfigurer() {
			public void addCorsMappings(CorsRegistry registry){
				registry.addMapping("/**")
					.allowedMethods("*")
					.allowedOrigins("http://localhost:3000")
					.allowedHeaders("*")
					.exposedHeaders("*")
					.allowCredentials(true);
			}
		};
	}
}
