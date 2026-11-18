# Web ART

Aplicación frontend desarrollada con **Angular 22** y **Angular Material** que consume el servicio REST paginado expuesto por el backend `bff-art`.

---

## Instalación de Angular Material (`ng add @angular/material`)

Para incorporar Angular Material en un proyecto Angular, el comando oficial es:

```bash
ng add @angular/material
```

### ¿Por qué es recomendable usar `ng add` en lugar de `npm install`?

Ejecutar `ng add @angular/material` utiliza los **schematics oficiales de Angular CLI** para automatizar tanto la instalación de dependencias como la configuración inicial del proyecto. A diferencia de `npm install @angular/material`, `ng add` realiza de forma automática las siguientes tareas:

1. **Sincronización de dependencias:** Instala `@angular/material` y `@angular/cdk` asegurando que sus versiones coincidan con la versión de `@angular/core` del proyecto en `package.json`.
2. **Configuración del tema visual (Material 3):** Configura automáticamente en `src/styles.scss` (o `src/styles.css`) el tema de Angular Material mediante `@use '@angular/material' as mat;` y `@include mat.theme(...)`, además de los estilos base para `html` y `body`.
3. **Inclusión de fuentes e íconos:** Agrega en `src/index.html` los enlaces a Google Fonts para la tipografía **Roboto** y la fuente de íconos **Material Icons**, junto con la clase `mat-typography`.

### Opciones al momento de la instalación

Durante la ejecución interactiva de `ng add @angular/material`, el asistente ofrece las siguientes opciones:

- **Selección de paleta de colores (`theme`):** Permite elegir el par de paletas de color iniciales para el tema de Material 3:
  - `Azure/Blue` (`azure-blue`) — *valor por defecto*
  - `Rose/Red` (`rose-red`)
  - `Magenta/Violet` (`magenta-violet`)
  - `Cyan/Orange` (`cyan-orange`)
  - `Custom` (en versiones anteriores, para generar un esquema personalizado)
- **Proyecto destino (`--project`):** En espacios de trabajo con múltiples aplicaciones, permite indicar sobre qué proyecto aplicar la configuración.

---

## Funcionamiento del componente `GenericTable<T>`

El componente `GenericTable<T>` (`src/app/components/generic-table/`) implementa una tabla reutilizable con paginación del lado del servidor basada en `MatTableModule` y `MatPaginatorModule`.

### Diseño y estructura

1. **Tipado genérico (`<T>`):**
   El componente no está acoplado a ningún modelo de dominio específico (como `Comprobante`). Puede renderizar cualquier tipo de objeto `T` cuya estructura coincida con la definición de columnas proporcionada.

2. **Definición de columnas (`TableColumn<T>`):**
   Cada columna se define mediante la interfaz `TableColumn<T>`:
   ```ts
   export interface TableColumn<T> {
     key: Extract<keyof T, string>;
     header: string;
   }
   ```
   - `key`: Nombre de la propiedad dentro del objeto `T` (validado en tiempo de compilación por TypeScript).
   - `header`: Texto visible en el encabezado de la columna.

3. **Entradas reactivas (`Signal Inputs`):**
   - `data` (`input.required<T[]>`): Lista de elementos de la página actual a mostrar en la tabla.
   - `columns` (`input.required<TableColumn<T>[]>`): Configuración de columnas a renderizar dinámicamente.
   - `totalElements` (`input<number>`): Cantidad total de registros existentes en el servidor.
   - `pageIndex` (`input<number>`): Índice de la página actual.
   - `pageSize` (`input<number>`): Cantidad de registros por página.
   - `pageSizeOptions` (`input<number[]>`): Opciones disponibles en el selector de tamaño de página (`[5, 10, 25, 50]`).

4. **Salida de eventos (`Output`):**
   - `pageChange` (`output<PageEvent>`): Emite el evento de `MatPaginator` cada vez que el usuario avanza/retrocede de página o cambia la cantidad de filas por página. El componente contenedor escucha este evento y solicita la nueva página al backend.
