package view;

import config.Config;
import config.LoadConfig;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class ToastView {

    private final Stage stage;
    private final Logger logger;

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

            stage.show();

        } catch (Throwable e) {
            logger.warn("Exception during initializing view: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
