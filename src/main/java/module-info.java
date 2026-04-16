module com.example.utez2elibreriajavafxequipo13 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    opens com.example.utez2elibreriajavafxequipo13 to javafx.fxml;
    opens com.example.utez2elibreriajavafxequipo13.controller to javafx.fxml;

    opens com.example.utez2elibreriajavafxequipo13.model to javafx.base;

    exports com.example.utez2elibreriajavafxequipo13;
}