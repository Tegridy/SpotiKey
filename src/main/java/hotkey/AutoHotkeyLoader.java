package hotkey;

import com.sun.jna.Native;
import com.sun.jna.WString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class AutoHotkeyLoader {

    private final String autohotkeyDir;
    private final String scriptsDir;
    private static AutoHotkeyDll autoHotKeyDll;
    private final Logger logger;


    private AutoHotkeyLoader() {

        autohotkeyDir = "src/main/resources/libs";
        scriptsDir = "src/main/resources/scripts/SpotifyGlobalHotkeys.ahk";
        logger = LoggerFactory.getLogger(AutoHotkeyLoader.class);
        setLibsPath();
        loadAutohotkeyDll();
        loadAhkScript();
    }

    private void setLibsPath() {
        System.setProperty("jna.library.path", autohotkeyDir);
        logger.debug("Set libs path");
    }

    private void loadAutohotkeyDll() {
        autoHotKeyDll = Native.loadLibrary("AutoHotkey.dll", AutoHotkeyDll.class);
        logger.debug("Loaded autoHotkey.dll");
    }

    private void loadAhkScript() {
        autoHotKeyDll.ahkTextDll(new WString(""), new WString(""), new WString(""));
        autoHotKeyDll.addFile(new WString(scriptsDir), 1);
        logger.debug("Add spotify hotkeys script file to program");
    }

    public static AutoHotkeyDll getInstance() {
        if (autoHotKeyDll == null) {
            new AutoHotkeyLoader();
        }
        return autoHotKeyDll;
    }
}
