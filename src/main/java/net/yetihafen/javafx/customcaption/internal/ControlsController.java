package net.yetihafen.javafx.customcaption.internal;

import com.sun.jna.platform.win32.WinDef;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import net.yetihafen.javafx.customcaption.CaptionConfiguration;
import org.jetbrains.annotations.Nullable;

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

    private CaptionConfiguration config;

    public void applyConfig(CaptionConfiguration config) {
        this.config = config;
        for(Button button : buttons) {
            button.setStyle(
                    "-fx-text-fill: " + colorToWeb(config.getControlForegroundColor()) + ";" +
                            "-fx-background-color: " + colorToWeb(config.getControlBackgroundColor()) + ";"
            );
        }

        root.setStyle("-fx-pref-height: " + config.getCaptionHeight() + "px;"+
                "-fx-max-height: " + config.getCaptionHeight() + "px;");
    }


    public void hoverButton(@Nullable CaptionButton hoveredButton) {
        Button button = hoveredButton != null ? switch (hoveredButton) {
            case CLOSE -> closeButton;
            case MAXIMIZE_RESTORE -> maximizeRestoreButton;
            case MINIMIZE -> minimizeButton;
        } : null;

        buttons.forEach(btn -> btn.setBackground(new Background(new BackgroundFill(config.getControlBackgroundColor(), CornerRadii.EMPTY, Insets.EMPTY))));
        buttons.forEach(btn -> btn.setTextFill(config.getButtonHoverColor()));

        if(button == null) return;

        Color bgColor = hoveredButton == CaptionButton.CLOSE ? config.getCloseButtonHoverColor() : config.getButtonHoverColor();

        button.setBackground(new Background(new BackgroundFill(bgColor, CornerRadii.EMPTY, Insets.EMPTY)));
        button.setTextFill(config.getIconHoverColor());
    }

    public void onResize(WinDef.WPARAM wParam) {
        switch (wParam.intValue()) {
            case 2 /*SIZE_MAXIMIZED*/ -> maximizeRestoreButton.setText("\uE923");
            case 0 /*SIZE_RESTORED*/ -> maximizeRestoreButton.setText("\uE922");
        }
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

    public enum CaptionButton {
        CLOSE, MINIMIZE, MAXIMIZE_RESTORE
    }
}
