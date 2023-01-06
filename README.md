# amigos-ms
Repositorio con imple de MS

```bash
1- Crear repositorio en GitHub 
2- git init 
3- git add . 
4- git commit -m "first commit" 
5- git remote add origin https://github.com/canitanoa/amigos-ms.git 
6- git push -u origin master 
```

## Componenetes:


### Eureka:
http://localhost:8761/

*Se debe deshabilitar para kubernetes ya que se reemplaza por los Services*

### APIGW:
El componenete apigw se utiliza como puerta de enlace y balanceador de carga hacia los componentes internos. 
#### Imple:
Agregar a las dependencias del *pom.xml* 
```xml
<!--Para que se un GateWay-->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-gateway</artifactId>
</dependency>
```
En el *application.yml* configurar el rutoe a los componentes internos de la arq
```yaml
  cloud:
    gateway:
      routes:
        - id: customer
          uri: lb://CUSTOMER
          predicates:
            - Path=/api/v1/customers/**
```

### AMQP:
El componente *AMQP* se va a utlizar como intermediario de mensajes va a contener a los Producers y Consumers
#### Imple:
En el proyecto *AMQP* agregar a las dependencias del *pom.xml*
```xml
<!--Para agregar los módulos spring-amqp y spring-rabbit-->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-amqp</artifactId>
</dependency>
```
Creamos las clases:
```txt
- RabbitMQConfig.java -> Es la clase de configuracion
- RabbitMQMessageProducer.java -> Es la clase que produce los mensajes
```

En los proyectos que implementen AMQP (customer y notificaciones) 
1. Agregar las dependencias al pom.xml
```txt
<!--Configurar AMQP-->
<!--Para agregar los módulos spring-amqp y spring-rabbit-->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-amqp</artifactId>
</dependency>
<!--Para visualizar el proy AMQP-->
<dependency>
    <groupId>org.amigosms</groupId>
    <artifactId>clients</artifactId>
    <version>1.0-SNAPSHOT</version>
    <!--<scope>compile</scope>-->
</dependency>
```
2. Comfigurar el los *application.xml*
```yaml
rabbitmq:
  exchanges:
    internal: internal.exchange #Nombre del exchange
  queue:
    notification: notification.queue #Nombre de la cola o queue
  routing-keys:
    internal-notification: internal.notification.routing-key
```
3. Crear las clases de configuracion para cada proyecto:
```txt
NotificationConfig.java 
  En esta clase se definen: 
    - internal-exchange
    - queue
    - binding

```

### K8S:
Contiene las configuraciones del cluster de kubernetes para todos los componentes que se deplieguen en la plataforma:
Se definen los siguientes manifiestos:
- configmap.yml
- service.yml
- statefulset.yml
- volume.yml

```txt

Para iniciar minikube: 
    minikube start --memory=4g

Para aplicar las configuraciones al cluster k8s:
    Hay que estar parado sobre ...amigos_ms\k8s\minikube\bootstrap>
    
    Estas son las config de los pods:
        kubectl apply -f bootstrap/postgres
        kubectl apply -f bootstrap/rabbitmq
        kubectl apply -f bootstrap/zipkin
    
    Estas son las config de los services:
        kubectl apply -f services/customer
        kubectl apply -f services/fraud
        kubectl apply -f services/notification
    
Para ver los pods:
    kubectl get pods -w
    kubectl get all
    
Para obtener info del pod:
    kubectl describe pod postgres-0
    
Para ver los logs del pod:
    kubectl logs postgres-0 
    
Para acceder al pod donde esta el volumen de base de datos: 
	kubectl exec -it postgres-0 -- psql -U amigoscode

	Para listar las DB del pod:
	amigoscode=# \l	
	
	Para crear las DB dentro del pod:
	amigoscode=# create database customer;
	amigoscode=# create database fraud;
	amigoscode=# create database notification;
	amigoscode=# \l
	
Para acceder a los services por kubectl:
    kubectl get svc
	
Para acceder a los services por minikube:
    minikube service --url postgres
    minikube service --url rabbitmq
    minikube service --url zipkin

PRUEBA por KS8:
    1) Para acceder a un service de tipo LoadBalancer en este caso el el de customer:
        minikube tunnel
        Luego se le puede pegar desde un Postman 
    2) Lebantar todos los services por cmd separados:
        Postgres:
            minikube service --url postgres
                http://127.0.0.1:59891 -> el puerto es random
                                          el puerto que arroja es el que se configura en el DBeaver
        Zipkin:
            minikube service --url zipkin
        Rabbit:
            minikube service --url rabbitmq
    
    
    
Buscar un puerto activo en windows por cmd:
    netstat -ano | findstr :5432
    taskkill /PID 7844 /F
    
```




## Libs:

### Sleuth:
Herramienta de Spring Cloud para traceo de las peticiones. Agrega en los headers de comunicación información sobre el ID de seguimiento y el ID de intervalo, de manera que pueda ser utilizado luego por herramientas como Zipking o ELK.
También se integra automáticamente con los canales de comunicación comunes como las solicitudes generadas en Sl4j, Restemplate, SpringMVC, Zuul, o tecnologías de mensajería como Apache Kafka o RabbitMQ.
#### Imple: 
En cada proyecto agregar la dependencia en el pom.xml: 
```xml
<!--Configurar Sleuth-->
<!--Para traceo de las peticiones-->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-sleuth</artifactId>
</dependency>
```

