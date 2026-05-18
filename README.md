# eCommerce

Backend REST para compras, carrito y checkout.

El proyecto principal esta en `factorit-backend/`.

## Requisitos

- Java 21
- No hace falta instalar Gradle. El backend usa Gradle Wrapper

## Backend

Desde la raiz del proyecto:

```bash
./gradlew bootRun
```

En Windows:

```powershell
.\gradlew.bat bootRun
```

La API queda disponible en:

```text
http://localhost:8080
```

## Base de datos

Se usa H2 en memoria. Cada vez que se reinicia la aplicacion, la base se recrea.

La consola de H2 queda en:

```text
http://localhost:8080/h2-console
```

Datos de conexion:

```text
JDBC URL: jdbc:h2:mem:testdb
User: sa
Password:
```

La app carga algunos datos iniciales desde `src/main/resources/data.sql`.

## Swagger

Con el backend levantado:

```text
http://localhost:8080/swagger-ui.html
```

OpenAPI JSON:

```text
http://localhost:8080/v3/api-docs
```

## Tests

Desde la raiz del proyecto:

```bash
./gradlew test
```

En Windows:

```powershell
.\gradlew.bat test
```

Los tests usan H2 y levantan contexto de Spring. Esto permite validar JPA, cascades, queries, checkout, descuentos y respuestas de error.

## Postman

La coleccion esta en:

```text
postman/factorit.postman_collection.json
```

Incluye endpoints para carritos, compras, checkout y errores.

Payloads sueltos:

```text
postman/bodies/
```
