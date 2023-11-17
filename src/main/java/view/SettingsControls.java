package view;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
class SettingsControls {

    @FXML
    protected CheckBox controlCheckBox;
    @FXML
    protected CheckBox altCheckBox;
    @FXML
    protected CheckBox shiftCheckBox;
    @FXML
    protected CheckBox playPauseCheckBox;
    @FXML
    protected CheckBox nextSongCheckBox;
    @FXML
    protected CheckBox previousSongCheckBox;
    @FXML
    protected CheckBox volumeUpCheckBox;
    @FXML
    protected CheckBox volumeDownCheckBox;
    @FXML
    protected TextField currentKeyTextField;

    @FXML
    protected HBox playPauseHBox;
    @FXML
    protected HBox nextSongHBox;
    @FXML
    protected HBox previousSongHBox;
    @FXML
    protected HBox volumeUpHBox;
    @FXML
    protected HBox volumeDownHBox;

    @FXML
    protected CheckBox toastEnableCheckBox;

    @FXML
    protected RadioButton taskbarStartRadio;

    @FXML
    protected RadioButton taskbarEndRadio;

    @FXML
    protected RadioButton bottomLeftRadio;

    @FXML
    protected RadioButton bottomRightRadio;
}
