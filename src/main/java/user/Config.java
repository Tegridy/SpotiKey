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
}
