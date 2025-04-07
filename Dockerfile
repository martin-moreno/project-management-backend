# Usa una imagen base de Java
FROM eclipse-temurin:21-jdk-alpine

# Crea un directorio dentro del contenedor para la app
WORKDIR /app

# Copia el archivo jar generado por Maven
COPY target/project-management-0.0.1-SNAPSHOT.jar app.jar

# Exponer el puerto de tu app (ajusta si usas otro)
EXPOSE 8081

# Comando para ejecutar la app
ENTRYPOINT ["java", "-jar", "app.jar"]