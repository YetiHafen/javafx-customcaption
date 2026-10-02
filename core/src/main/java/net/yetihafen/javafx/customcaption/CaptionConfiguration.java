package net.yetihafen.javafx.customcaption;


import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.control.MenuBar;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import lombok.Getter;
import net.yetihafen.javafx.customcaption.internal.MenuBarDragRegion;
import net.yetihafen.javafx.customcaption.internal.ShowInitializable;

@Getter
public class CaptionConfiguration implements ShowInitializable {

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

    private DragRegion captionDragRegion;

    /**
     * Optional host pane for the library's caption controls.
     *
     * <p>When set, the library will <b>not</b> replace the scene root.
     * Instead, the caption controls are added as a child of this pane
     * and aligned to the center-right corner. This preserves the caller's
     * scene-root identity, CSS theme classes, and inline styles.
     *
     * <p>When {@code null} (default), the library falls back to the legacy
     * behavior of wrapping the scene root in a StackPane, which breaks
     * {@code scene.getRoot()} expectations and moves theme classes around.
     */
    private Pane captionHost;


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
    public CaptionConfiguration useControls(boolean useControls) {
        this.useControls = useControls;
        return this;
    }

    /**
     * Specify the {@link Node} defining the draggable area
     * @param captionDragRegion the {@link Node}
     */
    public CaptionConfiguration setCaptionDragRegion(Node captionDragRegion) {
        this.captionDragRegion = new DragRegion(captionDragRegion);
        return this;
    }

    /**
     * Specify a {@link DragRegion} to define where the window should be
     * draggable
     * @param captionDragRegion the {@link DragRegion}
     */
    public CaptionConfiguration setCaptionDragRegion(DragRegion captionDragRegion) {
        this.captionDragRegion = captionDragRegion;
        return this;
    }

    /**
     * Specify a {@link MenuBar} to define where the window should be draggable
     * while excluding the buttons in the MenuBar
     * @param menuBar the {@link MenuBar}
     */
    public CaptionConfiguration setCaptionDragRegion(MenuBar menuBar) {
        this.captionDragRegion = new MenuBarDragRegion(menuBar);
        return this;
    }

    /**
     * Sets the pane where the library's caption controls should be placed.
     *
     * <p>Use a {@link javafx.scene.layout.StackPane} that wraps your header
     * bar. The controls will be aligned to the center-right of that pane.
     * Other Pane subclasses are supported but positioning is left to the
     * caller's layout.
     *
     * @param captionHost host pane for caption controls, or {@code null}
     *                    to use the legacy scene-root wrapping behavior
     * @return this configuration for chaining
     */
    public CaptionConfiguration setCaptionHost(Pane captionHost) {
        this.captionHost = captionHost;
        return this;
    }

    @Override
    public void showInit() {
        if(captionDragRegion != null)
            captionDragRegion.showInit();
    }
}
