package hotkey;

import com.sun.jna.Library;
import com.sun.jna.WString;

public interface AutoHotkeyDll extends Library {
    void ahkExec(WString s);

    void ahkDll(WString s, WString o, WString p);

    void addFile(WString s, int a);

    void ahkTextDll(WString s, WString o, WString p);

    void ahkFunction(WString f);

    void ahkFunction(WString f, WString p1);
}
