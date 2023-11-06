import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import keystroke.KeystrokeListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import view.SettingsControllerView;
import view.ToastView;

import java.io.FileNotFoundException;

public class Main extends Application {

    private SettingsControllerView viewController;
    private ToastView toastView;
    private Logger logger;

    @Override
    public void start(Stage stage) throws FileNotFoundException {
        try {
            logger = LoggerFactory.getLogger(Main.class);

            Thread t1 = new Thread(new KeystrokeListener());
            t1.start();

            viewController = new SettingsControllerView();
            toastView = new ToastView();

        } catch (Throwable e) {
            logger.warn("Can't start app: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {
        try {
            super.stop();
            Platform.exit();
            System.exit(0);
        } catch (Exception e) {
            logger.warn("Error while closing app: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
