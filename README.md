# Sistema de Gestión de Biblioteca - Equipo 13

## Descripción del Proyecto
Este sistema fue desarrollado con integral de una biblioteca, permitiendo realizar operaciones CRUD (Crear, Leer, Actualizar y Eliminar) sobre un catalogo de libros. El enfoque principal fue la organizacion profesional del codigo y la persistencia de datos.

## Arquitectura de N-Capas
El proyecto esta estructurado para facilitar su mantenimiento:
* **Controller:** Gestiona la interaccion entre la vista (FXML) y la logica (MainController, FormController, DetailController).
* **Service:** Contiene la logica de negocio y las validaciones de datos (LibroService).
* **Repository:** Se encarga exclusivamente de la entrada y salida de datos (FileRepository).
* **Model:** Define la entidad principal del sistema (Libro).

## Persistencia y Datos
* **Archivo de datos:** La informacion se almacena en `libros.txt`, asegurando que los datos persistan al cerrar la aplicacion.
* **Reportes:** Se incluye una funcion de exportacion a `reporte_libros.csv` para manejo de datos en hojas de calculo.

## Requisitos del Profesor Cumplidos
* **Git Flow:** Uso de ramas `main`, `dev` y personal.
* **Validaciones:** Control de campos vacios y filtros de texto en titulos.
* **Interfaz:** Diseño responsivo con JavaFX y CSS.

---
**Integrante:** Steven Romero - 2°E - Equipo 13