package net.yetihafen.javafx.customcaption;

import javafx.scene.paint.Color;
import net.yetihafen.javafx.customcaption.internal.Util;
import org.jetbrains.annotations.NotNull;

public interface BasicCustomStage extends CustomStage {

    boolean useImmersiveDarkMode(boolean enabled);
    boolean setCaptionColor(int rgb);
    boolean setBorderColor(int rgb);


    default boolean setCaptionColor(Color color) {
        return setCaptionColor(Util.colorToRgb(color));
    }

    default boolean setCaptionColor(@NotNull java.awt.Color color) {
        return setCaptionColor(color.getRGB());
    }

    default boolean setBorderColor(Color color) {
        return setBorderColor(Util.colorToRgb(color));
    }

    default boolean setBorderColor(@NotNull java.awt.Color color) {
        return setBorderColor(color.getRGB());
    }
}
