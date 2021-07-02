package player;

import com.sun.jna.WString;
import hotkey.AutoHotkeyDll;
import hotkey.AutoHotkeyLoader;

public class PlayerController {

    private final AutoHotkeyDll autoHotkeyDll;

    public PlayerController() {
        this.autoHotkeyDll = AutoHotkeyLoader.getInstance();
    }

    public void playPauseSong() {
        this.autoHotkeyDll.ahkFunction(new WString("jplaypause"));
    }


    public void skipToNextSong() {
        this.autoHotkeyDll.ahkFunction(new WString("jnext"));
    }

    public void skipToPreviousSong() {
        this.autoHotkeyDll.ahkFunction(new WString("jprev"));
    }

    public void scrollSongForward() {
        this.autoHotkeyDll.ahkFunction(new WString("jfwd"));
    }

    public void scrollSongBackward() {
        this.autoHotkeyDll.ahkFunction(new WString("jback"));
    }

    public void volumeUp() {
        this.autoHotkeyDll.ahkFunction(new WString("jvolumeup"));
    }

    public void volumeDown() {
        this.autoHotkeyDll.ahkFunction(new WString("jvolumedown"));
    }
}
