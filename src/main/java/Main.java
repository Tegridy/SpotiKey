import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import keystroke.KeystrokeListener;
import view.ViewController;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Main extends Application {

    private ViewController viewController;
    private Logger logger;

    @Override
    public void start(Stage stage) {
        try {

            logger = Logger.getLogger(getClass().getName());
            Thread t1 = new Thread(new KeystrokeListener());
            t1.start();

            viewController = new ViewController();
            viewController.showStage();
        } catch (Throwable e) {
            logger.log(Level.WARNING, "Can't start app: " + e.getMessage());
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
            logger.log(Level.WARNING, "Error while closing app: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
