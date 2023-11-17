import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import keystroke.KeystrokeListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import view.Settings;
import view.Toast;

import java.io.FileNotFoundException;

public class Main extends Application {

    private Logger logger;

    @Override
    public void start(Stage stage) throws FileNotFoundException {
        try {
            logger = LoggerFactory.getLogger(Main.class);

            Thread keystrokeListenerThread = new Thread(new KeystrokeListener());
            keystrokeListenerThread.start();

            new Settings();

        } catch (Throwable ex) {
            logger.warn("Application start failure: " + ex.getMessage());
        }
    }

    @Override
    public void stop() {
        try {
            super.stop();
            Platform.exit();
            System.exit(0);
        } catch (Exception ex) {
            logger.warn("Error while closing application: " + ex.getMessage());
        }
    }
}
