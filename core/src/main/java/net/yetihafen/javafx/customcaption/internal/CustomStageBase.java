package net.yetihafen.javafx.customcaption.internal;

import com.sun.jna.platform.win32.WinDef;
import javafx.beans.Observable;
import javafx.stage.Stage;
import lombok.Getter;
import net.yetihafen.javafx.customcaption.CustomStage;

public abstract class CustomStageBase implements CustomStage {

    @Getter
    private final Stage stage;
    private WinDef.HWND hWnd = null;

    public CustomStageBase(Stage stage) {
        this.stage = stage;
        stage.showingProperty().addListener(this::onShowUpdate);
        updateHwnd();
    }

    private void onShowUpdate(Observable observable, boolean oldVal, boolean newVal) {
        updateHwnd();
    }

    private void updateHwnd() {
        WinDef.HWND updatedHwnd = NativeUtilities.getHwnd(stage);

        if(updatedHwnd != null)
            this.hWnd = updatedHwnd;
    }

    @Override
    public WinDef.HWND getHwnd() {
        return hWnd;
    }

}
