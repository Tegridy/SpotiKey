package utils;

import java.util.HashMap;

public class Utils {

    public static final HashMap<Integer, String> keyCodes;

    static {
                keyCodes = new HashMap<>();

                keyCodes.put(0x01, "Left mouse button");
                keyCodes.put(0x02, "Right mouse button");
                keyCodes.put(0x03, "Control-break processing");
                keyCodes.put(0x04, "Middle mouse button");
                keyCodes.put(0x05, "X1 mouse button");
                keyCodes.put(0x06, "X2 mouse button");
                keyCodes.put(0x08, "BACKSPACE key");
                keyCodes.put(0x09, "TAB key");
                keyCodes.put(0x0C, "CLEAR key");
                keyCodes.put(0x0D, "ENTER key");
                keyCodes.put(0x10, "SHIFT key");
                keyCodes.put(0x11, "CTRL key");
                keyCodes.put(0x12, "ALT key");
                keyCodes.put(0x13, "PAUSE key");
                keyCodes.put(0x14, "CAPS LOCK key");
                keyCodes.put(0x15, "IME Kana, Hanguel mode");
                keyCodes.put(0x17, "IME Junja mode");
                keyCodes.put(0x18, "IME final mode");
                keyCodes.put(0x19, "IME Hanja, Kanji mode");
                keyCodes.put(0x1B, "ESC key");
                keyCodes.put(0x1C, "IME convert");
                keyCodes.put(0x1D, "IME nonconvert");
                keyCodes.put(0x1E, "IME accept");
                keyCodes.put(0x1F, "IME mode change request");
                keyCodes.put(0x20, "SPACEBAR");
                keyCodes.put(0x21, "PAGE UP key");
                keyCodes.put(0x22, "PAGE DOWN key");
                keyCodes.put(0x23, "END key");
                keyCodes.put(0x24, "HOME key");
                keyCodes.put(0x25, "LEFT ARROW key");
                keyCodes.put(0x26, "UP ARROW key");
                keyCodes.put(0x27, "RIGHT ARROW key");
                keyCodes.put(0x28, "DOWN ARROW key");
                keyCodes.put(0x29, "SELECT key");
                keyCodes.put(0x2A, "PRINT key");
                keyCodes.put(0x2B, "EXECUTE key");
                keyCodes.put(0x2C, "PRINT SCREEN key");
                keyCodes.put(0x2D, "INS key");
                keyCodes.put(0x2E, "DEL key");
                keyCodes.put(0x2F, "HELP key");
                keyCodes.put(0x30, "0 key");
                keyCodes.put(0x31, "1 key");
                keyCodes.put(0x32, "2 key");
                keyCodes.put(0x33, "3 key");
                keyCodes.put(0x34, "4 key");
                keyCodes.put(0x35, "5 key");
                keyCodes.put(0x36, "6 key");
                keyCodes.put(0x37, "7 key");
                keyCodes.put(0x38, "8 key");
                keyCodes.put(0x39, "9 key");
                keyCodes.put(0x41, "A key");
                keyCodes.put(0x42, "B key");
                keyCodes.put(0x43, "C key");
                keyCodes.put(0x44, "D key");
                keyCodes.put(0x45, "E key");
                keyCodes.put(0x46, "F key");
                keyCodes.put(0x47, "G key");
                keyCodes.put(0x48, "H key");
                keyCodes.put(0x49, "I key");
                keyCodes.put(0x4A, "J key");
                keyCodes.put(0x4B, "K key");
                keyCodes.put(0x4C, "L key");
                keyCodes.put(0x4D, "M key");
                keyCodes.put(0x4E, "N key");
                keyCodes.put(0x4F, "O key");
                keyCodes.put(0x50, "P key");
                keyCodes.put(0x51, "Q key");
                keyCodes.put(0x52, "R key");
                keyCodes.put(0x53, "S key");
                keyCodes.put(0x54, "T key");
                keyCodes.put(0x55, "U key");
                keyCodes.put(0x56, "V key");
                keyCodes.put(0x57, "W key");
                keyCodes.put(0x58, "X key");
                keyCodes.put(0x59, "Y key");
                keyCodes.put(0x5A, "Z key");
                keyCodes.put(0x5B, "Left Windows key");
                keyCodes.put(0x5C, "Right Windows key");
                keyCodes.put(0x5D, "Applications key");
                keyCodes.put(0x5F, "Computer Sleep key");
                keyCodes.put(0x60, "Numeric keypad 0 key");
                keyCodes.put(0x61, "Numeric keypad 1 key");
                keyCodes.put(0x62, "Numeric keypad 2 key");
                keyCodes.put(0x63, "Numeric keypad 3 key");
                keyCodes.put(0x64, "Numeric keypad 4 key");
                keyCodes.put(0x65, "Numeric keypad 5 key");
                keyCodes.put(0x66, "Numeric keypad 6 key");
                keyCodes.put(0x67, "Numeric keypad 7 key");
                keyCodes.put(0x68, "Numeric keypad 8 key");
                keyCodes.put(0x69, "Numeric keypad 9 key");
                keyCodes.put(0x6A, "Multiply key");
                keyCodes.put(0x6B, "Add key");
                keyCodes.put(0x6C, "Separator key");
                keyCodes.put(0x6D, "Subtract key");
                keyCodes.put(0x6E, "Decimal key");
                keyCodes.put(0x6F, "Divide key");
                keyCodes.put(0x70, "F1 key");
                keyCodes.put(0x71, "F2 key");
                keyCodes.put(0x72, "F3 key");
                keyCodes.put(0x73, "F4 key");
                keyCodes.put(0x74, "F5 key");
                keyCodes.put(0x75, "F6 key");
                keyCodes.put(0x76, "F7 key");
                keyCodes.put(0x77, "F8 key");
                keyCodes.put(0x78, "F9 key");
                keyCodes.put(0x79, "F10 key");
                keyCodes.put(0x7A, "F11 key");
                keyCodes.put(0x7B, "F12 key");
                keyCodes.put(0x7C, "F13 key");
                keyCodes.put(0x7D, "F14 key");
                keyCodes.put(0x7E, "F15 key");
                keyCodes.put(0x7F, "F16 key");
                keyCodes.put(0x80, "F17 key");
                keyCodes.put(0x81, "F18 key");
                keyCodes.put(0x82, "F19 key");
                keyCodes.put(0x83, "F20 key");
                keyCodes.put(0x84, "F21 key");
                keyCodes.put(0x85, "F22 key");
                keyCodes.put(0x86, "F23 key");
                keyCodes.put(0x87, "F24 key");
                keyCodes.put(0x90, "NUM LOCK key");
                keyCodes.put(0x91, "SCROLL LOCK key");
                keyCodes.put(0xA0, "Left SHIFT key");
                keyCodes.put(0xA1, "Right SHIFT key");
                keyCodes.put(0xA2, "Left CONTROL key");
                keyCodes.put(0xA3, "Right CONTROL key");
                keyCodes.put(0xA4, "Left MENU key");
                keyCodes.put(0xA5, "Right MENU key");
                keyCodes.put(0xA6, "Browser Back key");
                keyCodes.put(0xA7, "Browser Forward key");
                keyCodes.put(0xA8, "Browser Refresh key");
                keyCodes.put(0xA9, "Browser Stop key");
                keyCodes.put(0xAA, "Browser Search key");
                keyCodes.put(0xAB, "Browser Favorites key");
                keyCodes.put(0xAC, "Browser Start and Home key");
                keyCodes.put(0xAD, "Volume Mute key");
                keyCodes.put(0xAE, "Volume Down key");
                keyCodes.put(0xAF, "Volume Up key");
                keyCodes.put(0xB0, "Next Track key");
                keyCodes.put(0xB1, "Previous Track key");
                keyCodes.put(0xB2, "Stop Media key");
                keyCodes.put(0xB3, "Play/Pause Media key");
                keyCodes.put(0xB4, "Start Mail key");
                keyCodes.put(0xB5, "Select Media key");
                keyCodes.put(0xB6, "Start Application 1 key");
                keyCodes.put(0xB7, "Start Application 2 key");
                keyCodes.put(0xBA, "US keyboard, the ';:' key");
                keyCodes.put(0xBB, "+ key");
                keyCodes.put(0xBC, ", key");
                keyCodes.put(0xBD, "- key");
                keyCodes.put(0xBE, ". key");
                keyCodes.put(0xBF, "US keyboard, the '/?' key");
                keyCodes.put(0xC0, "US keyboard, the '`~' key");
                keyCodes.put(0xDB, "US keyboard, the '[{' key");
                keyCodes.put(0xDC, "US keyboard, the '\\|' key");
                keyCodes.put(0xDD, "US keyboard, the ']}' key");
                keyCodes.put(0xDE, "single-quote/double-quote key");
                keyCodes.put(0xE2, "Angle bracket key or the backslash key");
                keyCodes.put(0xE5, "IME PROCESS key");
                keyCodes.put(0xF6, "Attn key");
                keyCodes.put(0xF7, "CrSel key");
                keyCodes.put(0xF8, "ExSel key");
                keyCodes.put(0xF9, "Erase EOF key");
                keyCodes.put(0xFA, "Play key");
                keyCodes.put(0xFB, "Zoom key");
                keyCodes.put(0xFC, "Reserved");
                keyCodes.put(0xFD, "PA1 key");
                keyCodes.put(0xFE, "Clear key");
    }
}
