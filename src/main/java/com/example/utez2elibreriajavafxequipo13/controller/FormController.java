package com.example.utez2elibreriajavafxequipo13.controller;

import com.example.utez2elibreriajavafxequipo13.model.Libro;
import com.example.utez2elibreriajavafxequipo13.service.LibroService;
import com.example.utez2elibreriajavafxequipo13.utils.InputValidator;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class FormController {
    @FXML private TextField id, titulo, autor, anio, genero;
    @FXML private ComboBox<String> comboGenero;
    @FXML private RadioButton rbDisponible, rbPrestado;

    private ToggleGroup group = new ToggleGroup();
    private LibroService service;
    private Libro libroExistente;

    @FXML
    public void initialize() {
        rbDisponible.setToggleGroup(group);
        rbPrestado.setToggleGroup(group);
        rbDisponible.setSelected(true);
        comboGenero.getItems().addAll("Novela", "Ciencia Ficción", "Historia", "Autoayuda", "Otro");

        comboGenero.setOnAction(e -> {
            boolean esOtro = "Otro".equals(comboGenero.getValue());
            genero.setVisible(esOtro);
            genero.setManaged(esOtro);
        });
    }

    public void setService(LibroService s) {
        this.service = s;
        if (libroExistente == null) id.setText(service.generarId());
    }

    public void setLibro(Libro l) {
        this.libroExistente = l;
        if (l != null) {
            id.setText(l.getId());
            titulo.setText(l.getTitulo());
            autor.setText(l.getAutor());
            anio.setText(String.valueOf(l.getAnio()));
            if (comboGenero.getItems().contains(l.getGenero())) {
                comboGenero.setValue(l.getGenero());
            } else {
                comboGenero.setValue("Otro");
                genero.setText(l.getGenero());
                genero.setVisible(true);
                genero.setManaged(true);
            }
            if (l.isDisponible()) rbDisponible.setSelected(true);
            else rbPrestado.setSelected(true);
        }
    }

    @FXML
    public void guardar() {
        try {
            // 1. Recolectar datos
            String t = titulo.getText();
            String a = autor.getText();
            String y = anio.getText();
            String gSel = comboGenero.getValue();
            String gFinal = "Otro".equals(gSel) ? genero.getText() : (gSel != null ? gSel : "");

            // 2. VALIDACIÓN (Si algo falla, lanza error y se va al catch)
            InputValidator.validar(t, a, gFinal, y);

            // 3. PROCESO DE GUARDADO (Solo llega aquí si la validación pasó)
            boolean disponible = rbDisponible.isSelected();

            if (libroExistente == null) {
                service.agregar(new Libro(id.getText(), t.trim(), a.trim(), Integer.parseInt(y.trim()), gFinal.trim(), disponible));
            } else {
                libroExistente.setTitulo(t.trim());
                libroExistente.setAutor(a.trim());
                libroExistente.setAnio(Integer.parseInt(y.trim()));
                libroExistente.setGenero(gFinal.trim());
                libroExistente.setDisponible(disponible);
                service.guardarCambios();
            }

            // Cerrar ventana
            ((Stage) id.getScene().getWindow()).close();

        } catch (Exception e) {
            // Este mensaje aparecerá siempre que los datos estén mal
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Datos incorrectos");
            alert.setHeaderText(null);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
}