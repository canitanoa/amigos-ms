package org.amigosms;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@SpringBootApplication(
        scanBasePackages = {
                "org.amigosms"  //Para inyectar el producer del proyecto amqp
        }
)
@EnableEurekaClient //Configurarlo como cliente de Eureka
@PropertySources({
        //Levanta las properties del profile activo:
        //Si es default, usa las properties del entorno local ej: clients-default.properties
        //Si es kubernetes, usa las properties para el entorno de ks8 ej: clients-kube.properties
        @PropertySource("classpath:clients-${spring.profiles.active}.properties")
})
public class NotificationApplication {
    public static void main(String[] args) {
        System.out.println("Iniciando servicio de Notificaciones");
        SpringApplication.run(NotificationApplication.class, args);
    }

/*    @Bean
    CommandLineRunner commandLineRunner(
            RabbitMQMessageProducer producer,
            NotificationConfig notificationConfig
            ) {
        return args -> {
            producer.publish(
                    new Person("Adrian Canitano", 35),
                    notificationConfig.getInternalExchange(),
                    notificationConfig.getInternalNotificationRoutingKey());
        };
    }

    record Person(String name, int age){}*/

}