## PlugIns:

### Jib:
Jib es un complemento de Maven para crear imágenes Docker y OCI para sus aplicaciones Java.
Crea y envía una imagen de contenedor para su aplicación a un registro de contenedor.
Para mas info: https://github.com/GoogleContainerTools/jib/tree/master/jib-maven-plugin
- Imple:

1. Agregar en la seccion de *pluginManagement* del parent pom.xml:

```txt
<project>
  ...
  <build>
    <plugins>
      ...
      <plugin>
        <groupId>com.google.cloud.tools</groupId>
        <artifactId>jib-maven-plugin</artifactId>
        <version>3.3.1</version>
        <configuration>
            <!--Configura la imagen base para construir su aplicación encima-->
            <from>
                <platforms>
                    ...
            </from>
            <!--Configura la imagen de destino para construir su aplicación.-->
            <to>
                <image>
                    ...
            </to>
        </configuration>
      </plugin>
      ...
    </plugins>
  </build>
  ...
</project>
```
2. Agregar el profile en los pom.xml de los proyecto que se requieran componentizar:
```xml
    <!--Creo el profile para que JIB cree la imagen del proyecto y lo suba a la cuenta de docker-hub-->
    <profiles>
        <profile>
            <id>build-docker-image</id>
            <build>
                <plugins>
                    <plugin>
                        <groupId>com.google.cloud.tools</groupId>
                        <artifactId>jib-maven-plugin</artifactId>
                    </plugin>
                </plugins>
            </build>
        </profile>
    </profiles>
```
3. Creamos para cada proyecto un *xxx_jib.cmd* con el comando maven para empaquetar el proyecto y crear la imagen con el profile definido con el plugin de maven:
```txt
mvn clean package -P build-docker-image
```




## Herramientas:

### Docker Compose:
Es una herramienta para definir y ejecutar aplicaciones Docker de varios contenedores. Con Compose, utiliza un archivo YAML para configurar los servicios de su aplicación. Luego, con un solo comando, crea e inicia todos los servicios desde su configuración.

### Docker:



### Zipking: 
Herramienta de rastreo distribuido (https://zipkin.io/) que mide la latencia de comunicación entre los componentes. Agrega la interface gráfica para visualizar la traza de las peticiones entre los microservicios.
Lo hace por medio de IDs de seguimiento implementados mediante Sleuth
#### Imple: 
1. Agregar al docker-compose.yml
```txt
  zipkin:
    image: openzipkin/zipkin
    container_name: zipkin
    ports:
      - "9411:9411"
```
```bash
# Ejecutar el contenedor con el comando: 
docker compose up -d
# Para ver los logs:
docker logs zipkin
# La herramienta se publica en:
http://127.0.0.1:9411/
# Para mas configuraciones ver:
https://github.com/openzipkin-attic/docker-zipkin/blob/master/docker-compose.yml
```
2. Para integrar los proyectos  con Zipkin 
- Complementar en cada proyecto la dependencia de *Sleuth* en el pom.xml:
```xml
<!--Para integrarlo con Zipkin-->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-sleuth-zipkin</artifactId>
</dependency>
```
- Agregar en el *application.yml* de cada proyecto:
```yaml
zipkin:
  base-url: http://localhost:9411
```

### Brokers:

#### AMQP
“Advanced Message Queuing Protocol ” es un estándar abierto para enviar mensajes entre aplicaciones y/o organizaciones. Este estándar nos permite eliminar el “vendor lock” en lo que respecta a la creación de arquitecturas distribuidas ya que podemos utilizar cualquier intermediario y cambiarlo cuando así lo queramos sin tener necesidad de cambiar el código que escribimos para enviar o recibir los mensajes.

El protocolo AMQP resuelve varios problemas al mismo tiempo: por un lado, el protocolo (en colaboración con un bróker de mensajería) se encarga de una transmisión sólida de datos. Por otro, AMQP permite almacenar mensajes en una cola. Esto, a su vez, permite una comunicación asíncrona: transmisor y receptor no deben actuar al mismo ritmo. El receptor (consumidor) del mensaje no tiene por qué aceptar, procesar la información directamente y confirmar la recepción al emisor (productor). En su lugar, recuperará el mensaje de la cola cuando tenga capacidad disponible para ello. Esto ofrece al productor, entre otras cosas, la posibilidad de seguir trabajando y se evitan los tiempos de inactividad.

En el cosmos de AMQP hay tres actores y un objeto:

- El mensaje es el elemento central de toda la comunicación.
- El productor (producer) crea un mensaje y lo envía.
- El bróker de mensajería distribuye el mensaje de acuerdo con reglas definidas en diferentes colas (queue).
- El consumidor (consumer) recupera el mensaje de la cola —a la que tiene acceso— y lo edita.

#### RabbitMQ

Es uno de los intermediarios de mensajes de código abierto más populares que trabaja bajo el estandar AMQP. 
#### Imple:
1. Agregar al docker-compose.yml
```txt
  rabbitmq:
    image: rabbitmq:3.9.11-management-alpine
    container_name: rabbitmq
    ports:
      - "5672:5672"   #to public messages
      - "15672:15672" #to expose messages
```
```bash
# Ejecutar el contenedor con el comando: 
docker compose up -d
# La herramienta se publica en:
http://localhost:15672/
# User: guest
# Pass: guest
```