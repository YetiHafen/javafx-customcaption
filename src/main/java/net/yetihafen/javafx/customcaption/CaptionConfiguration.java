package net.yetihafen.javafx.customcaption;


import javafx.scene.paint.Color;
import lombok.Getter;
import lombok.Setter;

public class CaptionConfiguration {

    public static final CaptionConfiguration DEFAULT_CONFIG = new CaptionConfiguration();

    @Getter @Setter
    private int captionHeight;

    @Getter @Setter
    private Color iconColor;

    @Getter @Setter
    private Color controlBackgroundColor;

    @Getter @Setter
    private Color buttonHoverColor = Color.web("#808080");
    @Getter @Setter
    private Color closeButtonHoverColor = Color.RED;

    @Getter @Setter
    private Color iconHoverColor = Color.WHITE;


    public CaptionConfiguration() {
        this(31);
    }

    public CaptionConfiguration(int captionHeight) {
        this(captionHeight, Color.web("#A9A9A9"));
    }

    public CaptionConfiguration(int captionHeight, Color iconColor) {
        this(captionHeight, iconColor, Color.TRANSPARENT);
    }

    public CaptionConfiguration(int captionHeight, Color iconColor, Color controlBackgroundColor) {
        this.captionHeight = captionHeight;
        this.iconColor = iconColor;
        this.controlBackgroundColor = controlBackgroundColor;
    }
}
