package net.yetihafen.javafx.customcaption.internal;

import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.BaseTSD;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinUser;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.robot.Robot;
import javafx.stage.Stage;
import lombok.Getter;
import net.yetihafen.javafx.customcaption.CaptionConfiguration;
import net.yetihafen.javafx.customcaption.DragRegion;
import net.yetihafen.javafx.customcaption.internal.libraries.User32Ex;
import net.yetihafen.javafx.customcaption.internal.structs.NCCALCSIZE_PARAMS;
import net.yetihafen.javafx.customcaption.internal.structs.TRACKMOUSEEVENT;

import java.io.IOException;

import static com.sun.jna.platform.win32.WinUser.*;


public class CustomizedStage {

    @Getter
    private final Stage stage;
    @Getter
    private CaptionConfiguration config;
    private WinDef.HWND hWnd;
    private BaseTSD.LONG_PTR defWndProc;
    private WndProc wndProc;

    private HBox captionControls;
    private StackPane newRoot;
    private ControlsController controller;
    private boolean isRootReplaced;
    private boolean isInjected;

    private Node closeButton;
    private Node restoreButton;
    private Node minimizeButton;

    public CustomizedStage(Stage stage, CaptionConfiguration config) {
        this.stage = stage;
        this.config = config;
    }

    public void inject() {
        this.isInjected = true;

        this.hWnd = NativeUtilities.getHwnd(stage);
        this.wndProc = new WndProc();
        this.defWndProc = User32Ex.INSTANCE.SetWindowLongPtr(hWnd, WinUser.GWL_WNDPROC, wndProc);

        // trigger new WM_NCCALCSIZE message
        WinDef.RECT rect = new WinDef.RECT();
        User32Ex.INSTANCE.GetWindowRect(hWnd, rect);
        User32Ex.INSTANCE.SetWindowPos(hWnd, null, rect.left, rect.top, rect.right - rect.left, rect.bottom - rect.top, WinUser.SWP_FRAMECHANGED);


        stage.getScene().rootProperty().addListener(this::onParentChange);
        stage.sceneProperty().addListener(this::onSceneChange);
        if(config.isUseControls())
            addControlsToParent(stage.getScene().getRoot());
    }

    public void release() {
        this.isInjected = false;
        // release listeners
        stage.sceneProperty().removeListener(this::onSceneChange);
        stage.getScene().rootProperty().removeListener(this::onParentChange);

        // remove customized caption
        if(this.isRootReplaced) {
            StackPane root = (StackPane) stage.getScene().getRoot();
            Parent newParent = (Parent) root.getChildren().get(0);
            root.getChildren().clear();
            stage.getScene().setRoot(newParent);
        }

        User32Ex.INSTANCE.SetWindowLongPtr(hWnd, WinUser.GWL_WNDPROC, defWndProc);

        // trigger new WM_NCCALCSIZE message
        WinDef.RECT rect = new WinDef.RECT();
        User32Ex.INSTANCE.GetWindowRect(hWnd, rect);
        User32Ex.INSTANCE.SetWindowPos(hWnd, null, rect.left, rect.top, rect.right - rect.left, rect.bottom - rect.top, WinUser.SWP_FRAMECHANGED);
    }

    private void onParentChange(ObservableValue<? extends Parent> observable, Parent oldVal, Parent newVal) {
        if(!isInjected) return;
        if(newRoot == newVal) return;
        addControlsToParent(newVal);
    }

    private void onSceneChange(ObservableValue<? extends Scene> observable, Scene oldVal, Scene newVal) {
        if(!isInjected) return;
        oldVal.rootProperty().removeListener(this::onParentChange);
        newVal.rootProperty().addListener(this::onParentChange);
        addControlsToParent(newVal.getRoot());
    }

    private void addControlsToParent(Parent parent) {
        this.isRootReplaced = true;
        initControls();

        newRoot = new StackPane();
        newRoot.getChildren().add(parent);
        newRoot.getChildren().add(captionControls);

        newRoot.setAlignment(Pos.TOP_RIGHT);
        stage.getScene().setRoot(newRoot);
    }

