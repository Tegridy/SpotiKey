package hotkey;

import com.sun.jna.Native;
import com.sun.jna.WString;

import java.util.logging.Level;
import java.util.logging.Logger;


public class AutoHotkeyLoader {

    private final String userDir = System.getProperty("user.dir");
    private final String autohotkeyDir = userDir + "\\src\\main\\java\\libs";
    private final String scriptsDir = userDir + "\\src\\main\\resources\\scripts\\SpotifyGlobalHotkeys.ahk";
    private static AutoHotkeyDll autoHotKeyDll;
    private final Logger logger;


    private AutoHotkeyLoader() {
        logger = Logger.getLogger(getClass().getName());

        setLibsPath();
        loadDll();
        loadAhkScript();

    }

    private void setLibsPath() {
        System.setProperty("jna.library.path", autohotkeyDir);
        logger.log(Level.INFO, "Set libs path");
    }

    private void loadDll(){
        autoHotKeyDll = Native.load("AutoHotkey.dll", AutoHotkeyDll.class);
        logger.log(Level.INFO, "Loaded autoHotkey.dll");
    }

    private void loadAhkScript()  {
        autoHotKeyDll.ahkTextDll(new WString(""),new WString(""),new WString(""));
        autoHotKeyDll.addFile(new WString(scriptsDir), 1);
        logger.log(Level.INFO, "Add script file to program");
    }

    public static AutoHotkeyDll getInstance() {
        if (autoHotKeyDll == null) {
            new AutoHotkeyLoader();
        }
        return autoHotKeyDll;
    }
}
