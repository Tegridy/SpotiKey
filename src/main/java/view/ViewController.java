package view;

import config.LoadConfig;
import config.SaveConfig;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import config.Config;
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

    @FXML
    HBox playPauseHBox;
    @FXML
    HBox nextSongHBox;
    @FXML
    HBox previousSongHBox;
    @FXML
    HBox volumeUpHBox;
    @FXML
    HBox volumeDownHBox;


    private int playPauseKeyCode;
    private int nextSongKeyCode;
    private int previousSongKeyCode;
    private int volumeUpKeyCode;
    private int volumeDownKeyCode;

    public ViewController() {
        stage = new Stage();
        logger = Logger.getLogger(getClass().getName());
        config = Config.getInstance();
        currentlyActiveCheckBox = null;

        try {
            LoadConfig.loadConfigFromFile();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/scene.fxml"));

            loader.setController(this);

            stage.setScene(new Scene(loader.load()));

            logger.log(Level.INFO, "Scene loaded");

            stage.setTitle("SpotiKey");

            loadConfig();

        } catch (IOException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
    }

    private void loadConfig() {
        this.controlCheckBox.setSelected(config.controlMustBePressed());
        this.altCheckBox.setSelected(config.altMustBePressed());
        this.shiftCheckBox.setSelected(config.shiftMustBePressed());

        this.playPauseCheckBox.setSelected(config.isPlayPauseKeyCombinationActivated());
        this.nextSongCheckBox.setSelected(config.isNextSongKeyCombinationActivated());
        this.previousSongCheckBox.setSelected(config.isPreviousSongKeyCombinationActivated());
        this.volumeUpCheckBox.setSelected(config.isVolumeUpKeyCombinationActivated());
        this.volumeDownCheckBox.setSelected(config.isVolumeDownKeyCombinationActivated());

    }

    public void showStage() {
        stage.showAndWait();
        logger.log(Level.INFO, "Showing stage");
    }


    // TODO: Refactor this method
    @FXML
    public void setCurrentlyActiveOption(Event e) {
           Object event = e.getSource();


        if (event.equals(playPauseHBox)) {
            playPauseHBox.requestFocus();
            currentlyActiveCheckBox = playPauseCheckBox;
            currentKeyTextField.setText(findKeyCodeString(config.getPlayPauseKey()));
            clearTextField(playPauseCheckBox);
            config.setPlayPauseKeyCombinationActivated(playPauseCheckBox.isSelected());
        } else if (event.equals(nextSongHBox)) {
            nextSongHBox.requestFocus();
            currentlyActiveCheckBox = nextSongCheckBox;
            currentKeyTextField.setText(findKeyCodeString(config.getNextSongKey()));
            clearTextField(nextSongCheckBox);
            config.setPlayPauseKeyCombinationActivated(nextSongCheckBox.isSelected());
        } else if (event.equals(previousSongHBox)) {
            previousSongHBox.requestFocus();
            currentlyActiveCheckBox = previousSongCheckBox;
            currentKeyTextField.setText(findKeyCodeString(config.getPreviousSongKey()));
            clearTextField(previousSongCheckBox);
            config.setPlayPauseKeyCombinationActivated(previousSongCheckBox.isSelected());
        } else if (event.equals(volumeUpHBox)) {
            volumeUpHBox.requestFocus();
            currentlyActiveCheckBox = volumeUpCheckBox;
            currentKeyTextField.setText(findKeyCodeString(config.getVolumeUpKey()));
            clearTextField(volumeUpCheckBox);
            config.setPlayPauseKeyCombinationActivated(volumeUpCheckBox.isSelected());
        } else if (event.equals(volumeDownHBox)) {
            volumeDownHBox.requestFocus();
            currentlyActiveCheckBox = volumeDownCheckBox;
            currentKeyTextField.setText(findKeyCodeString(config.getVolumeDownKey()));
            clearTextField(volumeDownCheckBox);
            config.setPlayPauseKeyCombinationActivated(volumeDownCheckBox.isSelected());
        }
//

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
                this.playPauseKeyCode = pressedKeyCode;
            } else if (currentlyActiveCheckBox.equals(nextSongCheckBox)) {
                this.nextSongKeyCode = previousSongKeyCode;
            } else if (currentlyActiveCheckBox.equals(previousSongCheckBox)) {
                this.previousSongKeyCode = pressedKeyCode;
            } else if (currentlyActiveCheckBox.equals(volumeUpCheckBox)) {
                this.volumeUpKeyCode = pressedKeyCode;
            } else if (currentlyActiveCheckBox.equals(volumeDownCheckBox)) {
                this.volumeDownKeyCode = pressedKeyCode;
            }
            logger.log(Level.INFO, "Assigned: " + event.getText() + " as " + currentlyActiveCheckBox.getText() + " key.");
        }
    }

    private void clearTextField(CheckBox checkBox) {
        if (!checkBox.isSelected()) {
            resetCurrentKeyTextField();
        }
    }

    @FXML
    private void resetCurrentKeyTextField() {
        currentKeyTextField.setText(null);
    }

    @FXML
    private void saveConfig() {
        config.setControlMustBePressed(this.controlCheckBox.isSelected());
        config.setAltMustBePressed(this.altCheckBox.isSelected());
        config.setShiftMustBePressed(this.shiftCheckBox.isSelected());

        config.setPlayPauseKey(playPauseKeyCode);
        config.setNextSongKey(nextSongKeyCode);
        config.setPreviousSongKey(previousSongKeyCode);
        config.setVolumeUpKey(volumeUpKeyCode);
        config.setVolumeDownKey(volumeDownKeyCode);

        SaveConfig.saveConfigToFile(config);
    }
}

