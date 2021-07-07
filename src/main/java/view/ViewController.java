package view;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import user.Config;
import utils.Utils;

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

    @FXML
    TextField currentKeyTextField;

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

    private void loadConfig() {
        this.controlCheckBox.setSelected(config.controlMustBePressed());
        this.altCheckBox.setSelected(config.altMustBePressed());
        this.shiftCheckBox.setSelected(config.shiftMustBePressed());
    }

    public void showStage() {
        stage.showAndWait();
        logger.log(Level.INFO, "Showing stage");
    }

    // TODO: Refactor this method
    @FXML
    public void setCurrentlyActiveOption(Event e) {
        Object event = e.getSource();

        if (event.equals(playPauseCheckBox)) {
            currentlyActiveCheckBox = playPauseCheckBox;
            currentKeyTextField.setText(findKeyCodeString(config.getPlayPauseKey()));
            markCheckBoxAndClearTextField(playPauseCheckBox);
            config.setPlayPauseKeyCombinationActivated(playPauseCheckBox.isSelected());
        } else if (event.equals(nextSongCheckBox)) {
            currentlyActiveCheckBox = nextSongCheckBox;
            currentKeyTextField.setText(findKeyCodeString(config.getNextSongKey()));
            markCheckBoxAndClearTextField(nextSongCheckBox);
            config.setPlayPauseKeyCombinationActivated(nextSongCheckBox.isSelected());
        } else if (event.equals(previousSongCheckBox)) {
            currentlyActiveCheckBox = previousSongCheckBox;
            currentKeyTextField.setText(findKeyCodeString(config.getPreviousSongKey()));
            markCheckBoxAndClearTextField(previousSongCheckBox);
            config.setPlayPauseKeyCombinationActivated(previousSongCheckBox.isSelected());
        } else if (event.equals(volumeUpCheckBox)) {
            currentlyActiveCheckBox = volumeUpCheckBox;
            currentKeyTextField.setText(findKeyCodeString(config.getVolumeUpKey()));
            markCheckBoxAndClearTextField(volumeUpCheckBox);
            config.setPlayPauseKeyCombinationActivated(volumeUpCheckBox.isSelected());
        } else if (event.equals(volumeDownCheckBox)) {
            currentlyActiveCheckBox = volumeDownCheckBox;
            currentKeyTextField.setText(findKeyCodeString(config.getVolumeDownKey()));
            markCheckBoxAndClearTextField(volumeDownCheckBox);
            config.setPlayPauseKeyCombinationActivated(volumeDownCheckBox.isSelected());
        }



        logger.log(Level.INFO, e.getSource().toString() + " key is currently selected to change.");
    }

    private String findKeyCodeString(int playPauseKeyCode) {
        return Utils.keyCodes.get(playPauseKeyCode);
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

    private void markCheckBoxAndClearTextField(CheckBox checkBox) {
        if (checkBox.isSelected()) {
            checkBox.setStyle("-fx-background-color: #2b8ed9;");
        } else {
            checkBox.setStyle("-fx-background-color: white;");
            resetCurrentKeyTextField();
        }
    }

    @FXML
    private void resetCurrentKeyTextField() {
        currentKeyTextField.setText(null);
    }
}
