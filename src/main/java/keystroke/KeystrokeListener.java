package keystroke;

import lc.kra.system.keyboard.GlobalKeyboardHook;
import lc.kra.system.keyboard.event.GlobalKeyAdapter;
import lc.kra.system.keyboard.event.GlobalKeyEvent;

import java.util.logging.Level;
import java.util.logging.Logger;


public class KeystrokeListener extends Thread{

    private static boolean run = true;
    private Logger logger;

    public KeystrokeListener() {

        logger = Logger.getLogger(getClass().getName());

        GlobalKeyboardHook keyboardHook = new GlobalKeyboardHook(true); // Use false here to switch to hook instead of raw input

        System.out.println("Global keyboard hook successfully started, press [escape] key to shutdown. Connected keyboards:");

        keyboardHook.addKeyListener(new GlobalKeyAdapter() {

            @Override
            public void keyPressed(GlobalKeyEvent event) {

//                if (event.isControlPressed() && event.isMenuPressed() && (event.getVirtualKeyCode() == 17)) {
//                    System.out.println("Should stop spotify");
//                }

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
