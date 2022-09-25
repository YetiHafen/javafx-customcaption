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
import lombok.Getter;
import net.yetihafen.javafx.customcaption.CaptionConfiguration;
import org.jetbrains.annotations.Nullable;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ControlsController implements Initializable {

    @FXML @Getter
    private HBox root;
    @FXML @Getter
    private Button maximizeRestoreButton;
    @FXML @Getter
    private Button closeButton;
    @FXML @Getter
    private Button minimizeButton;

    private final List<Button> buttons = new ArrayList<>();

    private CaptionConfiguration config;

    public void applyConfig(CaptionConfiguration config) {
        this.config = config;
        for(Button button : buttons) {
            button.setTextFill(config.getIconColor());
            button.setBackground(new Background(new BackgroundFill(config.getControlBackgroundColor(), null, null)));
        }

        root.setPrefHeight(config.getCaptionHeight());
        root.setMaxHeight(config.getCaptionHeight());
    }


    public void hoverButton(@Nullable CaptionButton hoveredButton) {
        Button button = hoveredButton != null ? switch (hoveredButton) {
            case CLOSE -> closeButton;
            case MAXIMIZE_RESTORE -> maximizeRestoreButton;
            case MINIMIZE -> minimizeButton;
        } : null;

        for(Button btn : buttons) {
            btn.setTextFill(config.getIconColor());
            btn.setBackground(new Background(new BackgroundFill(config.getControlBackgroundColor(), null, null)));
        }

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

    public enum CaptionButton {
        CLOSE, MINIMIZE, MAXIMIZE_RESTORE
    }
}
