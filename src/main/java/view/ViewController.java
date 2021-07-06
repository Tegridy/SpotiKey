package view;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import user.Config;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ViewController {

    public Stage stage;
    private Logger logger;
    private Config config;

    @FXML
    CheckBox controlCheckBox;

    @FXML
    CheckBox altCheckBox;

    @FXML
    CheckBox shiftCheckBox;

    @FXML
    CheckBox playPauseCheckBox;

    @FXML
    CheckBox nextSongCheckBox;

    @FXML
    CheckBox previousSongCheckBox;

    @FXML
    CheckBox volumeUpCheckBox;

    @FXML
    CheckBox volumeDownCheckBox;

    CheckBox currentlyActiveCheckBox;


    public ViewController() {
        stage = new Stage();
        logger = Logger.getLogger(getClass().getName());
        config = Config.getInstance();
        currentlyActiveCheckBox = null;

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
        System.out.println(controlCheckBox.isSelected());
    }

    @FXML
    public void setControlButtonState() {
        controlCheckBox.setSelected(true);
    }

    @FXML
    public void getAltButtonState() {
        System.out.println(altCheckBox.isSelected());
    }

    @FXML
    public void setAltEnabledCheckBoxButtonState() {
        System.out.println(altCheckBox.isSelected());
    }

    @FXML
    public void getShiftButtonState() {
        System.out.println(shiftCheckBox.isSelected());
    }

    @FXML
    public void setShiftEnabledCheckBox() {
        System.out.println(controlCheckBox.isSelected());
    }

    @FXML
    public void setCurrentlyActiveOption(Event e) {
        Object event = e.getSource();

        if (event.equals(playPauseCheckBox)) {
            currentlyActiveCheckBox = playPauseCheckBox;
        } else if (event.equals(nextSongCheckBox)) {
            currentlyActiveCheckBox = nextSongCheckBox;
        } else if (event.equals(previousSongCheckBox)) {
            currentlyActiveCheckBox = previousSongCheckBox;
        } else if (event.equals(volumeUpCheckBox)) {
            currentlyActiveCheckBox = volumeUpCheckBox;
        } else if (event.equals(volumeDownCheckBox)) {
            currentlyActiveCheckBox = volumeDownCheckBox;
        }

        logger.log(Level.INFO, e.getSource().toString() + " key is currently selected to change.");
    }

    @FXML
    public void assignNewKey(KeyEvent event) {
        int pressedKeyCode = event.getCode().getCode();

        if (currentlyActiveCheckBox != null && currentlyActiveCheckBox.isSelected()) {
            if (currentlyActiveCheckBox.equals(playPauseCheckBox)) {
                config.setPlayPauseKey(pressedKeyCode);
            } else if (currentlyActiveCheckBox.equals(nextSongCheckBox)) {
                config.setNextSongKey(pressedKeyCode);
            } else if (currentlyActiveCheckBox.equals(previousSongCheckBox)) {
                config.setPreviousSongKey(pressedKeyCode);
            } else if (currentlyActiveCheckBox.equals(volumeUpCheckBox)) {
                config.setVolumeUpKey(pressedKeyCode);
            } else if (currentlyActiveCheckBox.equals(volumeDownCheckBox)) {
                config.setVolumeDownKey(pressedKeyCode);
            }
            logger.log(Level.INFO, "Assigned: " + event.getText() + " as " + currentlyActiveCheckBox.getText() + " key.");
        }
    }
}
