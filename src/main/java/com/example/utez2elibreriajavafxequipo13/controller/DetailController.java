package com.example.utez2elibreriajavafxequipo13.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import com.example.utez2elibreriajavafxequipo13.model.Libro;

public class DetailController {

    @FXML private Label datos;

    public void setLibro(Libro l) {
        if (l != null) {
            datos.setText(String.format(
                    "ID: %s\nTITULO: %s\nAUTOR: %s\nAÑO: %d\nGENERO: %s\nESTADO: %s",
                    l.getId(), l.getTitulo(), l.getAutor(), l.getAnio(), l.getGenero(),
                    l.isDisponible() ? "Disponible" : "No Disponible"
            ));
        }
    }

    @FXML
    public void cerrar() {
        ((Stage) datos.getScene().getWindow()).close();
    }
}