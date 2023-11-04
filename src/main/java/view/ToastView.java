package view;

import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import de.labystudio.spotifyapi.SpotifyAPI;
import de.labystudio.spotifyapi.SpotifyAPIFactory;
import de.labystudio.spotifyapi.SpotifyListener;
import de.labystudio.spotifyapi.model.Track;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ToastView {

    private final Stage stage;
    private final Logger logger;
    private final SpotifyAPI spotifyAPI;

    @FXML
    private ProgressBar songProgressBar;

    public static final int TOAST_WIDTH = 300;
    public static final int TOAST_HEIGHT = 80;

    public ToastView() {
        stage = new Stage();
        spotifyAPI = SpotifyAPIFactory.create();

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

            initSpotifyAPI();

            findPlaceForToast();
        } catch (Throwable e) {
            logger.warn("Exception during initializing view: " + e.getMessage());
            e.printStackTrace();
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

    private void findPlaceForToast() {
        User32 user32 = User32.INSTANCE;

        System.out.println(user32);

        WinDef.HWND taskbarHWND = user32.FindWindow("Shell_TrayWnd", null);

        WinDef.HWND trayNotifyHWND = user32.FindWindowEx(taskbarHWND, null, "TrayNotifyWnd", null);
        System.out.println(trayNotifyHWND);
        WinDef.RECT bounds = new WinDef.RECT();
        bounds.write();

        System.out.println(user32.GetWindowRect(trayNotifyHWND, bounds));
        bounds.read();
        int trayNotifyWidth = bounds.toRectangle().width;
        int trayNotifyHeight = bounds.toRectangle().height;
        int screenWidth = bounds.left;
        int screenHeight = bounds.bottom;

        double posX = screenWidth - trayNotifyWidth;
        double posY = screenHeight - trayNotifyHeight;

        stage.setX(posX);
        stage.setY(posY);
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
}