    private void initControls() {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/net/yetihafen/javafx/customcaption/caption-controls.fxml"));
        try {
            captionControls = loader.load();
            controller = loader.getController();
            captionControls.getStylesheets().add(getClass().getResource("/net/yetihafen/javafx/customcaption/caption-controls.css").toExternalForm());
            controller.applyConfig(config);
            minimizeButton = controller.getMinimizeButton();
            restoreButton = controller.getMaximizeRestoreButton();
            closeButton = controller.getCloseButton();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Bounds getCloseBtnLocation() {
        return closeButton.localToScreen(closeButton.getBoundsInLocal());
    }

    private Bounds getMaximizeBtnLocation() {
        return restoreButton.localToScreen(restoreButton.getBoundsInLocal());
    }

    private Bounds getMinimizeBtnLocation() {
        return minimizeButton.localToScreen(minimizeButton.getBoundsInLocal());
    }


    class WndProc implements WinUser.WindowProc {

        private static final int WM_NCCALCSIZE = 0x0083;
        private static final int WM_NCHITTEST = 0x0084;
        private static final int WM_NCMOUSEMOVE = 0x00A0;
        private static final int WM_NCLBUTTONDOWN = 0x00A1;
        private static final int WM_MOUSELEAVE = 0x02A3;
        private static final int WM_NCMOUSELEAVE = 0x02A2;
        private static final int HTCLIENT = 1;
        private static final int HTCAPTION = 2;
        private static final int HTMAXBUTTON = 9;
        private static final int HTCLOSE = 20;
        private static final int HTMINBUTTON = 8;
        private static final int HTTOP = 12;
        private static final int SC_CLOSE = 0xF060;
        private static final int SC_RESTORE = 0xF120;
        private static final int TME_LEAVE = 0x00000002;
        private static final int TME_NONCLIENT = 0x00000010;
        private static final int HOVER_DEFAULT = 0xFFFFFFFF;

        @Override
        public WinDef.LRESULT callback(WinDef.HWND hWnd, int msg, WinDef.WPARAM wParam, WinDef.LPARAM lParam) {
            return switch (msg) {
                case WM_NCCALCSIZE -> onWmNcCalcSize(hWnd, msg, wParam, lParam);
                case WM_NCHITTEST -> onWmNcHitTest(hWnd, msg, wParam, lParam);
                case WM_NCLBUTTONDOWN -> onWmNcLButtonDown(hWnd, msg, wParam, lParam);
                case WM_NCMOUSEMOVE -> onWmNcMouseMove(hWnd, msg, wParam, lParam);
                case WM_NCMOUSELEAVE, WM_MOUSELEAVE -> {
                    if(isRootReplaced)
                        controller.hoverButton(null);
                    yield DefWndProc(hWnd, msg, wParam, lParam);
                }
                case WM_SIZE -> {
                    if(controller != null)
                        controller.onResize(wParam);
                    yield DefWndProc(hWnd, msg, wParam, lParam);
                }
                default -> DefWndProc(hWnd, msg, wParam, lParam);
            };
        }

        private WinDef.LRESULT onWmNcMouseMove(WinDef.HWND hWnd, int msg, WinDef.WPARAM wParam, WinDef.LPARAM lParam) {
            // when not using controls this is not needed
            if(!isRootReplaced) return DefWndProc(hWnd, msg, wParam, lParam);

            int position = wParam.intValue();

            if(position == HTCLOSE || position == HTMAXBUTTON || position == HTMINBUTTON) {
                TRACKMOUSEEVENT ev = new TRACKMOUSEEVENT();
                ev.cbSize = new WinDef.DWORD(ev.size());
                ev.dwFlags = new WinDef.DWORD(TME_LEAVE | TME_NONCLIENT);
                ev.hwndTrack = hWnd;
                ev.dwHoverTime = new WinDef.DWORD(HOVER_DEFAULT);
                User32Ex.INSTANCE.TrackMouseEvent(ev);
            }

            return switch(position) {
                case HTCLOSE -> {
                    controller.hoverButton(ControlsController.CaptionButton.CLOSE);
                    yield new WinDef.LRESULT(0);
                }
                case HTMAXBUTTON -> {
                    controller.hoverButton(ControlsController.CaptionButton.MAXIMIZE_RESTORE);
                    yield new WinDef.LRESULT(0);
                }
                case HTMINBUTTON -> {
                    controller.hoverButton(ControlsController.CaptionButton.MINIMIZE);
                    yield new WinDef.LRESULT(0);
                }
                default -> {
                    controller.hoverButton(null);
                    yield DefWndProc(hWnd, msg, wParam, lParam);
                }
            };
        }

        private WinDef.LRESULT onWmNcLButtonDown(WinDef.HWND hWnd, int msg, WinDef.WPARAM wParam, WinDef.LPARAM lParam) {
            int position = wParam.intValue();

            return switch (position) {
                case HTMINBUTTON -> {
                    User32Ex.INSTANCE.SendMessage(hWnd, WinUser.WM_SYSCOMMAND, new WinDef.WPARAM(SC_MINIMIZE), new WinDef.LPARAM(0));
                    yield new WinDef.LRESULT(0);
                }
                case HTMAXBUTTON -> {
                    boolean maximized = NativeUtilities.isMaximized(hWnd);
                    User32Ex.INSTANCE.SendMessage(hWnd, WinUser.WM_SYSCOMMAND, new WinDef.WPARAM(maximized ? SC_RESTORE : SC_MAXIMIZE), new WinDef.LPARAM(0));
                    yield new WinDef.LRESULT(0);
                }
                case HTCLOSE -> {
                    User32Ex.INSTANCE.SendMessage(hWnd, WinUser.WM_SYSCOMMAND, new WinDef.WPARAM(SC_CLOSE), new WinDef.LPARAM(0));
                    yield new WinDef.LRESULT(0);
                }
                default -> DefWndProc(hWnd, msg, wParam, lParam);
            };
        }

        private WinDef.LRESULT onWmNcHitTest(WinDef.HWND hWnd, int msg, WinDef.WPARAM wParam, WinDef.LPARAM lParam) {
            // if there are no controls in the client area there is no need to check
            // however this needs to be updated once custom controls are implemented
            if(!isRootReplaced) return DefWndProc(hWnd, msg, wParam, lParam);

            WinDef.RECT rect = new WinDef.RECT();
            User32Ex.INSTANCE.GetClientRect(hWnd, rect);

            int screenX = GET_X_LPARAM(lParam);
            int screenY = GET_Y_LPARAM(lParam);

            WinDef.POINT point = new WinDef.POINT(screenX, screenY);
            User32Ex.INSTANCE.ScreenToClient(hWnd, point);

            WinDef.LRESULT res = DefWndProc(hWnd, msg, wParam, lParam);
            if(res.longValue() != HTCLIENT) return res;

            if(!NativeUtilities.isMaximized(hWnd))
                if(point.y <= 3) return new WinDef.LRESULT(HTTOP);

            DragRegion captionBounds = config.getCaptionDragRegion();
            Point2D mousePosScreen = new Robot().getMousePosition();

            Bounds closeButtonBounds = getCloseBtnLocation();
            Bounds maximizeButtonBounds = getMaximizeBtnLocation();
            Bounds minimizeButtonBounds = getMinimizeBtnLocation();

            if(closeButtonBounds.contains(mousePosScreen)) {
                return new WinDef.LRESULT(HTCLOSE);
            } else if(maximizeButtonBounds.contains(mousePosScreen)) {
                return new WinDef.LRESULT(HTMAXBUTTON);
            } else if(minimizeButtonBounds.contains(mousePosScreen)) {
                return new WinDef.LRESULT(HTMINBUTTON);
            }

            if(captionBounds != null) {
                if (captionBounds.contains(mousePosScreen))
                    return new WinDef.LRESULT(HTCAPTION);
            } else {
                if(point.y < config.getCaptionHeight())
                    return new LRESULT(HTCAPTION);
            }
            return new WinDef.LRESULT(HTCLIENT);
        }

        private WinDef.LRESULT onWmNcCalcSize(WinDef.HWND hWnd, int msg, WinDef.WPARAM wParam, WinDef.LPARAM lParam) {
            if(wParam.longValue() == 0) return new WinDef.LRESULT(0);

            NCCALCSIZE_PARAMS params = new NCCALCSIZE_PARAMS(new Pointer(lParam.longValue()));
            int oldTop = params.rgrc[0].top;

            WinDef.LRESULT res = DefWndProc(hWnd, msg, wParam, lParam);
            if(res.longValue() != 0) return res;

            params.read();

            WinDef.RECT newSize = params.rgrc[0];
            newSize.top = oldTop;

            boolean maximized = NativeUtilities.isMaximized(hWnd);


            if(maximized && !stage.isFullScreen()) {
                newSize.top += NativeUtilities.getResizeHandleHeight(hWnd);
            }

            params.write();
            return new WinDef.LRESULT(0);
        }

        private WinDef.LRESULT DefWndProc(WinDef.HWND hWnd, int msg, WinDef.WPARAM wParam, WinDef.LPARAM lParam) {
            return User32Ex.INSTANCE.CallWindowProc(defWndProc, hWnd, msg, wParam, lParam);
        }

        private int HIWORD(BaseTSD.LONG_PTR lParam) {
            return (int) ((lParam.longValue() >> 16) & 0xffff);
        }
        private int LOWORD(BaseTSD.LONG_PTR lParam) {
            return (int) (lParam.longValue() & 0xffff);
        }

        private int GET_X_LPARAM(BaseTSD.LONG_PTR lParam) {
            return (short) LOWORD(lParam);
        }

        private int GET_Y_LPARAM(BaseTSD.LONG_PTR lParam) {
            return (short) HIWORD(lParam);
        }
    }
}
