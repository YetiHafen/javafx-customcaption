package net.yetihafen.javafx.customcaption.internal;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import net.yetihafen.javafx.customcaption.CaptionConfiguration;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ControlsController implements Initializable {

    @FXML
    private HBox root;
    @FXML
    private Button maximizeRestoreButton;
    @FXML
    private Button closeButton;
    @FXML
    private Button minimizeButton;

    private final List<Button> buttons = new ArrayList<>();

    public void applyConfig(CaptionConfiguration config) {
        for(Button button : buttons) {
            button.setStyle(
                    "-fx-text-fill: " + colorToWeb(config.getControlForegroundColor()) + ";" +
                            "-fx-background-color: " + colorToWeb(config.getControlBackgroundColor()) + ";"
            );
        }

        root.setStyle("-fx-pref-height: " + config.getCaptionHeight() + "px;"+
                "-fx-max-height: " + config.getCaptionHeight() + "px;");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        buttons.add(maximizeRestoreButton);
        buttons.add(closeButton);
        buttons.add(minimizeButton);
    }

    private static String colorToWeb(Color color) {
        return String.format("#%02X%02X%02X%02X",
                (int)(color.getRed() * 255),
                (int)(color.getGreen() * 255),
                (int)(color.getBlue() * 255 ),
                (int)(color.getOpacity() * 255));
    }
}
