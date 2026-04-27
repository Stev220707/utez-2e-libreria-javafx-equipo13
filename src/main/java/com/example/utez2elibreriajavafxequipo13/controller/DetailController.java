package com.example.utez2elibreriajavafxequipo13.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import com.example.utez2elibreriajavafxequipo13.model.Libro;

/**
 * Esta clase controla la ventana de "Detalles" o "Ficha Tecnica".
 * Su unica funcion es recibir un objeto Libro y mostrar su informacion.
 */
public class DetailController {

    // @FXML conecta este objeto de Java con el Label que dibujaste en Scene Builder.
    // 'datos' es el nombre (fx:id) del Label grande donde se escribe la info.
    @FXML private Label datos;

    /**
     * Este metodo recibe el libro que seleccionaste en la tabla principal.
     * Sirve como un "puente" de datos entre la ventana Main y esta ventana.
     */
    public void setLibro(Libro l) {
        // Verifica que el libro no llegue vacio para evitar errores de programa.
        if (l != null) {
            /* String.format: Es una plantilla. Los %s y %d son espacios vacios
               que se llenan con los datos que ponemos despues de la coma.
               \n: Significa "Salto de linea", para que cada dato salga uno abajo del otro.
            */
            datos.setText(String.format(
                    "ID: %s\nTITULO: %s\nAUTOR: %s\nAÑO: %d\nGENERO: %s\nESTADO: %s",
                    l.getId(),           // Reemplaza el primer %s (ID)
                    l.getTitulo(),       // Reemplaza el segundo %s (TITULO)
                    l.getAutor(),        // Reemplaza el tercer %s (AUTOR)
                    l.getAnio(),         // Reemplaza el %d (AÑO - numero entero)
                    l.getGenero(),       // Reemplaza el cuarto %s (GENERO)
                    // Operador ternario: si es true escribe "Disponible", si no "No Disponible".
                    l.isDisponible() ? "Disponible" : "No Disponible"
            ));
        }
    }

    /**
     * Metodo vinculado al boton "Cerrar" de la ventana de detalles.
     */
    @FXML
    public void cerrar() {
        /*
           1. datos.getScene(): Obtiene la escena donde esta el label.
           2. .getWindow(): Obtiene la ventana (Stage) que contiene esa escena.
           3. (Stage): Se convierte el objeto a tipo ventana para poder usar sus funciones.
           4. .close(): Cierra la ventana actual de golpe.
        */
        ((Stage) datos.getScene().getWindow()).close();
    }
}