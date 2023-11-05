package view;

import de.labystudio.spotifyapi.SpotifyAPI;
import de.labystudio.spotifyapi.SpotifyAPIFactory;
import de.labystudio.spotifyapi.SpotifyListener;
import de.labystudio.spotifyapi.model.Track;
import de.labystudio.spotifyapi.open.OpenSpotifyAPI;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
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


    @FXML
    private ProgressBar songProgressBar;

    @FXML
    private ImageView trackCoverView;

    @FXML
    private Button playPauseButton;

    public ToastView() {
        stage = new Stage();
        spotifyAPI = SpotifyAPIFactory.create();
        openSpotifyAPI = new OpenSpotifyAPI();

        logger = LoggerFactory.getLogger(ToastView.class);

        try {

            Thread.sleep(2000);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/toast.fxml"));
            loader.setController(this);

//             stage.initModality(Modality.APPLICATION_MODAL);
//            stage.setResizable(false);

            stage.setScene(new Scene(loader.load()));

            logger.info("Toast scene loaded");

            stage.setAlwaysOnTop(true);
            stage.initStyle(StageStyle.UNDECORATED);

            stage.show();

            playPauseButton.setOnAction(event);


            initSpotifyAPI();

            choosePlaceForToast();
        } catch (Throwable e) {
            logger.warn("Exception during initializing view: " + e.getMessage());
        }
    }

    private void initSpotifyAPI() {
        spotifyAPI.registerListener(new SpotifyListener() {
            @Override
            public void onConnect() {
                logger.debug("Connected to Spotify!");
            }

            @Override
            public void onTrackChanged(Track track) {
               logger.debug(String.format("Track changed: %s (%s)\n", track, track.getLength()));
               fetchAndSetCurrentTrackImage();
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
               spotifyAPI.stop();
            }
        });

        spotifyAPI.initialize();
    }

    private void choosePlaceForToast() {

        // Switch depending on chosen checkbox

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
            } else {
                logger.info("Track is not loaded yet. Try to unpause song.");
            }
        } catch (IOException ex) {
            logger.warn("Can't fetch track image: " + ex.getMessage());
        }
    }

    private void getCurrentSongPositionAndUpdateProgressBar(int position) {

        if (!spotifyAPI.hasTrack()) {
            System.out.println("returned");
            return;
        }

        int length = spotifyAPI.getTrack().getLength();
        double currentProgress = 1.0F / length * position;

        songProgressBar.setProgress(currentProgress);

        logger.debug(String.format("Position changed: %s of %s (%d%%)\n", position, length, (int) currentProgress));
    }

    private void playPauseButtonClicked() {
        PlayerController.playPauseSong();
    }

    EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
        public void handle(ActionEvent e)
        {
            playPauseButtonClicked();
        }
    };
}
