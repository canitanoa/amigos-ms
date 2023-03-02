@echo off

@ECHO ***************************
@ECHO Start the ZooKeeper service
@ECHO ***************************

call .\kafka\bin\windows\zookeeper-server-start.bat .\kafka\config\zookeeper.properties

@echo pause

