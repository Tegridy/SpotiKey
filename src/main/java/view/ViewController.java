package view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ViewController {

    public Stage stage;
    private Logger logger;

    @FXML
    CheckBox controlEnabledCheckBox;

    @FXML
    CheckBox altEnabledCheckBox;

    @FXML
    CheckBox shiftEnabledCheckBox;


    public ViewController() {
        stage = new Stage();
        logger = Logger.getLogger(getClass().getName());

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/scene.fxml"));

            loader.setController(this);

            stage.setScene(new Scene(loader.load()));

            logger.log(Level.INFO, "Scene loaded");

            stage.setTitle("SpotiKey");

        } catch (IOException e) {
           logger.log(Level.WARNING, e.getMessage());
        }
    }

    public void showStage() {
        stage.showAndWait();
        logger.log(Level.INFO, "Showing stage");
    }

    @FXML
    public void getControlButtonState() {
        System.out.println(controlEnabledCheckBox.isSelected());
    }

    @FXML
    public void setControlButtonState() {
        controlEnabledCheckBox.setSelected(true);
    }

    @FXML
    public void getAltButtonState() {
        System.out.println(altEnabledCheckBox.isSelected());
    }

    @FXML
    public void setAltEnabledCheckBoxButtonState() {
        System.out.println(controlEnabledCheckBox.isSelected());
    }

    @FXML
    public void getShiftButtonState() {
        System.out.println(shiftEnabledCheckBox.isSelected());
    }

    @FXML
    public void setShiftEnabledCheckBox() {
        System.out.println(controlEnabledCheckBox.isSelected());
    }
}
