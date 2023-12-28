package player;

import com.sun.jna.WString;
import hotkey.AutoHotkeyDll;
import hotkey.AutoHotkeyLoader;

public class PlayerController {

    private final AutoHotkeyDll autoHotkeyDll;
    private static PlayerController playerInstance;

    private PlayerController() {
        this.autoHotkeyDll = AutoHotkeyLoader.getInstance();
    }

    public static PlayerController getInstance() {
        if (playerInstance == null) {
            playerInstance = new PlayerController();
        }
        return playerInstance;
    }

    public void playPauseSong() {
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

    public void mute() {
        autoHotkeyDll.ahkFunction(new WString("mute"));
    }
}
