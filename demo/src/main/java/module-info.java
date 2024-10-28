module net.yetihafen.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires net.yetihafen.javafx.customcaption;

    opens net.yetihafen.demo to javafx.fxml;
    exports net.yetihafen.demo;
}