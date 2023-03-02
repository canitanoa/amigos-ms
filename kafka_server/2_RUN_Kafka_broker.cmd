@echo off

@ECHO ******************************
@ECHO Start the Kafka broker service
@ECHO ******************************

call .\kafka\bin\windows\kafka-server-start.bat .\kafka\config\server.properties

@echo pause

