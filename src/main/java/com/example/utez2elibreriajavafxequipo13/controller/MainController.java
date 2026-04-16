package com.example.utez2elibreriajavafxequipo13.controller;

import javafx.collections.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.example.utez2elibreriajavafxequipo13.model.Libro;
import com.example.utez2elibreriajavafxequipo13.service.LibroService;

public class MainController {

    @FXML private TableView<Libro> tabla;
    @FXML private Label lblId, lblTitulo, lblAutor, lblGenero, lblAnio, lblEstado, lblTotal;
    @FXML private TableColumn<Libro, String> colISBN, colTitulo, colAutor, colGenero, colDisponible;
    @FXML private TableColumn<Libro, Integer> colAnio;
    @FXML private TextField buscar;

    private LibroService service = new LibroService();
    private ObservableList<Libro> lista;

    @FXML
    public void initialize() {
        colISBN.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getId()));
        colTitulo.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getTitulo()));
        colAutor.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getAutor()));
        colGenero.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getGenero()));
        colAnio.setCellValueFactory(c -> new javafx.beans.property.SimpleObjectProperty<>(c.getValue().getAnio()));
        colDisponible.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(
                c.getValue().isDisponible() ? "Disponible" : "No Disponible"));

        tabla.getSelectionModel().selectedItemProperty().addListener((obs, old, libro) -> {
            if (libro != null) {
                lblId.setText(libro.getId());
                lblTitulo.setText(libro.getTitulo());
                lblAutor.setText(libro.getAutor());
                lblGenero.setText(libro.getGenero());
                lblAnio.setText(String.valueOf(libro.getAnio()));
                lblEstado.setText(libro.isDisponible() ? "Disponible" : "No disponible");
            }
        });

        buscar.textProperty().addListener((obs, old, newVal) -> {
            if (lista != null) {
                tabla.setItems(lista.filtered(l ->
                        l.getTitulo().toLowerCase().contains(newVal.toLowerCase()) ||
                                l.getAutor().toLowerCase().contains(newVal.toLowerCase())));
            }
        });

        cargarDatos();
    }

    private void cargarDatos() {
        lista = FXCollections.observableArrayList(service.getLibros());
        tabla.setItems(lista);
        if (lblTotal != null) lblTotal.setText("Libros: " + lista.size());
    }

    @FXML public void nuevo() { abrirFormulario(null); }

    @FXML public void editar() {
        Libro l = tabla.getSelectionModel().getSelectedItem();
        if (l != null) abrirFormulario(l);
    }

    @FXML public void eliminar() {
        Libro l = tabla.getSelectionModel().getSelectedItem();
        if (l != null) {
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "¿Eliminar libro?");
            if (confirm.showAndWait().get() == ButtonType.OK) {
                service.eliminar(l);
                cargarDatos();
            }
        }
    }

    @FXML public void detalle() throws Exception {
        Libro l = tabla.getSelectionModel().getSelectedItem();
        if (l == null) return;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/utez2elibreriajavafxequipo13/view/DetailView.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(loader.load()));
        ((DetailController)loader.getController()).setLibro(l);
        stage.showAndWait();
    }

    private void abrirFormulario(Libro libro) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/utez2elibreriajavafxequipo13/view/FormView.fxml"));
            Stage stage = new Stage();
            stage.setTitle(libro == null ? "Nuevo" : "Editar");
            stage.setScene(new Scene(loader.load()));
            FormController controller = loader.getController();
            controller.setService(service);
            controller.setLibro(libro);
            stage.showAndWait();
            cargarDatos();
        } catch (Exception e) { e.printStackTrace(); }
    }

    @FXML public void exportar() {
        service.exportar();
        new Alert(Alert.AlertType.INFORMATION, "Reporte 'reporte_libros.csv' generado con éxito.").show();
    }
}