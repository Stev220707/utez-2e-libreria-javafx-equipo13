package com.example.utez2elibreriajavafxequipo13.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import com.example.utez2elibreriajavafxequipo13.model.Libro;

public class DetailController {

    @FXML private Label datos;

    public void setLibro(Libro l) {
        if (l != null) {
            datos.setText(
                    "ID: " + l.getId() + "\n" +
                            "Título: " + l.getTitulo() + "\n" +
                            "Autor: " + l.getAutor() + "\n" +
                            "Año: " + l.getAnio() + "\n" +
                            "Género: " + l.getGenero() + "\n" +
                            "Estado: " + (l.isDisponible() ? "Disponible" : "Prestado")
            );
        }
    }

    @FXML
    public void cerrar() {
        ((Stage) datos.getScene().getWindow()).close();
    }
}