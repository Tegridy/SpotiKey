package view;

import com.dustinredmond.fxtrayicon.FXTrayIcon;
import config.LoadConfig;
import config.SaveConfig;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import config.Config;
import javafx.stage.StageStyle;
import utils.Utils;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ViewController {

    private Stage stage;
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

    HBox currentlyActiveHBox;

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

    private final ArrayList<HBox> hBoxes;

    private int playPauseKeyCode;
    private int nextSongKeyCode;
    private int previousSongKeyCode;
    private int volumeUpKeyCode;
    private int volumeDownKeyCode;

    public ViewController() {
        stage = new Stage();
        logger = Logger.getLogger(getClass().getName());
        config = Config.getInstance();
        currentlyActiveHBox = null;

        try {

            LoadConfig.loadConfigFromFile();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/scene.fxml"));


            loader.setController(this);

            System.out.println(getClass().getResource("/icon/s.png"));

            stage.getIcons().add(
                    new Image(Objects.requireNonNull(
                            ViewController.class.getResourceAsStream("/icon/keyboard-key-s.png"))));


            initTrayIconMenu();

            stage.initStyle(StageStyle.UTILITY);

            stage.setResizable(false);

            stage.setScene(new Scene(loader.load()));

            logger.log(Level.INFO, "Scene loaded");

            stage.setTitle("SpotiKey");

            loadConfig();

        } catch (Exception e) {
            logger.log(Level.WARNING, e.getMessage());
        }

        this.hBoxes = new ArrayList<>(Arrays.asList(playPauseHBox, nextSongHBox,
                previousSongHBox, volumeUpHBox, volumeDownHBox));
    }

    private void initTrayIconMenu() {
        FXTrayIcon icon = new FXTrayIcon(stage, getClass().getResource("/icon/keyboard-key-s.png"));

        MenuItem settingsMenuItem = new MenuItem("Settings");
        MenuItem exitAppMenuItem = new MenuItem("Exit");

        settingsMenuItem.setOnAction(event -> {
            stage.show();
        });

        exitAppMenuItem.setOnAction(event -> {
            Platform.exit();
            System.exit(0);
        });

        icon.addMenuItem(settingsMenuItem);
        icon.addMenuItem(exitAppMenuItem);

        icon.show();
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

    @FXML
    public void setCurrentlyActiveOption(Event event) {

        Object eventObject = event.getSource();

        resetHBoxCssBgColorClass();

        String keyCodeString = "";

        if (eventObject.equals(playPauseHBox)) {
            currentlyActiveHBox = playPauseHBox;
            keyCodeString = findKeyCodeString(playPauseKeyCode == 0 ? config.getPlayPauseKey() : playPauseKeyCode);
            config.setPlayPauseKeyCombinationActivated(playPauseCheckBox.isSelected());

        } else if (eventObject.equals(nextSongHBox)) {
            currentlyActiveHBox = nextSongHBox;
            keyCodeString = findKeyCodeString(nextSongKeyCode == 0 ? config.getNextSongKey() : nextSongKeyCode);
            config.setNextSongKeyCombinationActivated(nextSongCheckBox.isSelected());

        } else if (eventObject.equals(previousSongHBox)) {
            currentlyActiveHBox = previousSongHBox;
            keyCodeString = findKeyCodeString(previousSongKeyCode == 0 ? config.getPreviousSongKey() : previousSongKeyCode);
            config.setPreviousSongKeyCombinationActivated(previousSongCheckBox.isSelected());

        } else if (eventObject.equals(volumeUpHBox)) {
            currentlyActiveHBox = volumeUpHBox;
            keyCodeString = findKeyCodeString(volumeUpKeyCode == 0 ? config.getVolumeUpKey() : volumeUpKeyCode);
            config.setVolumeUpKeyCombinationActivated(volumeUpCheckBox.isSelected());

        } else if (eventObject.equals(volumeDownHBox)) {
            currentlyActiveHBox = volumeDownHBox;
            keyCodeString = findKeyCodeString(volumeDownKeyCode == 0 ? config.getVolumeDownKey() : volumeDownKeyCode);
            config.setVolumeDownKeyCombinationActivated(volumeDownCheckBox.isSelected());
        }

        currentKeyTextField.setText(keyCodeString);

        if (eventObject instanceof HBox) {
            ((HBox) eventObject).setStyle("-fx-background-color: #2b8ed9;");
        }

        logger.log(Level.INFO, eventObject + " key is currently selected to change.");
    }

    private void resetHBoxCssBgColorClass() {
        hBoxes.forEach(item -> item.setStyle("-fx-background-color: transparent;"));
    }

    private String findKeyCodeString(int playPauseKeyCode) {
        return Utils.keyCodes.get(playPauseKeyCode);
    }

    @FXML
    public void assignNewKey(KeyEvent event) {
        int pressedKeyCode = event.getCode().getCode();

        String keyType = Utils.keyCodes.get(pressedKeyCode);

        if (currentlyActiveHBox != null) {
            if (currentlyActiveHBox.equals(playPauseHBox)) {
                this.playPauseKeyCode = pressedKeyCode;
            } else if (currentlyActiveHBox.equals(nextSongHBox)) {
                this.nextSongKeyCode = pressedKeyCode;
            } else if (currentlyActiveHBox.equals(previousSongHBox)) {
                this.previousSongKeyCode = pressedKeyCode;
            } else if (currentlyActiveHBox.equals(volumeUpHBox)) {
                this.volumeUpKeyCode = pressedKeyCode;
            } else if (currentlyActiveHBox.equals(volumeDownHBox)) {
                this.volumeDownKeyCode = pressedKeyCode;
            }
            logger.log(Level.INFO, "Assigned: " + keyType + " as "
                    + currentlyActiveHBox.getId().replace("HBox", "") + " key.");
        }

        currentKeyTextField.textProperty().setValue(keyType);
    }

    @FXML
    private void saveConfig() {
        config.setControlMustBePressed(this.controlCheckBox.isSelected());
        config.setAltMustBePressed(this.altCheckBox.isSelected());
        config.setShiftMustBePressed(this.shiftCheckBox.isSelected());

        config.setPlayPauseKey(playPauseKeyCode);
        config.setPlayPauseKeyCombinationActivated(playPauseCheckBox.isSelected());
        config.setNextSongKey(nextSongKeyCode);
        config.setNextSongKeyCombinationActivated(nextSongCheckBox.isSelected());
        config.setPreviousSongKey(previousSongKeyCode);
        config.setPreviousSongKeyCombinationActivated(previousSongCheckBox.isSelected());
        config.setVolumeUpKey(volumeUpKeyCode);
        config.setVolumeUpKeyCombinationActivated(volumeUpCheckBox.isSelected());
        config.setVolumeDownKey(volumeDownKeyCode);
        config.setVolumeDownKeyCombinationActivated(volumeDownCheckBox.isSelected());

        SaveConfig.saveConfigToFile(config);
    }
}

