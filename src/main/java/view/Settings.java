package view;

import com.dustinredmond.fxtrayicon.FXTrayIcon;
import config.LoadConfig;
import config.SaveConfig;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.ScreenPosition;
import utils.Utils;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class Settings extends SettingsControls {

    private final Stage stage;
    private final Logger logger;
    private final Config config;

    private Toast toast;

    private HBox currentlyActiveHBox;

    private final ArrayList<HBox> hBoxes;

    private final ToggleGroup taskbarPositionGroup;

    private ChangeListener<? super Toggle> toggleListener;
    private int playPauseKeyCode;
    private int nextSongKeyCode;
    private int previousSongKeyCode;
    private int volumeUpKeyCode;
    private int volumeDownKeyCode;

    @FXML
    private Hyperlink githubUrl;
    @FXML
    private Hyperlink iconAuthorUrl;
    @FXML
    private Hyperlink flaticonUrl;

    public Settings() {

        stage = new Stage();
        logger = LoggerFactory.getLogger(Settings.class);
        config = Config.getInstance();
        currentlyActiveHBox = null;
        taskbarPositionGroup = new ToggleGroup();

        try {
            LoadConfig.loadConfigFromFile();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/settings.fxml"));
            loader.setController(this);

            stage.getIcons().add(
                    new Image(
                            Objects.requireNonNull(getClass().getResourceAsStream("/icon/keyboard-key-s-32.png"))));

            stage.initModality(Modality.NONE);
            stage.setResizable(false);
            stage.setScene(new Scene(loader.load()));
            stage.setTitle("SpotiKey");

            initTrayIconMenu();
            updateView();
            setUrlsOpener();

            taskbarStartRadio.setToggleGroup(taskbarPositionGroup);
            taskbarEndRadio.setToggleGroup(taskbarPositionGroup);
            bottomLeftRadio.setToggleGroup(taskbarPositionGroup);
            bottomRightRadio.setToggleGroup(taskbarPositionGroup);

        } catch (Exception ex) {
            logger.warn("Exception during initializing view: " + ex.getMessage());
        }

        hBoxes = new ArrayList<>(Arrays.asList(playPauseHBox, nextSongHBox,
                previousSongHBox, volumeUpHBox, volumeDownHBox));

        toastEnableCheckBox.setSelected(true);
        toastEnableCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> {

            if (newValue) {
                taskbarPositionGroup.getToggles().forEach(toggle -> ((RadioButton) toggle).setDisable(false));
            } else {
                taskbarPositionGroup.getToggles().forEach(toggle -> ((RadioButton) toggle).setDisable(true));
            }
        });

        taskbarStartRadio.setSelected(true);
        enableToast();
    }

    private void initTrayIconMenu() {

        FXTrayIcon icon = new FXTrayIcon(stage, getClass().getResource("/icon/keyboard-key-s-32.png"));

        MenuItem settingsMenuItem = new MenuItem("Settings");
        MenuItem toastActivation = new MenuItem("Show / Hide Toast");
        MenuItem exitAppMenuItem = new MenuItem("Exit");

        toastActivation.setOnAction(event -> {
            toastEnableCheckBox.setSelected(!toastEnableCheckBox.isSelected());
            enableToast();
        });

        settingsMenuItem.setOnAction(event -> {
            stage.show();
        });

        exitAppMenuItem.setOnAction(event -> {
            Platform.exit();
            System.exit(0);
        });

        icon.addMenuItem(settingsMenuItem);
        icon.addMenuItem(toastActivation);
        icon.addMenuItem(exitAppMenuItem);

        icon.show();
    }

    public void updateView() {

        Platform.runLater(() -> {

            controlCheckBox.setSelected(config.controlMustBePressed());
            altCheckBox.setSelected(config.altMustBePressed());
            shiftCheckBox.setSelected(config.shiftMustBePressed());

            playPauseCheckBox.setSelected(config.isPlayPauseKeyCombinationActivated());
            nextSongCheckBox.setSelected(config.isNextSongKeyCombinationActivated());
            previousSongCheckBox.setSelected(config.isPreviousSongKeyCombinationActivated());
            volumeUpCheckBox.setSelected(config.isVolumeUpKeyCombinationActivated());
            volumeDownCheckBox.setSelected(config.isVolumeDownKeyCombinationActivated());

            toastEnableCheckBox.setSelected(config.isToastEnabled());

            if (toastEnableCheckBox.isSelected()) {

                switch (ScreenPosition.valueOf(config.getToastScreenPosition())) {
                    case SCREEN_LEFT -> bottomLeftRadio.setSelected(true);
                    case SCREEN_RIGHT -> bottomRightRadio.setSelected(true);
                    case TASKBAR_END -> taskbarEndRadio.setSelected(true);
                    default -> taskbarStartRadio.setSelected(true);
                }
            }
            logger.debug("Settings view updated.");
        });
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
        } catch (IOException | URISyntaxException ex) {
            logger.warn("Can't open browser: " + ex.getMessage());
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

        logger.debug(eventObject + " key is currently selected to change.");
    }

    private void resetHBoxesCssBgColorClass() {

        hBoxes.forEach(item -> item.setStyle("-fx-background-color: transparent;"));
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
                playPauseKeyCode = pressedKeyCode;
            } else if (currentlyActiveHBox.equals(nextSongHBox)) {
                nextSongKeyCode = pressedKeyCode;
            } else if (currentlyActiveHBox.equals(previousSongHBox)) {
                previousSongKeyCode = pressedKeyCode;
            } else if (currentlyActiveHBox.equals(volumeUpHBox)) {
                volumeUpKeyCode = pressedKeyCode;
            } else if (currentlyActiveHBox.equals(volumeDownHBox)) {
                volumeDownKeyCode = pressedKeyCode;
            }
            logger.debug("Assigned: " + keyType + " as "
                    + currentlyActiveHBox.getId().replace("HBox", "") + " key.");
        }

        updateCurrentKeyTextField(keyType);
    }


    @FXML
    private void saveConfig() {

        config.setControlMustBePressed(controlCheckBox.isSelected());
        config.setAltMustBePressed(altCheckBox.isSelected());
        config.setShiftMustBePressed(shiftCheckBox.isSelected());

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

        if (toast != null) {
            config.setToastEnabled(toastEnableCheckBox.isSelected());
            config.setToastScreenPosition(toast.getToastPosition());
        }

        SaveConfig.saveConfigToFile(config);
    }

    @FXML
    private void enableToast() {

        if (toastEnableCheckBox.isSelected()) {
            toast = new Toast();
            addRadioButtonsHandler();
        } else {
            toast.disableToast();
            toast = null;
            if (toggleListener != null) {
                taskbarPositionGroup.selectedToggleProperty().removeListener(toggleListener);
            }
        }
    }

    @FXML
    public void addRadioButtonsHandler() {

        toggleListener = (observable, oldValue, newValue) -> {
            if (newValue == taskbarStartRadio) {
                toast.setToastPosition(ScreenPosition.TASKBAR_START);
            } else if (newValue == taskbarEndRadio) {
                toast.setToastPosition(ScreenPosition.TASKBAR_END);
            } else if (newValue == bottomLeftRadio) {
                toast.setToastPosition(ScreenPosition.SCREEN_LEFT);
            } else if (newValue == bottomRightRadio) {
                toast.setToastPosition(ScreenPosition.SCREEN_RIGHT);
            }
        };

        taskbarPositionGroup.selectedToggleProperty().addListener(toggleListener);
    }
}

