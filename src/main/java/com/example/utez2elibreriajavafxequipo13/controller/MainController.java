package com.example.utez2elibreriajavafxequipo13.controller;

import javafx.collections.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.animation.*;
import javafx.util.Duration;

import com.example.utez2elibreriajavafxequipo13.model.Libro;
import com.example.utez2elibreriajavafxequipo13.service.LibroService;

import java.io.File;
import java.time.LocalDateTime;

public class MainController {

    @FXML private TableView<Libro> tabla;
    @FXML private Label lblId, lblTitulo, lblAutor, lblGenero, lblAnio, lblEstado;
    @FXML private TableColumn<Libro, String> colISBN, colTitulo, colAutor, colGenero, colDisponible;
    @FXML private TableColumn<Libro, Integer> colAnio;
    @FXML private TextField buscar;
    @FXML private Label lblReloj;
    @FXML private Label lblTotal;

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

        colDisponible.setCellFactory(col -> new TableCell<Libro, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(item);
                    if (item.equals("Disponible")) {
                        setStyle("-fx-text-fill: #00ff99; -fx-font-weight: bold;");
                    } else {
                        setStyle("-fx-text-fill: #ff4d4d; -fx-font-weight: bold;");
                    }
                }
            }
        });

        tabla.setStyle("-fx-control-inner-background: #2f3640; -fx-table-cell-border-color: transparent; -fx-selection-bar: #00a8ff; -fx-selection-bar-non-focused: #0097e6; -fx-font-size: 15px;");
        tabla.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        tabla.setRowFactory(tv -> {
            TableRow<Libro> row = new TableRow<>();
            row.setStyle("-fx-cell-size: 45px; -fx-font-size: 14px;");
            row.setOnMouseEntered(e -> {
                if (!row.isEmpty()) {
                    row.setStyle("-fx-background-color: #3a3f44; -fx-cell-size: 45px;");
                }
            });
            row.setOnMouseExited(e -> {
                if (!row.isEmpty()) {
                    row.setStyle("-fx-cell-size: 45px;");
                }
            });
            return row;
        });

        tabla.getSelectionModel().selectedItemProperty().addListener((obs, old, libro) -> {
            if (libro != null) {
                lblId.setText(libro.getId());
                lblTitulo.setText(libro.getTitulo());
                lblAutor.setText(libro.getAutor());
                lblGenero.setText(libro.getGenero());
                lblAnio.setText(String.valueOf(libro.getAnio()));

                if (libro.isDisponible()) {
                    lblEstado.setText("Disponible");
                    lblEstado.setStyle("-fx-text-fill: #00ff99; -fx-font-weight: bold;");
                } else {
                    lblEstado.setText("No disponible");
                    lblEstado.setStyle("-fx-text-fill: #ff4d4d; -fx-font-weight: bold;");
                }

                FadeTransition ft = new FadeTransition(Duration.millis(250));
                ft.setNode(lblTitulo.getParent().getParent());
                ft.setFromValue(0.5);
                ft.setToValue(1);
                ft.play();
            }
        });

        tabla.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 && !tabla.getSelectionModel().isEmpty()) {
                try { editar(); } catch (Exception e) { e.printStackTrace(); }
            }
        });

        buscar.textProperty().addListener((obs, old, newVal) -> {
            if (lista == null) return;
            tabla.setItems(lista.filtered(l ->
                    l.getTitulo().toLowerCase().contains(newVal.toLowerCase()) ||
                            l.getAutor().toLowerCase().contains(newVal.toLowerCase()) ||
                            l.getId().toLowerCase().contains(newVal.toLowerCase())
            ));
        });

        cargarDatos();
        iniciarReloj();
    }

    private void cargarDatos() {
        lista = FXCollections.observableArrayList(service.getLibros());
        tabla.setItems(lista);
        if (lblTotal != null) {
            lblTotal.setText("Libros: " + lista.size());
        }
        tabla.refresh();
    }

    @FXML public void nuevo() throws Exception { abrirFormulario(null); }

    @FXML public void editar() throws Exception {
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
        DetailController controller = loader.getController();
        controller.setLibro(l);
        stage.showAndWait();
    }

    private void abrirFormulario(Libro libro) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/utez2elibreriajavafxequipo13/view/FormView.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();
            stage.setTitle(libro == null ? "Nuevo Libro" : "Editar Libro");
            stage.setScene(scene);

            FormController controller = loader.getController();
            controller.setService(service);
            controller.setLibro(libro);

            stage.showAndWait();

            cargarDatos();
            tabla.refresh();

        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error al abrir formulario").show();
        }
    }

    @FXML public void exportar() {
        String ruta = service.exportar();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Exportacion");
        alert.setHeaderText("Reporte generado");
        ButtonType abrir = new ButtonType("Abrir ubicacion");
        alert.getButtonTypes().setAll(abrir, ButtonType.OK);

        alert.showAndWait().ifPresent(response -> {
            if (response == abrir) {
                try {
                    File archivo = new File(ruta);
                    if (archivo.exists()) {
                        if (archivo.getParentFile() != null) {
                            java.awt.Desktop.getDesktop().open(archivo.getParentFile());
                        } else {
                            java.awt.Desktop.getDesktop().open(new File("."));
                        }
                    } else {
                        new Alert(Alert.AlertType.ERROR, "No se encontro el archivo exportado").show();
                    }
                } catch (Exception e) { e.printStackTrace(); }
            }
        });
    }

    @FXML public void exportarComo() {
        FileChooser fc = new FileChooser();
        fc.setTitle("Guardar Reporte");
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV", "*.csv"));
        File file = fc.showSaveDialog(tabla.getScene().getWindow());
        if (file != null) {
            service.exportarComo(file.getAbsolutePath());
        }
    }

    private void iniciarReloj() {
        Timeline reloj = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            LocalDateTime now = LocalDateTime.now();
            lblReloj.setText(String.format("%02d/%02d/%d %02d:%02d:%02d",
                    now.getDayOfMonth(), now.getMonthValue(), now.getYear(),
                    now.getHour(), now.getMinute(), now.getSecond()));
        }));
        reloj.setCycleCount(Animation.INDEFINITE);
        reloj.play();
    }
}