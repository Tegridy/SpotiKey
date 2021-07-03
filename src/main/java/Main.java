import keystroke.KeystrokeListener;
import player.PlayerController;
import user.Config;
import user.SaveConfig;

public class Main {

    public static void main(String[] args) {
      //  KeystrokeListener keystrokeListener = new KeystrokeListener();
        Config config = new Config();
        SaveConfig saveConfig = new SaveConfig(config);
    }
}
