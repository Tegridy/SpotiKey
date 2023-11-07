package view;

import de.labystudio.spotifyapi.SpotifyAPI;
import de.labystudio.spotifyapi.SpotifyAPIFactory;
import de.labystudio.spotifyapi.SpotifyListener;
import de.labystudio.spotifyapi.model.Track;
import de.labystudio.spotifyapi.open.OpenSpotifyAPI;
import javafx.animation.FadeTransition;
import javafx.animation.Transition;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;

import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import player.PlayerController;
import utils.ScreenCoordinates;
import utils.ScreenPosition;
import utils.ToastPosition;


import java.awt.image.BufferedImage;
import java.io.IOException;


public class ToastView {

    private final Stage stage;
    private final Logger logger;
    private final SpotifyAPI spotifyAPI;
    private final OpenSpotifyAPI openSpotifyAPI;
    private PlayerController playerController;
    private Image playImage;
    private Image pauseImage;
    private FadeTransition fadeTransition;
    private static boolean isPlaying;

    @FXML
    private ProgressBar songProgressBar;

    @FXML
    private ImageView trackCoverView;

    @FXML
    private Button playPauseButton;

    @FXML
    private Button nextSongButton;

    @FXML
    private Button previousSongButton;

    @FXML
    private ImageView playPauseImageView;

    @FXML private TextField songTitleTextField;

    public ToastView() {
        stage = new Stage();
        spotifyAPI = SpotifyAPIFactory.create();
        openSpotifyAPI = new OpenSpotifyAPI();
        playerController = PlayerController.getInstance();

        logger = LoggerFactory.getLogger(ToastView.class);

        try {
            playImage = new Image(getClass().getResourceAsStream("../icon/play.png"));
            pauseImage = new Image(getClass().getResourceAsStream("../icon/pause-circle.png"));

            fadeTransition = new FadeTransition(Duration.millis(1300), trackCoverView);
            fadeTransition.setFromValue(0.1);
            fadeTransition.setToValue(1);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/toast.fxml"));
            loader.setController(this);

            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.setResizable(false);
            stage.setAlwaysOnTop(true);
            stage.setTitle("SpotiKeyToast");
            Scene scene = new Scene(loader.load());
            scene.setFill(Color.TRANSPARENT);
            stage.setScene(scene);
            stage.show();

            initSpotifyAPI();

            choosePlaceForToast();

            addEventsToToastButtons();

        } catch (Throwable e) {
            logger.warn("Exception during initializing view: " + e.getMessage());
        }
    }

    private void addEventsToToastButtons() {
        playPauseButton.setOnAction(playPauseEvent);
        nextSongButton.setOnAction(nextSongEvent);
        previousSongButton.setOnAction(previousSongEvent);
    }

    private void initSpotifyAPI() {
        spotifyAPI.registerListener(new SpotifyListener() {
            @Override
            public void onConnect() {
                isPlaying = spotifyAPI.isPlaying();
                setPlaying(isPlaying);
                logger.debug("Connected to Spotify!");
            }

            @Override
            public void onTrackChanged(Track track) {
                logger.debug(String.format("Track changed: %s (%s)\n", track, track.getLength()));
                fetchAndSetCurrentTrackImage();
                fetchAndSetCurrentSongTitle();
            }

            @Override
            public void onPositionChanged(int position) {
                getCurrentSongPositionAndUpdateProgressBar(position);
            }

            @Override
            public void onPlayBackChanged(boolean isPlaying) {
                logger.debug(isPlaying ? "Song started playing" : "Song stopped playing");
            }

            @Override
            public void onSync() {
            }

            @Override
            public void onDisconnect(Exception exception) {
                logger.debug("Disconnected: " + exception.getMessage());

                // TODO Disable toast

                spotifyAPI.stop();
            }
        });

        spotifyAPI.initialize();
        changePlayPauseIcon(isPlaying);
        fetchAndSetCurrentSongTitle();
    }

    private void choosePlaceForToast() {

        // TODO Switch depending on chosen checkbox

        ScreenCoordinates screenCoordinates = ToastPosition.getPositionOnScreen(ScreenPosition.TASKBAR_START);

        stage.setX(screenCoordinates.x());
        stage.setY(screenCoordinates.y());

    }

    private void fetchAndSetCurrentTrackImage() {

        try {
            if (spotifyAPI.hasTrack()) {
                Track track = spotifyAPI.getTrack();
                BufferedImage imageTrackCover = openSpotifyAPI.requestImage(track);
                Image image = SwingFXUtils.toFXImage(imageTrackCover, null);
                trackCoverView.setImage(image);
                fadeTransition.setNode(trackCoverView);
                fadeTransition.setCycleCount(1);
                fadeTransition.play();
            } else {
                logger.info("Track is not loaded yet. Try to unpause song.");
            }
        } catch (IOException ex) {
            logger.warn("Can't fetch track image: " + ex.getMessage());
        }
    }

    private void fetchAndSetCurrentSongTitle() {

        if (spotifyAPI.hasTrack()) {
            Track track = spotifyAPI.getTrack();
            String songTitle = track.getName();
            songTitleTextField.setText(songTitle);
        } else {
            logger.info("Track is not loaded yet. Try to unpause song.");
        }
    }
    private void getCurrentSongPositionAndUpdateProgressBar(int position) {

        if (!spotifyAPI.hasTrack()) {
            return;
        }

        int length = spotifyAPI.getTrack().getLength();
        double currentProgress = 1.0F / length * position;

        songProgressBar.setProgress(currentProgress);

        // logger.debug(String.format("Position changed: %s of %s (%d%%)\n", position, length, (int) currentProgress));
    }

    private final EventHandler<ActionEvent> playPauseEvent = event -> {
        isPlaying = !isPlaying;
        changePlayPauseIcon(isPlaying);
        playerController.playPauseSong();
    };
    private final EventHandler<ActionEvent> nextSongEvent = event -> playerController.skipToNextSong();
    private final EventHandler<ActionEvent> previousSongEvent = event -> playerController.skipToPreviousSong();



    private void changePlayPauseIcon(boolean isPlaying) {

        if (isPlaying) {
            playPauseImageView.setImage(playImage);
        } else {
            playPauseImageView.setImage(pauseImage);
        }
        playPauseButton.setGraphic(playPauseImageView);
    }

    public void setPlaying(boolean playing) {
        isPlaying = playing;
    }

    public static boolean isPlaying() {
        return isPlaying;
    }
}

