package config;

import lc.kra.system.keyboard.event.GlobalKeyEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Config {

    private static Config instance;

    private final Logger logger;

    boolean controlMustBePressed;
    boolean altMustBePressed;
    boolean shiftMustBePressed;

    int playPauseKey;
    int nextSongKey;
    int previousSongKey;
    int volumeUpKey;
    int volumeDownKey;

    boolean playPauseKeyCombinationActivated;
    boolean nextSongKeyCombinationActivated;
    boolean previousSongKeyCombinationActivated;
    boolean volumeUpKeyCombinationActivated;
    boolean volumeDownKeyCombinationActivated;

    private Config() {
        logger = LoggerFactory.getLogger(Config.class);

        this.controlMustBePressed = true;
        this.altMustBePressed = false;
        this.shiftMustBePressed = false;

        this.playPauseKey = GlobalKeyEvent.VK_UP;
        playPauseKeyCombinationActivated = true;
        this.nextSongKey = GlobalKeyEvent.VK_RIGHT;
        nextSongKeyCombinationActivated = true;
        this.previousSongKey = GlobalKeyEvent.VK_LEFT;
        previousSongKeyCombinationActivated = true;
        this.volumeUpKey = GlobalKeyEvent.VK_P;
        volumeUpKeyCombinationActivated = false;
        this.volumeDownKey = GlobalKeyEvent.VK_L;
        volumeDownKeyCombinationActivated = false;

        logger.info("Initialize config class");

    }

    public static Config getInstance() {
        if (instance == null) {
            instance = new Config();
        }
        return instance;
    }

    private boolean isModifierKeyPressed(GlobalKeyEvent event) {
        return controlMustBePressed == event.isControlPressed() &&
                altMustBePressed == event.isMenuPressed() &&
                shiftMustBePressed == event.isShiftPressed();
    }

    public boolean playPauseKeysPressed(GlobalKeyEvent event) {
        return isModifierKeyPressed(event) &&
                (event.getVirtualKeyCode() == playPauseKey);
    }

    public boolean nextSongKeysPressed(GlobalKeyEvent event) {
        return isModifierKeyPressed(event) &&
                (event.getVirtualKeyCode() == nextSongKey);
    }

    public boolean previousSongKeysPressed(GlobalKeyEvent event) {
        return isModifierKeyPressed(event) &&
                (event.getVirtualKeyCode() == previousSongKey);
    }

    public boolean volumeUpKeysPressed(GlobalKeyEvent event) {
        return isModifierKeyPressed(event) &&
                (event.getVirtualKeyCode() == volumeUpKey);
    }

    public boolean volumeDownKeysPressed(GlobalKeyEvent event) {
        return isModifierKeyPressed(event) &&
                (event.getVirtualKeyCode() == volumeDownKey);
    }

    public boolean controlMustBePressed() {
        return controlMustBePressed;
    }

    public boolean altMustBePressed() {
        return altMustBePressed;
    }

    public boolean shiftMustBePressed() {
        return shiftMustBePressed;
    }

    public int getPlayPauseKey() {
        return playPauseKey;
    }

    public int getNextSongKey() {
        return nextSongKey;
    }

    public int getPreviousSongKey() {
        return previousSongKey;
    }

    public int getVolumeUpKey() {
        return volumeUpKey;
    }

    public int getVolumeDownKey() {
        return volumeDownKey;
    }

    public void setControlMustBePressed(boolean controlMustBePressed) {
        this.controlMustBePressed = controlMustBePressed;
    }

    public void setAltMustBePressed(boolean altMustBePressed) {
        this.altMustBePressed = altMustBePressed;
    }

    public void setShiftMustBePressed(boolean shiftMustBePressed) {
        this.shiftMustBePressed = shiftMustBePressed;
    }

    public void setPlayPauseKey(int playPauseKey) {
        if (playPauseKey == 0) {
            this.playPauseKey = this.getPlayPauseKey();
        } else {
            this.playPauseKey = playPauseKey;
        }
    }

    public void setNextSongKey(int nextSongKey) {
        if (nextSongKey == 0) {
            this.nextSongKey = this.getNextSongKey();
        } else {
            this.nextSongKey = nextSongKey;
        }
    }

    public void setPreviousSongKey(int previousSongKey) {
        if (previousSongKey == 0) {
            this.previousSongKey = this.getPreviousSongKey();
        } else {
            this.previousSongKey = previousSongKey;
        }
    }

    public void setVolumeUpKey(int volumeUpKey) {
        if (volumeUpKey == 0) {
            this.volumeUpKey = this.getVolumeUpKey();
        } else {
            this.volumeUpKey = volumeUpKey;
        }
    }

    public void setVolumeDownKey(int volumeDownKey) {
        if (volumeDownKey == 0) {
            this.volumeDownKey = this.getVolumeDownKey();
        } else {
            this.volumeDownKey = volumeDownKey;
        }
    }

    public boolean isPlayPauseKeyCombinationActivated() {
        return playPauseKeyCombinationActivated;
    }

    public void setPlayPauseKeyCombinationActivated(boolean playPauseKeyCombinationActivated) {
        this.playPauseKeyCombinationActivated = playPauseKeyCombinationActivated;
    }

    public boolean isNextSongKeyCombinationActivated() {
        return nextSongKeyCombinationActivated;
    }

    public void setNextSongKeyCombinationActivated(boolean nextSongKeyCombinationActivated) {
        this.nextSongKeyCombinationActivated = nextSongKeyCombinationActivated;
    }

    public boolean isPreviousSongKeyCombinationActivated() {
        return previousSongKeyCombinationActivated;
    }

    public void setPreviousSongKeyCombinationActivated(boolean previousSongKeyCombinationActivated) {
        this.previousSongKeyCombinationActivated = previousSongKeyCombinationActivated;
    }

    public boolean isVolumeUpKeyCombinationActivated() {
        return volumeUpKeyCombinationActivated;
    }

    public void setVolumeUpKeyCombinationActivated(boolean volumeUpKeyCombinationActivated) {
        this.volumeUpKeyCombinationActivated = volumeUpKeyCombinationActivated;
    }

    public boolean isVolumeDownKeyCombinationActivated() {
        return volumeDownKeyCombinationActivated;
    }

    public void setVolumeDownKeyCombinationActivated(boolean volumeDownKeyCombinationActivated) {
        this.volumeDownKeyCombinationActivated = volumeDownKeyCombinationActivated;
    }

    @Override
    public String toString() {
        return "Config{" +
                "controlMustBePressed=" + controlMustBePressed +
                ", altMustBePressed=" + altMustBePressed +
                ", shiftMustBePressed=" + shiftMustBePressed +
                ", playPauseKey=" + playPauseKey +
                ", nextSongKey=" + nextSongKey +
                ", previousSongKey=" + previousSongKey +
                ", volumeUpKey=" + volumeUpKey +
                ", volumeDownKey=" + volumeDownKey +
                '}';
    }
}
