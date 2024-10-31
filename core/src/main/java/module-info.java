module net.yetihafen.javafx.customcaption {
    requires javafx.fxml;
    requires javafx.base;
    requires javafx.controls;
    requires javafx.graphics;

    requires com.sun.jna.platform;
    requires com.sun.jna;

    requires lombok;
    requires org.jetbrains.annotations;
    requires java.desktop;

    opens net.yetihafen.javafx.customcaption.internal.structs to com.sun.jna;
    opens net.yetihafen.javafx.customcaption.internal to javafx.fxml;

    exports net.yetihafen.javafx.customcaption;
}