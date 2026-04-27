module com.example.utez2elibreriajavafxequipo13 {

    // 'requires': Indica que librerias externas necesita nuestro programa para funcionar.
    // Necesitamos los controles de JavaFX (botones, tablas, etc.)
    requires javafx.controls;
    // Necesitamos el motor que lee los archivos FXML (el diseño de Scene Builder).
    requires javafx.fxml;
    // 'java.desktop' permite usar funciones tipicas de computadora (como abrir ventanas de alerta).
    requires java.desktop;

    /*
       'opens': Esto es darle permiso a JavaFX para que "mire" dentro de tus carpetas.
       Sin esto, JavaFX no podria conectar los botones del FXML con el codigo de tus controladores.
    */

    // Da permiso a la carpeta principal (donde esta HelloApplication).
    opens com.example.utez2elibreriajavafxequipo13 to javafx.fxml;

    // Da permiso a la carpeta de los controladores para que los eventos de los botones funcionen.
    opens com.example.utez2elibreriajavafxequipo13.controller to javafx.fxml;

    /*
       Este es MUY importante:
       'opens ... to javafx.base' permite que la TableView pueda leer los datos de tu clase Libro.
       Sin esta linea, la tabla saldria vacia aunque el archivo libros.txt tuviera datos.
    */
    opens com.example.utez2elibreriajavafxequipo13.model to javafx.base;

    // 'exports': Permite que otras partes del sistema Java puedan ejecutar tu aplicacion.
    exports com.example.utez2elibreriajavafxequipo13;

}