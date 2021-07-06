package keystroke;

import lc.kra.system.keyboard.GlobalKeyboardHook;
import lc.kra.system.keyboard.event.GlobalKeyAdapter;
import lc.kra.system.keyboard.event.GlobalKeyEvent;
import player.PlayerController;
import user.Config;

import java.util.logging.Level;
import java.util.logging.Logger;


public class KeystrokeListener extends Thread{

    private static boolean run = true;
    private final Logger logger;
    PlayerController playerController;

    public KeystrokeListener() {

        logger = Logger.getLogger(getClass().getName());
        playerController = new PlayerController();

        GlobalKeyboardHook keyboardHook = new GlobalKeyboardHook(true); // Use false here to switch to hook instead of raw input

        logger.log(Level.INFO, "Global keyboard hook successfully started");

        Config config = Config.getInstance();

        keyboardHook.addKeyListener(new GlobalKeyAdapter() {

            @Override
            public void keyPressed(GlobalKeyEvent event) {


            if (config.playPauseKeysPressed(event)){
                playerController.playPauseSong();
            } else if (config.nextSongKeysPressed(event)) {
                playerController.skipToNextSong();
            } else if (config.previousSongKeysPressed(event)) {
                playerController.skipToPreviousSong();
            } else if (config.volumeUpKeysPressed(event)) {
                playerController.volumeUp();
            } else if (config.volumeDownKeysPressed(event)) {
                playerController.volumeDown();
            }

            }

        });

        try {
            while(run) {
                Thread.sleep(128);
            }
        } catch(InterruptedException e) {
            logger.log(Level.WARNING, e.getMessage());
        } finally {
            keyboardHook.shutdownHook();
        }
    }
}
