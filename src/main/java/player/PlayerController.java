package player;

import com.sun.jna.WString;
import hotkey.AutoHotkeyDll;
import hotkey.AutoHotkeyLoader;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import view.ToastController;

public class PlayerController {

    private final AutoHotkeyDll autoHotkeyDll;
    private static PlayerController playerInstance;
    private static boolean isPlaying;
    private static int currentPosition;
    private static int songLength;
    private final BooleanProperty booleanProperty;

    private PlayerController() {
        this.autoHotkeyDll = AutoHotkeyLoader.getInstance();
        booleanProperty = new SimpleBooleanProperty(isPlaying);
        booleanProperty.set(isPlaying);
    }

    public static PlayerController getInstance() {
        if (playerInstance == null) {
            playerInstance = new PlayerController();
        }
        return playerInstance;
    }

    public void playPauseSong() {
        isPlaying = !isPlaying;
        booleanProperty.set(isPlaying);
        autoHotkeyDll.ahkFunction(new WString("jplaypause"));
    }

    public void skipToNextSong() {
        autoHotkeyDll.ahkFunction(new WString("jnext"));
    }

    public void skipToPreviousSong() {
        autoHotkeyDll.ahkFunction(new WString("jprev"));
    }

    public void volumeUp() {
        autoHotkeyDll.ahkFunction(new WString("jvolumeup"));
    }

    public void volumeDown() {
        autoHotkeyDll.ahkFunction(new WString("jvolumedown"));
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public void setIsPlaying(boolean isPlaying) {
        PlayerController.isPlaying = isPlaying;
        booleanProperty.set(isPlaying);
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(int currentPosition) {
        PlayerController.currentPosition = currentPosition;
    }

    public int getSongLength() {
        return songLength;
    }

    public void setSongLength(int songLength) {
        PlayerController.songLength = songLength;
    }

    public <T extends ChangeListener<Boolean>> void addIsPlayingListener(T newListener) {
        booleanProperty.addListener(newListener);
    }
}
