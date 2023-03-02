## Kafka:

Apache Kafka es una plataforma distribuida para la transmisión de datos que permite no solo publicar, almacenar y procesar flujos de eventos de forma inmediata, sino también suscribirse a ellos. Está diseñada para administrar los flujos de datos de varias fuentes y enviarlos a distintos usuarios. En pocas palabras, transfiere cantidades enormes de datos, no solo desde el punto A hasta el B, sino también del punto A al Z y a cualquier otro lugar que necesite, y todo al mismo tiempo.

### Imple:
1. Descragar Apache Kafka
```txt
URL: https://kafka.apache.org/quickstart

    PASO 1: OBTENER KAFKA
        Descargue la última versión de Kafka y extráigala:
        https://www.apache.org/dyn/closer.cgi?path=/kafka/3.3.1/kafka_2.13-3.3.1.tgz
        
        Descomprimir:
        $ tar -xzf kafka_2.13-3.3.1.tgz
        
		Renombrar la carpeta:
		kafka_2.13-3.3.1 por kafka
		
        Acceder a la carpeta:
        $ cd kafka
	
	OPCION 1:
		Ejecutar los server por .CMDs:
		
		1_RUN_Zookeeper.cmd
		2_RUN_Kafka_broker.cmd
    
	OPCION 2:
		INICIE EL ENTORNO DE KAFKA
        NOTA: Su entorno local debe tener instalado Java 8+.
        Apache Kafka se puede iniciar con ZooKeeper o KRaft. Para comenzar con cualquiera de las configuraciones, siga una de las secciones a continuación, pero no ambas.
        
        Kafka con ZooKeeper
        Ejecute los siguientes comandos para iniciar todos los servicios en el orden correcto:
        
        # Debe estar parado dentro de la carpeta donde descomprimio los archivos de kafka:
        $ cd "C:\CANITANO\Desarrollo\Brokers\kafka"

        # Start the ZooKeeper service
        $ .\bin\windows\zookeeper-server-start.bat .\config\zookeeper.properties
        
        Abra otra sesión de terminal y ejecute:
        # Start the Kafka broker service
        $ .\bin\windows\kafka-server-start.bat .\config\server.properties
		
	
        
    Una vez que todos los servicios se hayan iniciado correctamente, tendrá un entorno Kafka básico ejecutándose y listo para usar.
