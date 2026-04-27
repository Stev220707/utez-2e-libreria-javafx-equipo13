package com.example.utez2elibreriajavafxequipo13;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.net.URL;

/**
 * Esta clase es el punto de entrada de la aplicacion.
 * Hereda de 'Application', que es la clase base de JavaFX para crear ventanas.
 */
public class HelloApplication extends Application {

    /**
     * El metodo start es el primer metodo que JavaFX ejecuta al abrirse.
     * @param stage Representa la "Ventana" principal del programa (el marco).
     */
    @Override
    public void start(Stage stage) throws Exception {

        /* Buscando el archivo visual (.fxml):
           getClass().getResource(...) intenta localizar el archivo MainView.fxml dentro de
           la carpeta de recursos (resources). Es como darle la direccion de la casa a Java.
        */

        // Intento 1: Busca usando el formato de puntos (comun en algunos proyectos de IntelliJ).
        URL fxmlLocation = getClass().getResource("/com.example.utez2elibreriajavafxequipo13/MainView.fxml");

        // Intento 2: Si no lo encuentra, busca usando el formato de carpetas con diagonales.
        if (fxmlLocation == null) {
            fxmlLocation = getClass().getResource("/com/example/utez2elibreriajavafxequipo13/MainView.fxml");
        }

        // Si despues de los dos intentos no encuentra nada, detiene el programa con un error.
        if (fxmlLocation == null) {
            throw new Exception("Error critico: No se encontro MainView.fxml. Revisa la carpeta resources.");
        }

        /* FXMLLoader: Es el objeto que "infla" o construye la vista.
           Toma el archivo XML (diseño) y lo convierte en objetos de Java.
        */
        FXMLLoader fxmlLoader = new FXMLLoader(fxmlLocation);

        /* Scene (Escena): Es el contenido dentro de la ventana.
           Aqui definimos el tamaño inicial de la pantalla (1000 pixeles de ancho por 700 de alto).
        */
        Scene scene = new Scene(fxmlLoader.load(), 1000, 700);

        // Configuracion de la ventana principal:
        stage.setTitle("Sistema De Gestion De Biblioteca - Equipo 13"); // Pone el nombre en la barra superior.
        stage.setScene(scene); // Le pone la escena (el contenido) a la ventana.
        stage.show(); // Hace que la ventana sea visible para el usuario.
    }

    /**
     * El metodo main es el estandar en Java para iniciar cualquier programa.
     */
    public static void main(String[] args) {
        // launch(args) es una funcion interna de JavaFX que prepara todo y llama al metodo start().
        launch(args);
    }
}