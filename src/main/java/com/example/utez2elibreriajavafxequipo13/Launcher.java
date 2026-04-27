package com.example.utez2elibreriajavafxequipo13;

import javafx.application.Application;

/**
 * Esta clase es el "Arrancador" externo del proyecto.
 * Se utiliza principalmente para evitar problemas de compatibilidad con el
 * Java Virtual Machine (JVM) al trabajar con JavaFX.
 */
public class Launcher {

    /**
     * Metodo main: Es el punto de entrada que busca el sistema operativo.
     */
    public static void main(String[] args) {
        /*
           Application.launch: Es un metodo estatico que inicia la plataforma JavaFX.

           HelloApplication.class: Aqui le decimos explicitamente que la clase que
           contiene todo el diseño y el metodo start() es 'HelloApplication'.

           args: Son los argumentos de linea de comandos que podrian pasarse al programa.
        */
        Application.launch(HelloApplication.class, args);
    }
}