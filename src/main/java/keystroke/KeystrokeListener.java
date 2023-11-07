package keystroke;

import lc.kra.system.keyboard.GlobalKeyboardHook;
import lc.kra.system.keyboard.event.GlobalKeyAdapter;
import lc.kra.system.keyboard.event.GlobalKeyEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import player.PlayerController;
import config.Config;


public class KeystrokeListener extends Thread{

    private final Logger logger;
    private final PlayerController playerController;

    public KeystrokeListener() {

        logger = LoggerFactory.getLogger(KeystrokeListener.class);
        GlobalKeyboardHook keyboardHook = new GlobalKeyboardHook(false); // Use false here to switch to hook instead of raw input
        playerController = PlayerController.getInstance();
        logger.info("Global keyboard hook successfully started");
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
