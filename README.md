# Movimientos de Bienes ART

Repositorio monorepo que integra el **Backend for Frontend (`bff-art`)** y la **Aplicación Web (`web-art`)** para la consulta paginada de comprobantes y movimientos de entrega de bienes.

---

## Arquitectura del Sistema

```text
[ web-art (Angular 22 + Material) :4200 ]
                   │
                   │  GET /api/entregas-bienes/comprobante/{id}?page=0&size=10
                   ▼
[ bff-art (Spring Boot 4 + Caffeine Cache) :8080 ]
                   │
                   │  POST (Webservice externo)
                   ▼
[ API Externa de Materiales ]
```

---

## Estructura del Repositorio

| Proyecto | Tecnología | Puerto | Descripción | Documentación |
| :--- | :--- | :--- | :--- | :--- |
| **`bff-art`** | Java 21, Spring Boot 4.1.2, Caffeine | `8080` | Consume el servicio externo de materiales, almacena resultados en caché y expone un endpoint REST paginado. | [Ver README de `bff-art`](./bff-art/README.md) |
| **`web-art`** | Angular 22, Angular Material | `4200` | Interfaz web con componente de tabla y paginador genérico (`GenericTable<T>`) que consume el BFF. | [Ver README de `web-art`](./web-art/README.md) |

---

## Inicio Rápido

### 1. Configurar y levantar el Backend (`bff-art`)

1. Crear el archivo `bff-art/src/main/resources/application-local.yaml` (ignorado por Git) o definir la variable de entorno `MATERIALES_API_URL` con la URL del servicio externo.
2. Ejecutar el servidor:
   ```bash
   cd bff-art
   ./mvnw spring-boot:run
   ```

### 2. Levantar el Frontend (`web-art`)

En una nueva terminal:

```bash
cd web-art
npm install
npm start
```

La aplicación estará disponible en `http://localhost:4200`.
