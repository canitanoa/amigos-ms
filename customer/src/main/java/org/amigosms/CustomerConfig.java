package org.amigosms;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class CustomerConfig {

/*    @Bean
    @LoadBalanced //Para balancear la carga en las peticiones
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }*/
}
