package net.yetihafen.javafx.customcaption.internal;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.stage.Stage;
import net.yetihafen.javafx.customcaption.CaptionConfiguration;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class StageManager {

    private final HashMap<Stage, CustomizedStage> customizedStages = new HashMap<>();

    public void registerStage(@NotNull Stage stage, @NotNull CaptionConfiguration config) {
        if(customizedStages.containsKey(stage)) throw new IllegalArgumentException("stage was already registered");

        CustomizedStage customStage = new CustomizedStage(stage, config);

        if(!stage.isShowing()) {

            stage.showingProperty().addListener(new ChangeListener<>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                    customStage.showInit();
                    stage.showingProperty().removeListener(this);
                }
            });

        } else {
            customStage.inject();
        }
        customizedStages.put(stage, customStage);
    }

    public void releaseStage(@NotNull Stage stage) {
        CustomizedStage customizedStage = customizedStages.get(stage);
        if(customizedStage == null) throw new IllegalArgumentException("cannot remove customization if stage was not customized");
        customizedStage.release();
        customizedStages.remove(stage);
    }
}
