package com.example.utez2elibreriajavafxequipo13.controller;

import javafx.collections.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.example.utez2elibreriajavafxequipo13.model.Libro;
import com.example.utez2elibreriajavafxequipo13.service.LibroService;
import com.example.utez2elibreriajavafxequipo13.InputValidator;

public class MainController {

    // Componentes vinculados a Scene Builder mediante su fx:id
    @FXML private TableView<Libro> tabla; // La cuadrícula donde se ven los libros
    @FXML private Label lblId, lblTitulo, lblAutor, lblGenero, lblAnio, lblEstado, lblTotal; // Etiquetas de la vista previa y contador
    @FXML private TableColumn<Libro, String> colISBN, colTitulo, colAutor, colGenero, colDisponible; // Columnas de texto
    @FXML private TableColumn<Libro, Integer> colAnio; // Columna de números
    @FXML private TextField buscar; // Barra de búsqueda

    // El Service maneja la lógica de los datos (leer/escribir archivo)
    private LibroService service = new LibroService();

    // ObservableList es una lista que "avisa" a la tabla cuando algo cambia automáticamente
    private ObservableList<Libro> lista;

    /**
     * initialize() se ejecuta automáticamente cuando JavaFX termina de cargar el archivo FXML.
     * Sirve para configurar el comportamiento inicial de la tabla y los buscadores.
     */
    @FXML
    public void initialize() {
        // Le indicamos a cada columna qué "pedazo" del objeto Libro debe mostrar.
        // Se usa SimpleStringProperty porque la tabla necesita "observar" el texto.
        colISBN.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getId()));
        colTitulo.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getTitulo()));
        colAutor.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getAutor()));
        colGenero.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getGenero()));
        colAnio.setCellValueFactory(c -> new javafx.beans.property.SimpleObjectProperty<>(c.getValue().getAnio()));

        // Aquí personalizamos el texto de la columna disponible basándonos en un boolean (true/false)
        colDisponible.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(
                c.getValue().isDisponible() ? "Disponible" : "No Disponible"));

        // Listener: "Escucha" cuando el usuario hace clic en una fila de la tabla.
        tabla.getSelectionModel().selectedItemProperty().addListener((obs, old, libro) -> {
            if (libro != null) {
                // Actualiza las etiquetas laterales (Vista Previa) con la info del libro seleccionado
                lblId.setText(libro.getId());
                lblTitulo.setText(libro.getTitulo());
                lblAutor.setText(libro.getAutor());
                lblGenero.setText(libro.getGenero());
                lblAnio.setText(String.valueOf(libro.getAnio()));
                lblEstado.setText(libro.isDisponible() ? "Disponible" : "No disponible");
            }
        });

        // Buscador en tiempo real: Se activa cada vez que escribes una letra en el TextField
        buscar.textProperty().addListener((obs, old, newVal) -> {
            if (lista != null) {
                /* filtered(): Aplica un filtro a la lista original sin borrar los datos.
                   l -> ... es una función Lambda que revisa si el título o autor coinciden.
                   toLowerCase(): Ignora si el usuario escribe en mayúsculas o minúsculas. */
                tabla.setItems(lista.filtered(l ->
                        l.getTitulo().toLowerCase().contains(newVal.toLowerCase()) ||
                                l.getAutor().toLowerCase().contains(newVal.toLowerCase())));
            }
        });

        // Al arrancar, llenamos la tabla por primera vez
        cargarDatos();
    }

    /**
     * Refresca la información visual de la tabla consultando al servicio.
     */
    private void cargarDatos() {
        // Obtenemos los libros del service y los envolvemos en una lista observable
        lista = FXCollections.observableArrayList(service.getLibros());
        // Pasamos la lista a la tabla
        tabla.setItems(lista);
        // Actualizamos el contador total de libros
        if (lblTotal != null) lblTotal.setText("Total: " + lista.size());
    }

    // Botón "Añadir": Abre el formulario vacío
    @FXML public void nuevo() { abrirFormulario(null); }

    // Botón "Modificar": Obtiene el libro de la fila seleccionada y lo manda al formulario
    @FXML public void editar() {
        Libro l = tabla.getSelectionModel().getSelectedItem();
        if (l != null) abrirFormulario(l);
    }

    // Botón "Eliminar": Borra el registro tras confirmar con el usuario
    @FXML public void eliminar() {
        Libro l = tabla.getSelectionModel().getSelectedItem();
        if (l != null) {
            // Ventana de confirmación para evitar borrados accidentales
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Desea eliminar este libro?");
            // Si el usuario presiona "Aceptar" (OK)
            if (confirm.showAndWait().get() == ButtonType.OK) {
                service.eliminar(l); // Lo borra del archivo y la lista
                cargarDatos();       // Refresca la tabla
            }
        }
    }

    /**
     * Método genérico para cargar la ventana FormView.fxml (sirve para Crear y Editar).
     */
    private void abrirFormulario(Libro libro) {
        try {
            // FXMLLoader carga el diseño visual desde el archivo .fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/utez2elibreriajavafxequipo13/FormView.fxml"));
            Stage stage = new Stage(); // Crea una nueva ventana
            stage.setTitle(libro == null ? "Nuevo Libro" : "Editar Libro");
            stage.setScene(new Scene(loader.load())); // Pone el diseño dentro de la ventana

            // Obtenemos el controlador de esa ventana para pasarle datos
            FormController controller = loader.getController();
            controller.setService(service); // Le pasa el servicio de libros
            controller.setLibro(libro);     // Le pasa el libro (si es null, el form se abre vacío)

            // showAndWait(): Detiene la ventana principal hasta que cierres el formulario
            stage.showAndWait();

            // Al cerrar el formulario, refrescamos los datos por si se agregó o editó algo
            cargarDatos();
            tabla.refresh();
        } catch (Exception e) { e.printStackTrace(); } // Muestra errores en consola si falla la carga
    }

    // Botón "Generar CSV": Exporta la lista a un archivo Excel/CSV
    @FXML public void exportar() {
        service.exportar();
        new Alert(Alert.AlertType.INFORMATION, "Reporte generado").show();
    }

    // Botón "Ficha Técnica": Abre una ventana pequeña con los detalles del libro
    @FXML
    public void detalle() {
        Libro l = tabla.getSelectionModel().getSelectedItem();
        if (l != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/utez2elibreriajavafxequipo13/DetailView.fxml"));
                Stage stage = new Stage();
                stage.setTitle("Detalles del Libro");
                stage.setScene(new Scene(loader.load()));

                DetailController controller = loader.getController();
                controller.setLibro(l); // Le pasa el libro seleccionado al controlador de detalles
                stage.show(); // Esta ventana sí permite seguir usando la principal (show)
            } catch (Exception e) { e.printStackTrace(); }
        } else {
            // Si no seleccionó nada, le avisa al usuario
            new Alert(Alert.AlertType.WARNING, "Selecciona un libro primero").show();
        }
    }
}