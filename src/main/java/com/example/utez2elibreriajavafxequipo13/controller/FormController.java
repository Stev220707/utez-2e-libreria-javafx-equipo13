package com.example.utez2elibreriajavafxequipo13.controller;

import com.example.utez2elibreriajavafxequipo13.model.Libro;
import com.example.utez2elibreriajavafxequipo13.service.LibroService;
import com.example.utez2elibreriajavafxequipo13.InputValidator;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class FormController {
    // Componentes de la interfaz vinculados con Scene Builder
    @FXML private TextField id, titulo, autor, anio, genero;
    @FXML private ComboBox<String> comboGenero;
    @FXML private RadioButton rbDisponible, rbPrestado;

    // ToggleGroup sirve para que solo se pueda seleccionar un RadioButton a la vez
    private ToggleGroup group = new ToggleGroup();
    // Referencia al servicio para guardar los datos
    private LibroService service;
    // Variable para saber si estamos editando un libro que ya existe o creando uno nuevo
    private Libro libroExistente;

    @FXML
    public void initialize() {
        // Agrupamos los botones de radio para que sean mutuamente excluyentes
        rbDisponible.setToggleGroup(group);
        rbPrestado.setToggleGroup(group);
        // Ponemos "Disponible" marcado por defecto
        rbDisponible.setSelected(true);
        // Agregamos las opciones al menu desplegable de generos
        comboGenero.getItems().addAll("Novela", "Ciencia Ficcion", "Historia", "Autoayuda", "Otro");

        // Este evento se dispara cada vez que el usuario elige un genero del ComboBox
        comboGenero.setOnAction(e -> {
            // Si elige "Otro", activamos el cuadro de texto manual para escribir el genero
            boolean esOtro = "Otro".equals(comboGenero.getValue());
            genero.setVisible(esOtro); // Lo hace aparecer visualmente
            genero.setManaged(esOtro); // Hace que ocupe espacio en el diseño (layout)
        });
    }

    // Metodo para conectar el servicio desde el MainController
    public void setService(LibroService s) {
        this.service = s;
        // Si no estamos editando (es un libro nuevo), le pedimos al service que genere un ID automatico
        if (libroExistente == null) id.setText(service.generarId());
    }

    // Metodo para cargar los datos de un libro cuando presionamos "Modificar"
    public void setLibro(Libro l) {
        this.libroExistente = l;
        if (l != null) {
            // Rellenamos todos los campos con la informacion del libro seleccionado
            id.setText(l.getId());
            titulo.setText(l.getTitulo());
            autor.setText(l.getAutor());
            anio.setText(String.valueOf(l.getAnio()));

            // Si el genero del libro esta en la lista, lo selecciona en el combo
            if (comboGenero.getItems().contains(l.getGenero())) {
                comboGenero.setValue(l.getGenero());
            } else {
                // Si el genero no esta en la lista (era uno personalizado), activa el campo "Otro"
                comboGenero.setValue("Otro");
                genero.setText(l.getGenero());
                genero.setVisible(true);
                genero.setManaged(true);
            }
            // Ajusta el boton de radio segun el estado del libro
            if (l.isDisponible()) rbDisponible.setSelected(true);
            else rbPrestado.setSelected(true);
        }
    }

    @FXML
    public void guardar() {
        try {
            // Obtenemos los textos de lo que el usuario escribio
            String t = titulo.getText();
            String a = autor.getText();
            String y = anio.getText();
            String gSel = comboGenero.getValue();

            // Logica para decidir el genero: si es "Otro", toma el texto manual, si no, el del combo
            String gFinal = "Otro".equals(gSel) ? genero.getText() : (gSel != null ? gSel : "");

            // LLAMADA AL VALIDADOR: Si los datos son basura o estan vacios, aqui se lanza el error
            InputValidator.validar(t, a, gFinal, y);

            boolean disp = rbDisponible.isSelected();

            // CASO 1: Es un libro nuevo
            if (libroExistente == null) {
                service.agregar(new Libro(id.getText(), t.trim(), a.trim(), Integer.parseInt(y.trim()), gFinal.trim(), disp));
            }
            // CASO 2: Estamos editando uno existente
            else {
                libroExistente.setTitulo(t.trim());
                libroExistente.setAutor(a.trim());
                libroExistente.setAnio(Integer.parseInt(y.trim()));
                libroExistente.setGenero(gFinal.trim());
                libroExistente.setDisponible(disp);
                // Le pedimos al service que actualice el archivo de texto
                service.guardarCambios();
            }

            // Si todo salio bien, cerramos la ventana del formulario
            ((Stage) id.getScene().getWindow()).close();

        } catch (Exception e) {
            // Si el validador encontro errores, los atrapamos aqui y mostramos la alerta
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText(e.getMessage()); // Muestra el mensaje exacto del validador
            alert.showAndWait();
        }
    }
}