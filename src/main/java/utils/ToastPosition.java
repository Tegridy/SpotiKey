package utils;

import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;

public class ToastPosition {

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

        double posX = screenWidth - trayNotifyWidth - 25;
        double posY = screenHeight - trayNotifyHeight;

        return new ScreenCoordinates(posX, posY);
    }


    private static WinDef.RECT getBounds() {
        User32 user32 = User32.INSTANCE;

        WinDef.HWND taskbarHWND = user32.FindWindow("Shell_TrayWnd", null);
        WinDef.HWND trayNotifyHWND = user32.FindWindowEx(taskbarHWND, null, "TrayNotifyWnd", null);

        WinDef.RECT bounds = new WinDef.RECT();
        //bounds.write();
        user32.GetWindowRect(trayNotifyHWND, bounds);
        //bounds.read();

        return bounds;
    }

    public static ScreenCoordinates getPositionOnScreen(ScreenPosition screenPosition) {
        switch (screenPosition) {
            case TASKBAR_START -> {
                return getTaskbarStartPosition();
            }
            case TASKBAR_END -> {
                return getTaskbarEndPosition();
            }
            default -> {
                return null;
            }
        }
    }
}




