package view;

import config.SaveConfig;
import javafx.event.Event;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import config.Config;
import utils.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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

    private final ArrayList<HBox> listItems;


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
            //LoadConfig.loadConfigFromFile();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/scene.fxml"));

            loader.setController(this);

            stage.setScene(new Scene(loader.load()));

            logger.log(Level.INFO, "Scene loaded");

            stage.setTitle("SpotiKey");

            loadConfig();

        } catch (IOException e) {
            logger.log(Level.WARNING, e.getMessage());
        }

        this.listItems = new ArrayList<>(Arrays.asList(playPauseHBox, nextSongHBox,
                previousSongHBox, volumeUpHBox, volumeDownHBox));
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

        System.out.println(e);

        Object event = e.getSource();

        resetHBoxCssBgColorClass();

        if (event.equals(playPauseHBox)) {
            currentlyActiveCheckBox = playPauseCheckBox;
            currentKeyTextField.setText(findKeyCodeString(
                    playPauseKeyCode == 0 ? config.getPlayPauseKey() : playPauseKeyCode
            ));
            clearTextField(playPauseCheckBox);
            config.setPlayPauseKeyCombinationActivated(playPauseCheckBox.isSelected());
        } else if (event.equals(nextSongHBox)) {
            currentlyActiveCheckBox = nextSongCheckBox;
            currentKeyTextField.setText(findKeyCodeString(
                    nextSongKeyCode == 0 ? config.getNextSongKey() : nextSongKeyCode));
            clearTextField(nextSongCheckBox);
            config.setPlayPauseKeyCombinationActivated(nextSongCheckBox.isSelected());
        } else if (event.equals(previousSongHBox)) {
            currentlyActiveCheckBox = previousSongCheckBox;
            currentKeyTextField.setText(findKeyCodeString(
                    previousSongKeyCode == 0 ? config.getPreviousSongKey() : previousSongKeyCode));
            clearTextField(previousSongCheckBox);
            config.setPlayPauseKeyCombinationActivated(previousSongCheckBox.isSelected());
        } else if (event.equals(volumeUpHBox)) {
            currentlyActiveCheckBox = volumeUpCheckBox;
            currentKeyTextField.setText(findKeyCodeString(
                    volumeUpKeyCode == 0 ? config.getVolumeUpKey() : volumeUpKeyCode));
            clearTextField(volumeUpCheckBox);
            config.setPlayPauseKeyCombinationActivated(volumeUpCheckBox.isSelected());
        } else if (event.equals(volumeDownHBox)) {
            currentlyActiveCheckBox = volumeDownCheckBox;
            currentKeyTextField.setText(findKeyCodeString(
                    volumeDownKeyCode == 0 ? config.getVolumeDownKey() : volumeDownKeyCode));
            clearTextField(volumeDownCheckBox);
            config.setPlayPauseKeyCombinationActivated(volumeDownCheckBox.isSelected());
        }

        if (e.getSource() instanceof HBox) {
            ((HBox) e.getSource()).setStyle("-fx-background-color: #2b8ed9;");
        }


        logger.log(Level.INFO, e.getSource().toString() + " key is currently selected to change.");
    }

    private void resetHBoxCssBgColorClass() {
        listItems.forEach(item -> item.setStyle("-fx-background-color: transparent;"));
    }

    private String findKeyCodeString(int playPauseKeyCode) {
        return Utils.keyCodes.get(playPauseKeyCode);
    }

    @FXML
    public void assignNewKey(KeyEvent event) {
        int pressedKeyCode = event.getCode().getCode();

        System.out.println(pressedKeyCode);

        String keyType = Utils.keyCodes.get(pressedKeyCode);

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
            logger.log(Level.INFO, "Assigned: " + keyType + " as " + currentlyActiveCheckBox.getText() + " key.");
        }

        currentKeyTextField.textProperty().setValue(keyType);
    }

    private void clearTextField(CheckBox checkBox) {
        if (!checkBox.isSelected()) {
            clearCurrentKeyTextField();
        }
    }

    @FXML
    private void clearCurrentKeyTextField() {
        currentKeyTextField.clear();
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

