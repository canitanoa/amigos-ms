package org.amigosms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient //Configurarlo como cliente de Eureka
@EnableFeignClients(basePackages = "org.amigosms.clients")
public class CustomerApplication {
    public static void main(String[] args) {
        System.out.println("Iniciando servicio de Clientes");
        SpringApplication.run(CustomerApplication.class, args);
    }
}