package org.amigosms;

import com.thoughtworks.xstream.converters.time.LocalDateTimeConverter;
import lombok.AllArgsConstructor;
import org.amigosms.clients.fraud.FraudCheckResponse;
import org.amigosms.clients.fraud.FraudClient;
import org.amigosms.clients.notification.NotificationClient;
import org.amigosms.clients.notification.NotificationRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
//    private final RestTemplate restTemplate;
    private final FraudClient fraudClient;
//    private final NotificationClient notificationClient;

    private final RabbitMQMessageProducer rabbitMQMessageProducer;

    public void registerCustomer(CustomerRegistrationRequest request) {

        Customer customer = Customer.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .build();
        //todo: check if email valid
        //todo: check if email not taken

        customerRepository.saveAndFlush(customer);

        //Consumo el servicio de Fraudes, para ver si es usuario fraudulento
        //por RestTemplate
        /* FraudCheckResponse fraudCheckResponse = restTemplate.getForObject(
                //Sin Eureka
                //"http://localhost:8081/api/v1/fraud-check/{customerId}",
                //Con Eureka
                "http://FRAUD/api/v1/fraud-check/{customerId}",
                FraudCheckResponse.class,customer.getId()
        );*/
        //por OpenFeign
        FraudCheckResponse fraudCheckResponse =
                fraudClient.isFraudster(customer.getId());

        if (fraudCheckResponse.isFrauster()){
            throw new IllegalStateException("fraudster");
        }

        //Armo el objeto a notificar
        NotificationRequest notificationRequest = new NotificationRequest(
                customer.getId(),
                customer.getEmail(),
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")).toString(),
                String.format("Hi %s, welcome to AmigosMS...", customer.getFirstName())
        );

        //Se envia la Notificacion a BD (SYNC)
        //notificationClient.sendNotification(notificationRequest);

        //todo: make it async. add to queue
        //Se realiza la notificacion a la Queue (ASYNC)
        rabbitMQMessageProducer.publish(
                notificationRequest,
                "internal.exchange",
                "internal.notification.routing-key"

        );
    }
}
