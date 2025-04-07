# Control de Ejecución de Proyectos - Backend

API REST empresarial desarrollada con Spring Boot para gestionar proyectos.

## 🚀 Tecnologías principales

- Java 17
- Spring Boot 3.4.4
- Spring Security + JWT
- JPA (Hibernate)
- PostgreSQL
- Maven
- Docker
- Micrometer + Prometheus + Actuator

## ▶️ Ejecutar localmente

Asegúrate de tener Java 17 instalado.

Desde la raíz del proyecto, ejecuta:

```bash
./mvnw spring-boot:run
```

Esto levantará el backend en `http://localhost:8080`.

### Endpoints de prueba:

- `GET /api/hello` – Prueba básica (puede probarse en navegador o con curl)
- `GET /actuator/health` – Verifica estado del sistema

## 🐳 Ejecutar con Docker

Si deseas probar el backend en un entorno similar al de producción, puedes construir y ejecutar la imagen Docker.

### Construcción de la imagen

```bash
mvn clean package -DskipTests
docker build -t control-ejecucion-backend .
```

### Ejecutar el contenedor

```bash
docker run -p 8080:8080 control-ejecucion-backend
```

Esto expondrá la aplicación en `http://localhost:8080`.

Puedes probar los mismos endpoints:

- `GET /api/hello`
- `GET /actuator/health`