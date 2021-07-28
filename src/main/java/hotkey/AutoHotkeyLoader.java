package hotkey;

import com.sun.jna.Native;
import com.sun.jna.WString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AutoHotkeyLoader {

    private final String userDir = System.getProperty("user.dir");
    private final String autohotkeyDir = userDir + "\\app\\libs";
    private final String scriptsDir = userDir + "\\app\\scripts\\SpotifyGlobalHotkeys.ahk";
    private static AutoHotkeyDll autoHotKeyDll;
    private final Logger logger;


    private AutoHotkeyLoader() {
        logger = LoggerFactory.getLogger(AutoHotkeyLoader.class);

        setLibsPath();
        loadDll();
        loadAhkScript();
    }

    private void setLibsPath() {
        System.setProperty("jna.library.path", autohotkeyDir);
        logger.info("Set libs path");
    }

    private void loadDll() {
        autoHotKeyDll = Native.load("AutoHotkey.dll", AutoHotkeyDll.class);
        logger.info("Loaded autoHotkey.dll");
    }

    private void loadAhkScript() {
        autoHotKeyDll.ahkTextDll(new WString(""), new WString(""), new WString(""));
        autoHotKeyDll.addFile(new WString(scriptsDir), 1);
        logger.info("Add script file to program");
    }

    public static AutoHotkeyDll getInstance() {
        if (autoHotKeyDll == null) {
            new AutoHotkeyLoader();
        }
        return autoHotKeyDll;
    }
}
