package org.amigosms;

import lombok.Getter;
import lombok.Setter;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
@Setter
public class NotificationConfig {

    //Definimos las propiedades que se van a llenar con la conf del application.yml
    @Value("${rabbitmq.exchanges.internal}")
    private String internalExchange;
    @Value("${rabbitmq.queue.notification}")
    private String notificationQueue;
    @Value("${rabbitmq.routing-keys.internal-notification}")
    private String internalNotificationRoutingKey;

    //Seteamos y definimos el Internal-Exchange
    @Bean
    public TopicExchange internalTopicExchange(){
        return new TopicExchange(this.internalExchange);
    }

    //Seteamos y definimos la Queue o Cola
    @Bean
    public Queue notificationQueue(){
        return new Queue(this.notificationQueue);
    }

    //Seteamos y definimos el Binding
    @Bean
    public Binding internalToNotificationBinding(){
        return BindingBuilder
                .bind(notificationQueue()) //Unimos la cola
                .to(internalTopicExchange()) //Al internal-exchange
                .with(this.internalNotificationRoutingKey); //Con la key
    }
}
