package player;

import com.sun.jna.WString;
import hotkey.AutoHotkeyDll;
import hotkey.AutoHotkeyLoader;

public class PlayerController {

    private static final AutoHotkeyDll autoHotkeyDll;

    static {
        autoHotkeyDll = AutoHotkeyLoader.getInstance();
    }

    public static void playPauseSong() {
        autoHotkeyDll.ahkFunction(new WString("jplaypause"));
    }

    public static void skipToNextSong() {
        autoHotkeyDll.ahkFunction(new WString("jnext"));
    }

    public static void skipToPreviousSong() {
        autoHotkeyDll.ahkFunction(new WString("jprev"));
    }

    public static void volumeUp() {
        autoHotkeyDll.ahkFunction(new WString("jvolumeup"));
    }

    public static void volumeDown() {
        autoHotkeyDll.ahkFunction(new WString("jvolumedown"));
    }
}
