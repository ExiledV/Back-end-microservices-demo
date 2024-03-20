#Ejecutar en diferentes puertos (cambiando el numero):

mvn clean install --> Esto generará un jar en la carpeta target, lo cual nos servirá para levantar el server desde la consola por diferentes puertos

java -jar target/servidor.jar --server.port=8001 --> de esta manera podemos ejecutar el servidor por comando

