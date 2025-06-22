package net.yetihafen.javafx.customcaption;


import com.sun.jna.platform.win32.WinDef;
import javafx.scene.paint.Color;
import net.yetihafen.javafx.customcaption.internal.NativeUtilities;
import net.yetihafen.javafx.customcaption.internal.Util;
import org.jetbrains.annotations.NotNull;


public interface CustomStage {
    WinDef.HWND getHwnd();


    default boolean setBorderColor(int rgb) {
        return NativeUtilities.setCaptionColor(getHwnd(), rgb);
    }

    default boolean setBorderColor(Color color) {
        return setBorderColor(Util.colorToRgb(color));
    }

    default boolean setBorderColor(@NotNull java.awt.Color color) {
        return setBorderColor(color.getRGB());
    }
}
