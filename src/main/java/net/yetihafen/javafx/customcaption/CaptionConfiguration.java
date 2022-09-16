package net.yetihafen.javafx.customcaption;


import javafx.scene.paint.Color;
import lombok.Getter;
import lombok.Setter;

public class CaptionConfiguration {

    public static final CaptionConfiguration DEFAULT_CONFIG = new CaptionConfiguration();

    @Getter @Setter
    private int captionHeight;

    @Getter @Setter
    private Color controlForegroundColor;

    @Getter @Setter
    private Color controlBackgroundColor;


    public CaptionConfiguration() {
        this(31);
    }

    public CaptionConfiguration(int captionHeight) {
        this(captionHeight, Color.web("#A9A9A9"));
    }

    public CaptionConfiguration(int captionHeight, Color controlForegroundColor) {
        this(captionHeight, controlForegroundColor, Color.TRANSPARENT);
    }

    public CaptionConfiguration(int captionHeight, Color controlForegroundColor, Color controlBackgroundColor) {
        this.captionHeight = captionHeight;
        this.controlForegroundColor = controlForegroundColor;
        this.controlBackgroundColor = controlBackgroundColor;
    }
}
