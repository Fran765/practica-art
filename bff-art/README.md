# ART Backend for Frontend (BFF)

Servicio Backend for Frontend (BFF) desarrollado con **Spring Boot 4.1.2** y **Java 21**. Consume el webservice externo `materiales-api`, almacena en caché en memoria la lista de entregas por comprobante utilizando **Caffeine** y expone un endpoint REST paginado listo para ser consumido desde **Angular**.

---

## Requisitos previos

Para compilar y ejecutar este proyecto de forma local, es necesario contar con:

1. **Java Development Kit (JDK) 21**
    - Verificar la versión instalada:
      ```bash
      java -version
      ```
    - Asegurarse de que la variable de entorno `JAVA_HOME` apunte al directorio de instalación de JDK 21.
2. **Maven** *(Opcional)*
    - No es necesario instalar Maven de forma global, ya que el proyecto incluye **Maven Wrapper** (`mvnw` para Linux/macOS y `mvnw.cmd` para Windows), el cual descarga y utiliza automáticamente la versión requerida.
3. **Conexión a Internet**
    - Necesaria para descargar las dependencias de Maven y conectarse al webservice externo configurado localmente.

---

## Cómo levantar el proyecto en local

### 1. Ubicarse en la raíz del proyecto
```bash
cd bff-art
```

### 2. Compilar el proyecto
- **Windows (PowerShell / CMD):**
  ```powershell
  .\mvnw.cmd clean compile
  ```
- **Linux / macOS:**
  ```bash
  ./mvnw clean compile
  ```

### 3. Iniciar la aplicación
- **Windows (PowerShell / CMD):**
  ```powershell
  .\mvnw.cmd spring-boot:run
  ```
- **Linux / macOS:**
  ```bash
  ./mvnw spring-boot:run
  ```

Por defecto, el servidor se levanta en **`http://localhost:8080`**.

---

## Uso de la API y Documentación

### Swagger UI
Con la aplicación en ejecución, se puede acceder a la documentación interactiva OpenAPI en:
- `http://localhost:8080/swagger-ui.html`

### Endpoint paginado
```http
GET http://localhost:8080/api/entregas-bienes/comprobante/{idComprobante}?page=0&size=10
```

#### Parámetros de consulta (Query Params)
| Parámetro | Tipo | Valor por defecto | Descripción                      |
| :--- | :--- |:------------------|:---------------------------------|
| `page` | `int` | `0`               | Número de página                 |
| `size` | `int` | `10`              | Cantidad de registros por página |

#### Ejemplo de respuesta
```json
{
  "content": [
    {
      //Datos del comprobante
    }
  ],
  "page": 0,
  "size": 10,
  "totalElements": 18837,
  "totalPages": 1884
}
```

---

## Configuración

Los parámetros principales se encuentran en `src/main/resources/application.yaml`:

- **CORS:** Origen permitido por defecto `http://localhost:4200` (`app.cors.allowed-origins`).
- **Webservice externo:** Configurado bajo `app.materiales-api` (`connect-timeout: 10s`, `read-timeout: 60s`). La URL del webservice (`app.materiales-api.url`) no se almacena en el repositorio por razones de seguridad; debe configurarse localmente mediante el archivo `bff-art/src/main/resources/application-local.yaml` (ignorado por Git y cargado automáticamente por Spring Boot) o a través de la variable de entorno `MATERIALES_API_URL`.
- **Caché en memoria (Caffeine):** Configurada bajo `app.cache.comprobantes` (`ttl: 10m`, `maximum-size: 20`).
