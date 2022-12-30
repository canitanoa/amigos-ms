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


## Herramientas:

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