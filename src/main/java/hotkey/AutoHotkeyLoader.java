package hotkey;

import com.sun.jna.Native;
import com.sun.jna.WString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AutoHotkeyLoader {

    private static final String userDir = System.getProperty("user.dir");
    private final String autohotkeyDir = userDir + "\\app\\libs";
    private final String scriptsDir = userDir + "\\app\\scripts\\SpotifyGlobalHotkeys.ahk";
    private static AutoHotkeyDll autoHotKeyDll;
    private final Logger logger;


    private AutoHotkeyLoader() {

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
        logger.debug("Add script file to program");
    }

    public static void loadTaskbarToastOnTopScript() {

        if (getInstance() != null) {
            autoHotKeyDll.ahkTextDll(new WString(""), new WString(""), new WString(""));
            autoHotKeyDll.addFile(new WString(userDir + "\\app\\scripts\\ToastOnTopScript.ahk"), 1);
        } else {
            getInstance();
            loadTaskbarToastOnTopScript();
        }
    }

    public static AutoHotkeyDll getInstance() {
        if (autoHotKeyDll == null) {
            new AutoHotkeyLoader();
        }
        return autoHotKeyDll;
    }
}
