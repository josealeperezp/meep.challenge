# Vehicle Updater

# Despliegue de la solución
## Despliegue regular

Compilar con `mvn clean install`
Ejecutar con `java -jar`

## Despliegue con Docker
Por simplicidad no se ha creado Dockerfile sino que en su lugar se usa el plugin jib-maven-plugin:dockerBuild para compilar y construir la imagen en un paso. El comando sería:
```
./mvnw com.google.cloud.tools:jib-maven-plugin:dockerBuild -Dimage=meep/vehicle-updater
```
Luego, iniciar el contenedor con:
```
docker run -p "8080:8080" meep/vehicle-updater
```
Se decide exponer el puerto 8080 en caso de querer consultar el endpoint creado para obtener los vehículos disponibles o si se desea hacer consultas a la BD de H2 a través de la consola.
