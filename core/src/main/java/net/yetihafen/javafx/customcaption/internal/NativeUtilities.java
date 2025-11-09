package net.yetihafen.javafx.customcaption.internal;

import com.sun.jna.platform.win32.*;
import com.sun.jna.ptr.IntByReference;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import net.yetihafen.javafx.customcaption.internal.libraries.DwmApi;
import net.yetihafen.javafx.customcaption.internal.libraries.User32Ex;
import net.yetihafen.javafx.customcaption.internal.structs.DWMWINDOWATTRIBUTE;

import java.util.UUID;

import static com.sun.jna.platform.win32.WinUser.SM_CXPADDEDBORDER;
import static com.sun.jna.platform.win32.WinUser.SM_CYSIZEFRAME;

public class NativeUtilities {

    /**
     * *should* return the HWND for the Specified Stage
     * might not, because JavaFX ist stupid and has no way
     * to do this
     * @param stage the Stage
     * @return hopefully the HWND for the correct stage
     */
    public static WinDef.HWND getHwnd(Stage stage) {
        String randomId = UUID.randomUUID().toString();
        String title = stage.getTitle();
        stage.setTitle(randomId);
        WinDef.HWND hWnd = User32.INSTANCE.FindWindow(null, randomId);
        stage.setTitle(title);
        return hWnd;
    }


    /**
     * Enables/disables the Immersive Dark Mode for a specified stage
     * officially only supported (documented) since Win 11 Build 22000
     * @param hWnd the Window handle to enable the Dark mode for
     * @param enabled if immersive dark mod should be enabled
     * @return if Immersive Dark Mode could be enabled successfully
     */
    public static boolean setImmersiveDarkMode(WinDef.HWND hWnd, boolean enabled) {
        WinNT.HRESULT res = DwmApi.INSTANCE.DwmSetWindowAttribute(hWnd, DWMWINDOWATTRIBUTE.DWMWA_USE_IMMERSIVE_DARK_MODE, new IntByReference(enabled ? 1 : 0), 4);
        return res.longValue() >= 0;
    }

    /**
     * Sets the Caption Color of the specified Stage to the specified Color
     * this does only work since Win 11 Build 22000
     * @param hWnd the window handle to change the Caption Color
     * @param color the Color to use
     * @return if the change was successful
     */
    public static boolean setCaptionColor(WinDef.HWND hWnd, Color color) {
        int red = (int) (color.getRed() * 255);
        int green = (int) (color.getGreen() * 255);
        int blue = (int) (color.getBlue() * 255);

        return setCaptionColor(hWnd, red << 16 | green << 8 | blue);
    }

    public static boolean setCaptionColor(WinDef.HWND hWnd, int rgb) {
        int red = (rgb & 0xFF0000) >> 16;
        int green = (rgb & 0x00FF00) >> 8;
        int blue = rgb & 0x0000FF;
        // win api accepts the colors in reverse order
        int bgr = red + (green << 8) + (blue << 16);
        WinNT.HRESULT res = DwmApi.INSTANCE.DwmSetWindowAttribute(hWnd, DWMWINDOWATTRIBUTE.DWMWA_CAPTION_COLOR, new IntByReference(bgr), 4);
        return res.longValue() >= 0;
    }

    /**
     * sets the caption to the specified color if supported
     * if not supported uses immersive dark mode if color is mostly dark
     * @param hWnd the window handle to modify
     * @param color the color to set the caption
     * @return if the stage was modified
     */
    public static boolean customizeCation(WinDef.HWND hWnd, Color color) {
        boolean success = setCaptionColor(hWnd, color);
        if(!success) {
            int red = (int) (color.getRed() * 255);
            int green = (int) (color.getGreen() * 255);
            int blue = (int) (color.getBlue() * 255);
            int colorSum = red + green + blue;

            boolean dark = colorSum < 255 * 3 / 2;
            success = setImmersiveDarkMode(hWnd, dark);
        }
        return success;
    }

    public static boolean setBorderColor(Stage stage, Color color) {
        WinDef.HWND hWnd = getHwnd(stage);
        int red = (int) (color.getRed() * 255);
        int green = (int) (color.getGreen() * 255);
        int blue = (int) (color.getBlue() * 255);
        // win api accepts the colors in reverse order
        int rgb = red + (green << 8) + (blue << 16);
        WinNT.HRESULT res = DwmApi.INSTANCE.DwmSetWindowAttribute(hWnd, DWMWINDOWATTRIBUTE.DWMWA_BORDER_COLOR, new IntByReference(rgb), 4);
        return res.longValue() >= 0;
    }


    public static boolean isMaximized(WinDef.HWND hWnd) {
        BaseTSD.LONG_PTR windowStyle = User32Ex.INSTANCE.GetWindowLongPtr(hWnd, WinUser.GWL_STYLE);
        return (windowStyle.longValue() & WinUser.WS_MAXIMIZE) == WinUser.WS_MAXIMIZE;
    }

    public static int getResizeHandleHeight(WinDef.HWND hWnd) {
        int dpi = User32Ex.INSTANCE.GetDpiForWindow(hWnd);
        return User32Ex.INSTANCE.GetSystemMetricsForDpi(SM_CXPADDEDBORDER, dpi) +
                User32Ex.INSTANCE.GetSystemMetricsForDpi(SM_CYSIZEFRAME, dpi);
    }
}
