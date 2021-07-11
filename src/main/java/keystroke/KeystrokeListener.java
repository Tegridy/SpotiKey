package keystroke;

import lc.kra.system.keyboard.GlobalKeyboardHook;
import lc.kra.system.keyboard.event.GlobalKeyAdapter;
import lc.kra.system.keyboard.event.GlobalKeyEvent;
import player.PlayerController;
import config.Config;

import java.util.logging.Level;
import java.util.logging.Logger;


public class KeystrokeListener extends Thread{

    private static boolean run = true;
    private final Logger logger;
    PlayerController playerController;

    public KeystrokeListener() {

        logger = Logger.getLogger(getClass().getName());
        playerController = new PlayerController();

        GlobalKeyboardHook keyboardHook = new GlobalKeyboardHook(false); // Use false here to switch to hook instead of raw input

        logger.log(Level.INFO, "Global keyboard hook successfully started");

        Config config = Config.getInstance();

        keyboardHook.addKeyListener(new GlobalKeyAdapter() {

            @Override
            public void keyPressed(GlobalKeyEvent event) {

            if (config.playPauseKeysPressed(event) && config.isPlayPauseKeyCombinationActivated()){
                playerController.playPauseSong();
            } else if (config.nextSongKeysPressed(event) && config.isNextSongKeyCombinationActivated()) {
                playerController.skipToNextSong();
            } else if (config.previousSongKeysPressed(event) && config.isPreviousSongKeyCombinationActivated()) {
                playerController.skipToPreviousSong();
            } else if (config.volumeUpKeysPressed(event) && config.isVolumeUpKeyCombinationActivated()) {
                playerController.volumeUp();
            } else if (config.volumeDownKeysPressed(event) && config.isVolumeDownKeyCombinationActivated()) {
                playerController.volumeDown();
            }
            }

        });
    }
}
