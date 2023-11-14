package view;

import de.labystudio.spotifyapi.SpotifyAPI;
import de.labystudio.spotifyapi.SpotifyAPIFactory;
import de.labystudio.spotifyapi.SpotifyListener;
import de.labystudio.spotifyapi.model.Track;
import de.labystudio.spotifyapi.open.OpenSpotifyAPI;
import javafx.animation.FadeTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;


public class ToastController implements ChangeListener<Boolean> {

    private final Stage stage;
    private final Logger logger;
    private final SpotifyAPI spotifyAPI;
    private final OpenSpotifyAPI openSpotifyAPI;
    private Image playImage;
    private Image pauseImage;
    private FadeTransition fadeTransition;
    private PlayerController playerControllerInstance;
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

    public ToastController() {
        stage = new Stage();
        spotifyAPI = SpotifyAPIFactory.create();
        openSpotifyAPI = new OpenSpotifyAPI();
        playerControllerInstance = PlayerController.getInstance();

        logger = LoggerFactory.getLogger(ToastController.class);

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

    long posUpdated;
    private void initSpotifyAPI() {
        spotifyAPI.registerListener(new SpotifyListener() {
            @Override
            public void onConnect() {
                playerControllerInstance.setIsPlaying(spotifyAPI.isPlaying());
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

                playerControllerInstance.setIsPlaying(!playerControllerInstance.isPlaying());
                playerControllerInstance.setCurrentPosition(position);
                posUpdated = System.currentTimeMillis();

                stopUpdatingProgressBar();
                startUpdatingProgressBar();
            }

            @Override
            public void onPlayBackChanged(boolean isPlaying) {
                logger.debug(isPlaying ? "Song started playing" : "Song stopped playing");
            }

            @Override
            public void onSync() {
                boolean is = spotifyAPI.isPlaying();
                playerControllerInstance.setIsPlaying(is);
                if (!is) {
                    stopUpdatingProgressBar();
                }
            }

            @Override
            public void onDisconnect(Exception exception) {
                logger.debug("Disconnected: " + exception.getMessage());

                // TODO Disable toast
                spotifyAPI.stop();
            }
        });

        spotifyAPI.initialize();
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
    private void getCurrentSongPositionAndUpdateProgressBar(int position) throws InterruptedException {

        if (!spotifyAPI.hasTrack() || !spotifyAPI.hasPosition()) {
            PlayerController.getInstance().playPauseSong();
            PlayerController.getInstance().playPauseSong();
            return;
        }

        long timePassed = System.currentTimeMillis() - posUpdated;
        long interpolatedPosition = spotifyAPI.getPosition() + timePassed;

        int length = spotifyAPI.getTrack().getLength();
        double currentProgress = 1.0F / length * interpolatedPosition;

        songProgressBar.setProgress(currentProgress);
        System.out.println(interpolatedPosition + " / " + spotifyAPI.getTrack().getLength());

     //   logger.debug(String.format("Position changed: %s of %s (%d%%)\n", position, length, (int) currentProgress));
    }

    // Create a ScheduledExecutorService instance
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private ScheduledFuture<?> updaterHandle;

    public void startUpdatingProgressBar() {
        final Runnable updater = new Runnable() {
            public void run() {
                try {
                    getCurrentSongPositionAndUpdateProgressBar(0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        updaterHandle = scheduler.scheduleAtFixedRate(updater, 0, 1, TimeUnit.SECONDS);
    }

    public void stopUpdatingProgressBar() {
        if (updaterHandle != null) {
            updaterHandle.cancel(true);
        }
    }

    private final EventHandler<ActionEvent> playPauseEvent = event -> playerControllerInstance.playPauseSong();
    private final EventHandler<ActionEvent> nextSongEvent = event -> playerControllerInstance.skipToNextSong();
    private final EventHandler<ActionEvent> previousSongEvent = event -> playerControllerInstance.skipToPreviousSong();

    private void changePlayPauseIcon(boolean isPlaying) {

        if (isPlaying) {
            playPauseImageView.setImage(playImage);
        } else {
            playPauseImageView.setImage(pauseImage);
        }
        playPauseButton.setGraphic(playPauseImageView);
    }

    @Override
    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
        logger.debug("Is player playing: " + newValue);
        changePlayPauseIcon(newValue);
    }
}

