Sistema de Gestión de Biblioteca - Equipo 13
📚 Descripción del Proyecto
Este sistema fue desarrollado para la gestión integral de una biblioteca, permitiendo realizar operaciones CRUD (Crear, Leer, Actualizar y Eliminar) sobre un catálogo de libros. El enfoque principal fue la organización profesional del código, la experiencia de usuario y la persistencia de datos en archivos planos.

🏗️ Arquitectura de N-Capas
El proyecto implementa una separación de responsabilidades para facilitar su escalabilidad:

Controller: Gestiona la interacción entre la vista (FXML) y la lógica (MainController, FormController, DetailController).

Service: Contiene la lógica de negocio y las validaciones de integridad (LibroService).

Repository: Se encarga exclusivamente de la persistencia y el flujo de datos (FileRepository).

Model: Define la entidad principal del sistema (Libro).

Utilities: Incluye el validador estricto de entradas (InputValidator).

💾 Persistencia y Reportes
Archivo de texto: La información se almacena en libros.txt, garantizando que los registros se mantengan tras cerrar la aplicación.

Exportación: Se incluye una función de exportación a reporte_libros.csv (formato universal compatible con Excel).

✅ Requisitos Implementados
Control de Versiones: Uso de flujo de trabajo Git Flow con ramas de desarrollo y producción.

Validaciones Estrictas: Sistema de seguridad que bloquea campos vacíos, años fuera de rango y detección de "texto basura" (teclazos aleatorios).

Interfaz Gráfica: Diseño moderno y responsivo utilizando JavaFX, Scene Builder y CSS.

Desarrollado por: Steven Romero

Grado y Grupo: 2°E

Equipo: 13