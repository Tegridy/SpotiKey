import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import keystroke.KeystrokeListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import view.Settings;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class Main extends Application {

    private Logger logger;

    @Override
    public void start(Stage stage) {
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
