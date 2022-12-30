package org.amigosms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient //Configurarlo como cliente de Eureka
public class ApiGWApplication {
    public static void main(String[] args) {

        System.out.println("Iniciando el APIGateWay");
        SpringApplication.run(ApiGWApplication.class, args);
    }
}