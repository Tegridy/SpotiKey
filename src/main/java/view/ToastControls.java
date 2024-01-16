package view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

class ToastControls {

    @FXML
    protected ProgressBar songProgressBar;

    @FXML
    protected ImageView trackCoverView;

    @FXML
    protected Button playPauseButton;

    @FXML
    protected Button nextSongButton;

    @FXML
    protected Button previousSongButton;

    @FXML
    protected ImageView playPauseImageView;

    @FXML
    protected TextField songTitleTextField;

    @FXML
    protected Pane songTitlePane;
}
