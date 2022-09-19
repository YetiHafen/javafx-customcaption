package net.yetihafen.javafx.customcaption;


import javafx.scene.paint.Color;
import javafx.stage.Stage;
import lombok.Getter;

@Getter
public class CaptionConfiguration {

    /**
     * The default config that is used when no parameter is passed
     * to {@link CustomCaption#useForStage(Stage)}
     */
    public static final CaptionConfiguration DEFAULT_CONFIG = new CaptionConfiguration();

    private int captionHeight;

    private Color iconColor;

    private Color controlBackgroundColor;

    private Color buttonHoverColor = Color.web("#808080");
    private Color closeButtonHoverColor = Color.RED;

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

    /**
     * Set the text/foreground color of the window controls
     * when hovered
     * @param iconHoverColor the color
     */
    public void setIconHoverColor(Color iconHoverColor) {
        this.iconHoverColor = iconHoverColor;
    }

    /**
     * set the background color of the close button when hovered
     * @param closeButtonHoverColor the color
     */
    public void setCloseButtonHoverColor(Color closeButtonHoverColor) {
        this.closeButtonHoverColor = closeButtonHoverColor;
    }

    /**
     * set the background color of the buttons (except the close button)
     * @param buttonHoverColor the color
     */
    public void setButtonHoverColor(Color buttonHoverColor) {
        this.buttonHoverColor = buttonHoverColor;
    }

    /**
     * Set the text/foreground color of the window controls
     * @param iconColor the color
     */
    public void setIconColor(Color iconColor) {
        this.iconColor = iconColor;
    }

    /**
     * set the caption height
     * this height will apply to the window controls and
     * the draggable area of the window
     * @param captionHeight the height in px
     */
    public void setCaptionHeight(int captionHeight) {
        this.captionHeight = captionHeight;
    }

    /**
     * set the background color of the controls
     * @param controlBackgroundColor the color
     */
    public void setControlBackgroundColor(Color controlBackgroundColor) {
        this.controlBackgroundColor = controlBackgroundColor;
    }
}
