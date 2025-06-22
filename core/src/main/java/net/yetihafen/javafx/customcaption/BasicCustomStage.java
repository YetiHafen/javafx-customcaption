package net.yetihafen.javafx.customcaption;

import javafx.scene.paint.Color;
import net.yetihafen.javafx.customcaption.internal.Util;
import org.jetbrains.annotations.NotNull;

public interface BasicCustomStage extends CustomStage {


    /**
     * Enables/disables the Immersive Dark Mode for a specified stage
     * officially only supported (documented) since Win 11 Build 22000
     * @param enabled if immersive dark mod should be enabled
     * @return if Immersive Dark Mode could be enabled successfully
     */
    boolean useImmersiveDarkMode(boolean enabled);


    /**
     * Sets the Caption Color of the specified Stage to the specified Color
     * this does only work since Win 11 Build 22000
     * @param rgb the Color to use
     * @return if the change was successful
     */
    boolean setCaptionColor(int rgb);

    /**
     * Sets the Caption Color of the specified Stage to the specified Color
     * this does only work since Win 11 Build 22000
     * @param color the Color to use
     * @return if the change was successful
     */
    default boolean setCaptionColor(Color color) {
        return setCaptionColor(Util.colorToRgb(color));
    }

    /**
     * Sets the Caption Color of the specified Stage to the specified Color
     * this does only work since Win 11 Build 22000
     * @param color the Color to use
     * @return if the change was successful
     */
    default boolean setCaptionColor(@NotNull java.awt.Color color) {
        return setCaptionColor(color.getRGB());
    }
}
