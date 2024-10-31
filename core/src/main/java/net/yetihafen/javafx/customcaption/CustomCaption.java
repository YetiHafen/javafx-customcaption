package net.yetihafen.javafx.customcaption;

import javafx.scene.paint.Color;
import javafx.stage.Stage;
import net.yetihafen.javafx.customcaption.internal.NativeUtilities;
import net.yetihafen.javafx.customcaption.internal.StageManager;
import org.jetbrains.annotations.NotNull;

public class CustomCaption {

    private static final StageManager stageManager = new StageManager();

    /**
     * Applies the custom caption with specified properties
     * @param stage the Stage to apply the config
     * @param config the configuration to apply
     */
    public static void useForStage(@NotNull Stage stage, @NotNull CaptionConfiguration config) {
        stageManager.registerStage(stage, config);
    }


    /**
     * same as {@link CustomCaption#useForStage(Stage, CaptionConfiguration)}
     * but uses the default config ({@link CaptionConfiguration#DEFAULT_CONFIG})
     * @param stage the stage to apply the custom caption
     */
    public static void useForStage(@NotNull Stage stage) {
        useForStage(stage, CaptionConfiguration.DEFAULT_CONFIG);
    }


    /**
     * removes all customizations that were previously added
     * @param stage the stage to remove the customizations
     */
    public static void removeCustomization(@NotNull Stage stage) {
        stageManager.releaseStage(stage);
    }

    /**
     * Sets the Caption Color of the specified Stage to the specified Color
     * this does only work since Win 11 Build 22000
     * @param stage the Stage to change the Caption Color
     * @param color the Color to use
     * @return if the change was successful
     */
    public static boolean setCaptionColor(Stage stage, Color color) {
        return NativeUtilities.setCaptionColor(stage, color);
    }

    /**
     * Enables/disables the Immersive Dark Mode for a specified stage
     * officially only supported (documented) since Win 11 Build 22000
     * @param stage the stage to enable the Dark mode for
     * @param enabled if immersive dark mod should be enabled
     * @return if Immersive Dark Mode could be enabled successfully
     */
    public static boolean setImmersiveDarkMode(Stage stage, boolean enabled) {
        return NativeUtilities.setImmersiveDarkMode(stage, enabled);
    }

    public static boolean setBorderColor(Stage stage, Color color) {
        return NativeUtilities.setBorderColor(stage, color);
    }
}
