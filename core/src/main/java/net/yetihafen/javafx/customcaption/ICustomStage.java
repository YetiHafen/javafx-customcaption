package net.yetihafen.javafx.customcaption;

import javafx.scene.paint.Color;
import net.yetihafen.javafx.customcaption.internal.Util;

public interface ICustomStage {

    boolean useImmersiveDarkMode(boolean enabled);
    boolean setCaptionColor(int rgb);
    boolean setBorderColor(int rgb);
    void removeCustomization();


    default boolean setCaptionColor(Color color) {
        return setCaptionColor(Util.colorToRgb(color));
    }

    default boolean setCaptionColor(java.awt.Color color) {
        return setCaptionColor(color.getRGB());
    }

    default boolean setBorderColor(Color color) {
        return setBorderColor(Util.colorToRgb(color));
    }

    default boolean setBorderColor(java.awt.Color color) {
        return setBorderColor(color.getRGB());
    }
}
