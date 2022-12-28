package org.amigosms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FraudApplication {
    public static void main(String[] args) {
        System.out.println("Iniciando servicio de Fraudes");
        SpringApplication.run(FraudApplication.class, args);
    }
}