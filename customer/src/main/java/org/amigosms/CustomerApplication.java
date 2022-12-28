package org.amigosms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CustomerApplication {
    public static void main(String[] args) {
        System.out.println("Iniciando servicio de Clientes");
        SpringApplication.run(CustomerApplication.class, args);
    }
}