package net.yetihafen.javafx.customcaption.internal;

import javafx.stage.Stage;
import net.yetihafen.javafx.customcaption.BasicCustomStage;

import java.util.function.Consumer;


public class BasicCustomStageImpl extends CustomStageBase implements BasicCustomStage {

    public BasicCustomStageImpl(final Stage stage) {
        super(stage);
    }

    public BasicCustomStageImpl(Stage stage, Consumer<?> consumer) {
        this(stage);
    }

    @Override
    public boolean useImmersiveDarkMode(boolean enabled) {
        return NativeUtilities.setImmersiveDarkMode(getHwnd(), enabled);
    }

    @Override
    public boolean setCaptionColor(int rgb) {
        return NativeUtilities.setCaptionColor(getHwnd(), rgb);
    }
}
