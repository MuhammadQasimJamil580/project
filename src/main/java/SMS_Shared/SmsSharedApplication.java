package SMS_Shared;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
@EnableDiscoveryClient
public class SmsSharedApplication {
	@Bean
	public WebClient.Builder getWebClient(){
		return WebClient.builder();
	}

	public static void main(String[] args) {
		SpringApplication.run(SmsSharedApplication.class, args);
	}
	@Bean
	public ModelMapper modelMapper(){
		ModelMapper mp = new ModelMapper();
		mp.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		return mp;
	}
}
