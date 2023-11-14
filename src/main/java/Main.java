import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import keystroke.KeystrokeListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import player.PlayerController;
import view.SettingsController;
import view.ToastController;

import java.io.FileNotFoundException;

public class Main extends Application {

    private Logger logger;

    @Override
    public void start(Stage stage) throws FileNotFoundException {
        try {
            logger = LoggerFactory.getLogger(Main.class);

            Thread t1 = new Thread(new KeystrokeListener());
            t1.start();

            SettingsController viewController = new SettingsController();
            ToastController toastController = new ToastController();
            PlayerController.getInstance().addIsPlayingListener(toastController);

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
        } catch (Exception ex) {
            logger.warn("Error while closing app: " + ex.getMessage());
        }
    }
}
