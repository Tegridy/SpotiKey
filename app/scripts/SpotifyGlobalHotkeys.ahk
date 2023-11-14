                    ;[ James' Media Keys ]-----------|
                    ;^End::jplaypause()               ; Play
                    ;^Space::jplaypause()             ; Pause
                    ;^Right::jnext()                  ; Skip Song
                    ;^Left::jprev()                   ; Previous Song
                    ;+Up::jvolumeup()                 ; Turn Volume Up
                    ;+Down::jvolumedown()             ; Turn Volume Down

                    ;[ James' Scrubbing Keys ] ------|
                    ;^Delete::jback()                 ; Scrub 15 seconds forward
                    ;^PgDn::jfwd()                    ; Scrub 15 seconds back

                    ;|-------------------------|
                    ;|------[ Functions ]------|
                    ;|-------------------------|

                    ;[ James Teh's Functions ]-|

                    ; https://gist.github.com/jcsteh/7ccbc6f7b1b7eb85c1c14ac5e0d65195

                     #SingleInstance
                     #NoTrayIcon

                     settimer, showlbl, 5

                     showlbl:
                     WinGet, hwnd, ID, SpotiKeyToast
                     WinSet, AlwaysOnTop, On, ahk_id %hwnd%
                     return

                    ; Get the HWND of the Spotify main window.
                    getSpotifyHwnd() {
                    	WinGet, spotifyHwnd, ID, ahk_exe spotify.exe
                    	; We need the app's third top level window, so get next twice.
                    	spotifyHwnd := DllCall("GetWindow", "uint", spotifyHwnd, "uint", 2)
                    	spotifyHwnd := DllCall("GetWindow", "uint", spotifyHwnd, "uint", 2)
                    	Return spotifyHwnd
                    }

                    ; Send a key to Spotify.
                    spotifyKey(key) {
                    	spotifyHwnd := getSpotifyHwnd()
                    	; Chromium ignores keys when it isn't focused.
                    	; Focus the document window without bringing the app to the foreground.
                    	ControlFocus, Chrome_RenderWidgetHostHWND1, ahk_id %spotifyHwnd%
                    	ControlSend, , %key%, ahk_id %spotifyHwnd%
                    	Return
                    }

                    jplaypause()
                    {
                    	DetectHiddenWindows On
                    	WinGet, style, Style, ahk_class Chrome_WidgetWin_0

                    	if !(style & 0x10000000)
                    		Send, {Media_Play_Pause}
                    	else if WinActive("ahk_class Chrome_WidgetWin_0")
                    		Send, {Space}
                    	else
                    		spotifyKey("{Space}")
                    	Return
                        ;PostMessage, 0x319,, 0xE0000,, ahk_exe Spotify.exe
                    }

                    jnext()
                    {
                    	DetectHiddenWindows On
                    	WinGet, style, Style, ahk_class Chrome_WidgetWin_0

                    	if !(style & 0x10000000)
                    		Send, {Media_Next}
                    	else if WinActive("ahk_class Chrome_WidgetWin_0")
                    		Send, ^{Right}
                    	else
                    		spotifyKey("^{Right}")
                    	Return
                        ;PostMessage, 0x319,, 0xB0000,, ahk_exe Spotify.exe
                    }

                    jprev()
                    {
                    	DetectHiddenWindows On
                    	WinGet, style, Style, ahk_class Chrome_WidgetWin_0

                    	if !(style & 0x10000000)
                    		Send, {Media_Prev}
                    	else if WinActive("ahk_class Chrome_WidgetWin_0")
                    		Send, ^{Left}
                    	else
                    		spotifyKey("^{Left}")
                    	Return
                    	;PostMessage, 0x319,, 0xC0000,, ahk_exe Spotify.exe
                    }

                    jfwd()
                    {
                    	DetectHiddenWindows On
                    	if !(style & 0x10000000)
                    		spotifyHwnd := getSpotifyHwnd()
                    		WinShow, ahk_id %spotifyHwnd%
                    		WinActivate, ahk_id %spotifyHwnd%
                    	if WinActive("ahk_class Chrome_WidgetWin_0")
                    		Send, +{Right}
                    	else
                    		spotifyKey("+{Right}")
                    	Return
                    }

                    jback()
                    {
                    	DetectHiddenWindows On
                    	if !(style & 0x10000000)
                    		spotifyHwnd := getSpotifyHwnd()
                    		WinShow, ahk_id %spotifyHwnd%
                    		WinActivate, ahk_id %spotifyHwnd%
                    	if WinActive("ahk_class Chrome_WidgetWin_0")
                    		Send, +{Left}
                    	else
                    		spotifyKey("+{Left}")
                    	Return
                    }

                    jvolumeup()
                    {
                    	WinGet, style, Style, ahk_class Chrome_WidgetWin_0

                    	if WinActive("ahk_class Chrome_WidgetWin_0")
                    		Send, ^{Up}
                    	else
                    		;spotifyKey("^{Up}")
                    		SoundSet +5
                    	Return
                    }

                    jvolumedown()
                    {
                    	WinGet, style, Style, ahk_class Chrome_WidgetWin_0

                    	if WinActive("ahk_class Chrome_WidgetWin_0")
                    		Send, ^{Down}
                    	else
                    		;spotifyKey("^{Down}")
                    		SoundSet -5
                    	Return
                    }

                    jtogglespotify()
                    {
                    	spotifyHwnd := getSpotifyHwnd()

                    	WinGet, style, Style, ahk_id %spotifyHwnd%
                    	WinGet MMX, MinMax, A

                    	if (style & 0x10000000) ; WS_VISIBLE
                    	{
                    		if (MMX = 1)
                    			WinActivate, ahk_id %spotifyHwnd%
                    		else
                    			WinHide, ahk_id %spotifyHwnd%
                    	}
                    	else
                    	{
                    		WinShow, ahk_id %spotifyHwnd%
                    		WinActivate, ahk_id %spotifyHwnd%
                    	}
                    	Return
                    }