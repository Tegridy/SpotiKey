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
import javafx.scene.control.Hyperlink;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.Utils;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

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

    @FXML
    Hyperlink githubUrl;
    @FXML
    Hyperlink iconAuthorUrl;
    @FXML
    Hyperlink flaticonUrl;

    public ViewController() {
        stage = new Stage();
        logger = LoggerFactory.getLogger(ViewController.class);
        config = Config.getInstance();
        currentlyActiveHBox = null;

        try {
            LoadConfig.loadConfigFromFile();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/scene.fxml"));

            loader.setController(this);

            stage.getIcons().add(
                    new Image(
                            Objects.requireNonNull(getClass().getResourceAsStream("/icon/keyboard-key-s-32.png"))));

            initTrayIconMenu();


            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);

            stage.setScene(new Scene(loader.load()));
            stage.setTitle("SpotiKey");

            logger.info("Scene loaded");

            updateView();

            setUrlsOpener();

        } catch (Throwable e) {
            logger.warn("Exception during initializing view: " + e.getMessage());
            e.printStackTrace();
        }

        this.hBoxes = new ArrayList<>(Arrays.asList(playPauseHBox, nextSongHBox,
                previousSongHBox, volumeUpHBox, volumeDownHBox));
    }

    private void initTrayIconMenu() {
        FXTrayIcon icon = new FXTrayIcon(stage, getClass().getResource("/icon/keyboard-key-s-32.png"));

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

        logger.info("Tray icon initialized");
    }

    private void updateView() {
        this.controlCheckBox.setSelected(config.controlMustBePressed());
        this.altCheckBox.setSelected(config.altMustBePressed());
        this.shiftCheckBox.setSelected(config.shiftMustBePressed());

        this.playPauseCheckBox.setSelected(config.isPlayPauseKeyCombinationActivated());
        this.nextSongCheckBox.setSelected(config.isNextSongKeyCombinationActivated());
        this.previousSongCheckBox.setSelected(config.isPreviousSongKeyCombinationActivated());
        this.volumeUpCheckBox.setSelected(config.isVolumeUpKeyCombinationActivated());
        this.volumeDownCheckBox.setSelected(config.isVolumeDownKeyCombinationActivated());

        logger.info("Updated view");
    }

    private void setUrlsOpener() {
        githubUrl.setOnAction(e -> {
            openURL("https://github.com/Tegridy/SpotiKey");
        });

        iconAuthorUrl.setOnAction(e -> {
            openURL("https://www.freepik.com");
        });

        flaticonUrl.setOnAction(e -> {
            openURL("https://www.flaticon.com/");
        });
    }

    private void openURL(String url) {
        try {
            Desktop.getDesktop().browse(new URL(url).toURI());
        } catch (IOException | URISyntaxException e) {
            logger.warn("Cannot open browser: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void setCurrentlyActiveOption(Event event) {

        Object eventObject = event.getSource();

        resetHBoxesCssBgColorClass();

        String keyType = "";

        if (eventObject.equals(playPauseHBox)) {
            currentlyActiveHBox = playPauseHBox;
            keyType = findKeyCodeString(playPauseKeyCode == 0 ? config.getPlayPauseKey() : playPauseKeyCode);
            config.setPlayPauseKeyCombinationActivated(playPauseCheckBox.isSelected());

        } else if (eventObject.equals(nextSongHBox)) {
            currentlyActiveHBox = nextSongHBox;
            keyType = findKeyCodeString(nextSongKeyCode == 0 ? config.getNextSongKey() : nextSongKeyCode);
            config.setNextSongKeyCombinationActivated(nextSongCheckBox.isSelected());

        } else if (eventObject.equals(previousSongHBox)) {
            currentlyActiveHBox = previousSongHBox;
            keyType = findKeyCodeString(previousSongKeyCode == 0 ? config.getPreviousSongKey() : previousSongKeyCode);
            config.setPreviousSongKeyCombinationActivated(previousSongCheckBox.isSelected());

        } else if (eventObject.equals(volumeUpHBox)) {
            currentlyActiveHBox = volumeUpHBox;
            keyType = findKeyCodeString(volumeUpKeyCode == 0 ? config.getVolumeUpKey() : volumeUpKeyCode);
            config.setVolumeUpKeyCombinationActivated(volumeUpCheckBox.isSelected());

        } else if (eventObject.equals(volumeDownHBox)) {
            currentlyActiveHBox = volumeDownHBox;
            keyType = findKeyCodeString(volumeDownKeyCode == 0 ? config.getVolumeDownKey() : volumeDownKeyCode);
            config.setVolumeDownKeyCombinationActivated(volumeDownCheckBox.isSelected());
        }

        updateCurrentKeyTextField(keyType);

        if (eventObject instanceof HBox) {
            ((HBox) eventObject).setStyle("-fx-background-color: #2b8ed9;");
        }

        logger.info(eventObject + " key is currently selected to change.");
    }

    private void resetHBoxesCssBgColorClass() {
        hBoxes.forEach(item -> item.setStyle("-fx-background-color: transparent;"));
        logger.info("Reset HBoxes background color");
    }

    private String findKeyCodeString(int playPauseKeyCode) {
        return Utils.keyCodes.get(playPauseKeyCode);
    }

    private void updateCurrentKeyTextField(String keyType) {
        currentKeyTextField.textProperty().setValue(keyType);
        logger.info("Updated currentKeyTextField with value: " + keyType);
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
            logger.info("Assigned: " + keyType + " as "
                    + currentlyActiveHBox.getId().replace("HBox", "") + " key.");
        }

        updateCurrentKeyTextField(keyType);
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

        logger.info("Config updated");

        SaveConfig.saveConfigToFile(config);
    }
}

