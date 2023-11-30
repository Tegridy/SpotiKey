package utils;

import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;

import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.WinDef.HWND;
import hotkey.AutoHotkeyLoader;


public class ToastPosition {

    public static final int TOAST_WIDTH = 250;

    private static ScreenCoordinates getTaskbarStartPosition() {

        WinDef.RECT bounds = getBounds();

        int trayNotifyHeight = bounds.toRectangle().height;
        int screenHeight = bounds.bottom;

        double posX = 0;
        double posY = screenHeight - trayNotifyHeight;

        return new ScreenCoordinates(posX, posY);
    }

    private static ScreenCoordinates getTaskbarEndPosition() {

        WinDef.RECT bounds = getBounds();

        int trayNotifyWidth = bounds.toRectangle().width;
        int trayNotifyHeight = bounds.toRectangle().height;
        int screenWidth = bounds.left;
        int screenHeight = bounds.bottom;

        double posX = screenWidth - trayNotifyWidth - 40;
        double posY = screenHeight - trayNotifyHeight;

        return new ScreenCoordinates(posX, posY);
    }

    private static ScreenCoordinates getScreenBottomLeftPosition() {

        WinDef.RECT bounds = getBounds();

        int trayNotifyHeight = bounds.toRectangle().height;
        int screenHeight = bounds.bottom;

        double posX = 0;
        double posY = screenHeight - trayNotifyHeight * 2;

        return new ScreenCoordinates(posX, posY);
    }

    private static ScreenCoordinates getScreenBottomRightPosition() {

        WinDef.RECT bounds = getBounds();

        int trayNotifyHeight = bounds.toRectangle().height;
        int screenWidth = bounds.right;
        int screenHeight = bounds.bottom;

        double posX = screenWidth - TOAST_WIDTH;
        double posY = screenHeight - trayNotifyHeight * 2;

        return new ScreenCoordinates(posX, posY);
    }

    private static WinDef.RECT getBounds() {

        User32 user32 = User32.INSTANCE;

        WinDef.HWND taskbarHWND = user32.FindWindow("Shell_TrayWnd", null);
        WinDef.HWND trayNotifyHWND = user32.FindWindowEx(taskbarHWND, null, "TrayNotifyWnd", null);

        WinDef.RECT bounds = new WinDef.RECT();
        user32.GetWindowRect(trayNotifyHWND, bounds);

        return bounds;
    }

    public static ScreenCoordinates getPositionOnScreen(ScreenPosition screenPosition) {

        ScreenCoordinates screenCoordinates;

        switch (screenPosition) {
            case TASKBAR_START -> screenCoordinates = getTaskbarStartPosition();
            case TASKBAR_END -> screenCoordinates = getTaskbarEndPosition();
            case SCREEN_LEFT -> screenCoordinates = getScreenBottomLeftPosition();
            default -> screenCoordinates = getScreenBottomRightPosition();
        }

        return screenCoordinates;
    }
}




