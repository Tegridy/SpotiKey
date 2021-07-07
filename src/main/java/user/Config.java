package user;

import lc.kra.system.keyboard.event.GlobalKeyEvent;

public class Config {

    private static Config instance;

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
        this.controlMustBePressed = true;
        this.altMustBePressed = false;
        this.shiftMustBePressed = false;

        this.playPauseKey = GlobalKeyEvent.VK_UP;
        playPauseKeyCombinationActivated = true;
        this.nextSongKey = GlobalKeyEvent.VK_RIGHT;
        nextSongKeyCombinationActivated = true;
        this.previousSongKey = GlobalKeyEvent.VK_LEFT;
        previousSongKeyCombinationActivated = true;
        this.volumeUpKey = GlobalKeyEvent.VK_OEM_PLUS;
        volumeUpKeyCombinationActivated = false;
        this.volumeDownKey = GlobalKeyEvent.VK_OEM_MINUS;
        volumeDownKeyCombinationActivated = false;

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
        this.playPauseKey = playPauseKey;
    }

    public void setNextSongKey(int nextSongKey) {
        this.nextSongKey = nextSongKey;
    }

    public void setPreviousSongKey(int previousSongKey) {
        this.previousSongKey = previousSongKey;
    }

    public void setVolumeUpKey(int volumeUpKey) {
        this.volumeUpKey = volumeUpKey;
    }

    public void setVolumeDownKey(int volumeDownKey) {
        this.volumeDownKey = volumeDownKey;
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
