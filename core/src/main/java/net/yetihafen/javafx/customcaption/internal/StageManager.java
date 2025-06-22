package net.yetihafen.javafx.customcaption.internal;

import javafx.stage.Stage;
import net.yetihafen.javafx.customcaption.BasicCustomStage;
import net.yetihafen.javafx.customcaption.CaptionConfiguration;
import net.yetihafen.javafx.customcaption.CustomStage;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class StageManager {

    private final HashMap<Stage, CustomStage> customizedStages = new HashMap<>();

    public void registerStage(@NotNull Stage stage, @NotNull CaptionConfiguration config) {
        if(customizedStages.containsKey(stage)) throw new IllegalArgumentException("stage was already registered");

        CustomizedStage customStage = new CustomizedStage(stage, config);

        customizedStages.put(stage, customStage);
    }

    public BasicCustomStage registerBasicStage(@NotNull Stage stage) {
        if(customizedStages.containsKey(stage)) throw new IllegalArgumentException("stage was already registered");
        BasicCustomStage customStage = new BasicCustomStageImpl(stage);
        customizedStages.put(stage, customStage);
        return customStage;
    }

    public void releaseStage(@NotNull Stage stage) {
        CustomStage customStage = customizedStages.get(stage);
        if(customStage == null) throw new IllegalArgumentException("cannot remove customization if stage was not customized");
        if(customStage instanceof CustomizedStage customizedStage) {
            customizedStage.release();
        }
        customizedStages.remove(stage);
    }
}
