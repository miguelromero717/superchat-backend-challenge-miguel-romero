package de.superchat.backendchallenge.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ExternalConfig {

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

}
