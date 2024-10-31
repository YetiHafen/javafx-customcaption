package net.yetihafen.javafx.customcaption.internal;

import javafx.scene.paint.Color;

public class Util {

    public static int colorToRgb(Color color) {
        int rgb = 0;
        rgb |= (int) (color.getBlue() * 255);
        rgb |= ((int) (color.getGreen() * 255)) << 8;
        rgb |= ((int) (color.getRed() * 255)) << 16;
        return rgb;
    }
}
