package user;

import lc.kra.system.keyboard.event.GlobalKeyEvent;

public class Config {
    boolean controlMustBePressed = true;
    boolean altMustBePressed = false;
    boolean shiftMustBePressed = false;

    int playPauseKey = GlobalKeyEvent.VK_UP;
    int nextSongKey = GlobalKeyEvent.VK_RIGHT;
    int previousSongKey = GlobalKeyEvent.VK_LEFT;
    int volumeUpKey = GlobalKeyEvent.VK_OEM_PLUS;
    int volumeDownKey = GlobalKeyEvent.VK_OEM_MINUS;

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
