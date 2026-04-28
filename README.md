# Sistema de Gestión de Biblioteca - Equipo 13

## Descripción del Proyecto
Este sistema fue desarrollado para la gestión de una biblioteca, permitiendo realizar operaciones CRUD (Crear, Leer, Actualizar y Eliminar) sobre un catálogo de libros.

El proyecto se enfoca en la organización del código, la validación de datos y la persistencia de la información mediante archivos.

---

## Arquitectura
El sistema está estructurado en una arquitectura por capas para mejorar la organización y mantenimiento:

- Controller: Maneja la interacción entre la interfaz gráfica (FXML) y la lógica del sistema.
    - Clases: MainController, FormController, DetailController

- Service: Contiene la lógica de negocio y las validaciones.
    - Clase: LibroService

- Repository: Gestiona la persistencia de los datos.
    - Clase: FileRepository

- Model: Representa la entidad principal del sistema.
    - Clase: Libro

- Utilities: Incluye herramientas auxiliares para validación.
    - Clase: InputValidator

---

## Persistencia de Datos
La información se almacena en un archivo de texto llamado `libros.txt`, lo que permite conservar los datos incluso después de cerrar la aplicación.

---

## Exportación
El sistema incluye una función para exportar los datos a un archivo `reporte_libros.csv`, compatible con herramientas como Excel.

---

## Funcionalidades
- Registro de libros
- Edición de información
- Eliminación de registros
- Validación de datos de entrada
- Exportación de información

---

## Tecnologías utilizadas
- Java
- JavaFX
- Scene Builder
- CSS

---

## Control de versiones
Se utilizó Git para el control de versiones, trabajando con múltiples ramas para desarrollo y pruebas.

---

## Información del proyecto
Desarrollado por: Steven Romero  
Grado y Grupo: 2°E  
Equipo: 13