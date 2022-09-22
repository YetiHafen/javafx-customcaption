package net.yetihafen.javafx.customcaption;


import javafx.scene.Scene;
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

    private boolean useControls = true;


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
    public CaptionConfiguration setIconHoverColor(Color iconHoverColor) {
        this.iconHoverColor = iconHoverColor;
        return this;
    }

    /**
     * set the background color of the close button when hovered
     * @param closeButtonHoverColor the color
     */
    public CaptionConfiguration setCloseButtonHoverColor(Color closeButtonHoverColor) {
        this.closeButtonHoverColor = closeButtonHoverColor;
        return this;
    }

    /**
     * set the background color of the buttons (except the close button)
     * @param buttonHoverColor the color
     */
    public CaptionConfiguration setButtonHoverColor(Color buttonHoverColor) {
        this.buttonHoverColor = buttonHoverColor;
        return this;
    }

    /**
     * Set the text/foreground color of the window controls
     * @param iconColor the color
     */
    public CaptionConfiguration setIconColor(Color iconColor) {
        this.iconColor = iconColor;
        return this;
    }

    /**
     * set the caption height
     * this height will apply to the window controls and
     * the draggable area of the window
     * @param captionHeight the height in px
     */
    public CaptionConfiguration setCaptionHeight(int captionHeight) {
        this.captionHeight = captionHeight;
        return this;
    }

    /**
     * set the background color of the controls
     * @param controlBackgroundColor the color
     */
    public CaptionConfiguration setControlBackgroundColor(Color controlBackgroundColor) {
        this.controlBackgroundColor = controlBackgroundColor;
        return this;
    }

    /**
     * choose if you want to add you want to use these libraries controls or use your own
     * <p>
     * Do note that {@link Scene#getRoot()} will return **not** your specified root
     * if this is set to true (default).
     * @param useControls if the library controls should be drawn
     */
    public CaptionConfiguration setUseControls(boolean useControls) {
        this.useControls = useControls;
        return this;
    }
}
