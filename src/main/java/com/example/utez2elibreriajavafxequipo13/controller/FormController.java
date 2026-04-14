package com.example.utez2elibreriajavafxequipo13.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import com.example.utez2elibreriajavafxequipo13.model.Libro;
import com.example.utez2elibreriajavafxequipo13.service.LibroService;

public class FormController {

    @FXML private TextField id, titulo, autor, anio, genero;
    @FXML private ComboBox<String> comboGenero;
    @FXML private RadioButton rbDisponible, rbPrestado;

    private ToggleGroup grupoEstado;
    private LibroService service;
    private Libro libro;

    @FXML
    public void initialize() {
        grupoEstado = new ToggleGroup();
        rbDisponible.setToggleGroup(grupoEstado);
        rbPrestado.setToggleGroup(grupoEstado);

        comboGenero.getItems().addAll(
                "Novela Historica", "Ciencia Ficcion", "Cyberpunk", "Distopia",
                "Fantasia Epica", "Terror Psicologico", "Policial / Noir",
                "Realismo Magico", "Biografia", "Ensayo Filosofico",
                "Poesía", "Infantil", "Autoayuda", "Historia Universal",
                "Divulgación", "Otro"
        );

        comboGenero.setOnAction(e -> {
            boolean esOtro = "Otro".equals(comboGenero.getValue());
            genero.setVisible(esOtro);
            genero.setManaged(esOtro);
            if (!esOtro) limpiarError(genero);
        });

        id.setEditable(false);

        titulo.setTextFormatter(new TextFormatter<>(change -> {
            if (change.getControlNewText().matches("[a-zA-Z0-9áéíóúÁÉÍÓÚñÑ ]*")) return change;
            return null;
        }));

        autor.setTextFormatter(new TextFormatter<>(change -> {
            if (change.getControlNewText().matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]*")) return change;
            return null;
        }));

        genero.setTextFormatter(new TextFormatter<>(change -> {
            if (change.getControlNewText().matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]*")) return change;
            return null;
        }));

        anio.setTextFormatter(new TextFormatter<>(change -> {
            String nuevo = change.getControlNewText();
            if (!nuevo.matches("\\d*")) return null;
            if (nuevo.length() > 4) return null;
            return change;
        }));

        titulo.textProperty().addListener((obs, old, val) -> limpiarError(titulo));
        autor.textProperty().addListener((obs, old, val) -> limpiarError(autor));
        anio.textProperty().addListener((obs, old, val) -> limpiarError(anio));
        genero.textProperty().addListener((obs, old, val) -> limpiarError(genero));
    }

    public void setService(LibroService service) {
        this.service = service;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
        if (service == null) return;

        if (libro == null) {
            id.setText(service.generarId());
        } else {
            id.setText(libro.getId());
            titulo.setText(libro.getTitulo());
            autor.setText(libro.getAutor());
            anio.setText(String.valueOf(libro.getAnio()));
            comboGenero.setValue(libro.getGenero());

            if (libro.isDisponible()) {
                rbDisponible.setSelected(true);
            } else {
                rbPrestado.setSelected(true);
            }
        }
    }

    private boolean esTextoValido(String texto) {
        if (texto == null || texto.trim().length() < 2) return false;

        String[] palabras = texto.trim().toLowerCase().split("\\s+");

        for (String palabra : palabras) {
            if (palabra.isEmpty()) continue;

            if (palabra.matches("\\d+")) continue;

            if (palabra.matches(".*(.)\\1{2,}.*")) return false;

            int vocalesPalabra = 0;
            int consSeguidas = 0;
            int maxCons = 0;

            for (int i = 0; i < palabra.length(); i++) {
                char c = palabra.charAt(i);
                if ("aeiouáéíóúy".indexOf(c) != -1) {
                    vocalesPalabra++;
                    consSeguidas = 0;
                } else if (Character.isLetter(c)) {
                    consSeguidas++;
                    if (consSeguidas > maxCons) {
                        maxCons = consSeguidas;
                    }
                }
            }

            if (maxCons >= 4) return false;

            if (palabra.length() > 1 && vocalesPalabra == 0) return false;

            if (palabra.length() >= 6 && vocalesPalabra < 2) return false;
        }

        return true;
    }

    private void marcarError(TextField campo) {
        campo.setStyle("-fx-border-color: #ff4d4d; -fx-border-width: 2px; -fx-border-radius: 3px; -fx-background-color: #fff0f0;");
        campo.requestFocus();
    }

    private void limpiarError(TextField campo) {
        campo.setStyle("");
    }

    @FXML
    public void guardar() {
        try {
            String tit = titulo.getText().trim();
            String aut = autor.getText().trim();
            String gen = comboGenero.getValue();

            if (!esTextoValido(tit)) {
                marcarError(titulo);
                showError("Titulo invalido. Por favor, ingrese un titulo valido.");
                return;
            }

            if (!esTextoValido(aut)) {
                marcarError(autor);
                showError("Autor invalido. Escriba un nombre real.");
                return;
            }

            if (anio.getText().length() != 4) {
                marcarError(anio);
                showError("El año debe tener exactamente 4 digitos.");
                return;
            }

            int year = Integer.parseInt(anio.getText());
            if (year < 1450 || year > java.time.Year.now().getValue()) {
                marcarError(anio);
                showError("Año fuera del rango permitido (1450 - Actualidad).");
                return;
            }

            if ("Otro".equals(gen)) {
                gen = genero.getText().trim();
                if (!esTextoValido(gen)) {
                    marcarError(genero);
                    showError("Genero personalizado invalido. Escriba correctamente.");
                    return;
                }
            } else if (gen == null || gen.isEmpty()) {
                showError("Seleccione un genero valido de la lista.");
                comboGenero.requestFocus();
                return;
            }

            if (grupoEstado.getSelectedToggle() == null) {
                showError("Por favor, seleccione si el libro esta Disponible o Prestado.");
                return;
            }

            boolean disponible = rbDisponible.isSelected();

            if (libro == null) {
                service.agregar(new Libro(id.getText(), tit, aut, year, gen, disponible));
            } else {
                libro.setTitulo(tit);
                libro.setAutor(aut);
                libro.setGenero(gen);
                libro.setAnio(year);
                libro.setDisponible(disponible);
                service.guardarCambios();
            }

            cerrar();

        } catch (NumberFormatException e) {
            marcarError(anio);
            showError("Asegurese de ingresar numeros validos en el año.");
        } catch (Exception e) {
            showError("Aviso del sistema: " + e.getMessage());
        }
    }

    @FXML public void cancelar() { cerrar(); }

    private void cerrar() {
        Stage stage = (Stage) id.getScene().getWindow();
        stage.close();
    }

    private void showError(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Validacion de Entrada");
        alert.setContentText(msg);
        alert.showAndWait();
    }
}