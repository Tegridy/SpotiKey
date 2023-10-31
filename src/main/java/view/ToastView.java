package view;

import com.sun.jna.platform.win32.WinDef;
import config.Config;
import config.LoadConfig;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import com.sun.jna.platform.win32.User32;

import javax.swing.text.Style;

public class ToastView {

    private final Stage stage;
    private final Logger logger;

    public static final int TOAST_WIDTH = 300;
    public static final int TOAST_HEIGHT = 80;

    public ToastView() {
        stage = new Stage();
        logger = LoggerFactory.getLogger(ToastView.class);

        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/toast.fxml"));
            loader.setController(this);

//             stage.initModality(Modality.APPLICATION_MODAL);
//            stage.setResizable(false);

            stage.setScene(new Scene(loader.load()));

            logger.info("Toast scene loaded");

            stage.setAlwaysOnTop(true);
            stage.initStyle(StageStyle.UNDECORATED);

            stage.show();

            findPlaceForToast();

        } catch (Throwable e) {
            logger.warn("Exception during initializing view: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void findPlaceForToast() {
        User32 user32 = User32.INSTANCE;

        System.out.println(user32);

        WinDef.HWND taskbarHWND = user32.FindWindow("Shell_TrayWnd", null);

        WinDef.HWND trayNotifyHWND = user32.FindWindowEx(taskbarHWND, null, "TrayNotifyWnd", null);
        System.out.println(trayNotifyHWND);
        WinDef.RECT bounds = new WinDef.RECT();
        bounds.write();

        System.out.println(user32.GetWindowRect(trayNotifyHWND, bounds));
        bounds.read();
        int trayNotifyWidth = bounds.toRectangle().width;
        int trayNotifyHeight = bounds.toRectangle().height;
        int screenWidth = bounds.left;
        int screenHeight = bounds.bottom;

        double posX = screenWidth - trayNotifyWidth;
        double posY = screenHeight - trayNotifyHeight;

        stage.setX(posX);
        stage.setY(posY);
    }
}
