                    ; Keep toast always on top

                    #SingleInstance
                    #NoTrayIcon

                    settimer, showlbl, 1

                    showlbl:
                    WinGet, hwnd, ID, SpotiKeyToast
                    WinSet, AlwaysOnTop, On, ahk_id %hwnd%
                    return