package net.yetihafen.javafx.customcaption;

import javafx.stage.Stage;
import net.yetihafen.javafx.customcaption.internal.StageManager;
import org.jetbrains.annotations.NotNull;

public class CustomCaption {

    private static final StageManager stageManager = new StageManager();

    public static void useForStage(@NotNull Stage stage, @NotNull CaptionConfiguration config) {
        stageManager.registerStage(stage, config);
    }

    public static void useForStage(@NotNull Stage stage) {
        useForStage(stage, CaptionConfiguration.DEFAULT_CONFIG);
    }

    public static void removeCustomization(@NotNull Stage stage) {
        stageManager.releaseStage(stage);
    }
}
