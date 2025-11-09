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
     * Used to create a {@code BasicCustomStage}
     * @param stage the stage to customize
     * @return the {@code BasicCustomStage} you can use to customize the stage
     */
    public static BasicCustomStage useBasic(Stage stage) {
        return stageManager.registerBasicStage(stage);
    }

    /**
     * Sets the Border Color of the specified Stage to the specified Color
     * this does only work since Win 11 Build 22000
     * @param stage the stage to change the Border Color
     * @param color the Color to use
     * @return if the change was successful
     * @deprecated Native Window Handle is not cached, use {@link BasicCustomStage#setBorderColor(Color)} instead
     */
    @Deprecated
    public static boolean setBorderColor(Stage stage, Color color) {
        return NativeUtilities.setBorderColor(stage, color);
    }
}
