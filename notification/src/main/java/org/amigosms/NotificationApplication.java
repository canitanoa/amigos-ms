package org.amigosms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NotificationApplication {
    public static void main(String[] args) {
        System.out.println("Iniciando servicio de Notificaciones");
        SpringApplication.run(NotificationApplication.class, args);
    }
}