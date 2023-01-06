package org.amigosms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@SpringBootApplication
@EnableEurekaClient //Configurarlo como cliente de Eureka
@EnableFeignClients(basePackages = "org.amigosms.clients")
@PropertySources({
        //Levanta las properties del profile activo:
        //Si es default, usa las properties del entorno local ej: clients-default.properties
        //Si es kubernetes, usa las properties para el entorno de ks8 ej: clients-kube.properties
        @PropertySource("classpath:clients-${spring.profiles.active}.properties")
})
public class FraudApplication {
    public static void main(String[] args) {
        System.out.println("Iniciando servicio de Fraudes");
        SpringApplication.run(FraudApplication.class, args);
    }
}