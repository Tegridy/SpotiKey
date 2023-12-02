package config;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import view.Settings;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SaveConfig {

    private static Logger logger;

    public static void saveConfigToFile(Config config) {

        logger = LoggerFactory.getLogger(SaveConfig.class);
        JSONObject userConfig = new JSONObject();
        updateConfig(config, userConfig);

        File dir = new File( System.getenv("APPDATA"), "SpotiKey\\configs");
        File configFile = new File(dir, "conf.json");

        try {
            if (!dir.exists()) {
                if (dir.mkdirs() && configFile.createNewFile()) {
                    logger.debug("Config file created successfully.");
                }
            }
        } catch (IOException ex) {
            logger.warn("Could not save config file: " + ex.getMessage());
        }

        try (FileWriter file = new FileWriter(configFile)) {

            file.write(userConfig.toJSONString());

            logger.debug("Successfully copied JSON Object to File...");
            logger.debug("Config updated");
        } catch (IOException ex) {
            logger.warn("Can't save config to a file: " + ex.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private static void updateConfig(Config config, JSONObject userConfig) {

        JSONObject configProperties = new JSONObject();

        configProperties.put("controlMustBePressed", config.controlMustBePressed());
        configProperties.put("altMustBePressed", config.altMustBePressed());
        configProperties.put("shiftMustBePressed", config.shiftMustBePressed());

        configProperties.put("playPauseKey", config.getPlayPauseKey());
        configProperties.put("nextSongKey", config.getNextSongKey());
        configProperties.put("previousSongKey", config.getPreviousSongKey());
        configProperties.put("volumeUpKey", config.getVolumeUpKey());
        configProperties.put("volumeDownKey", config.getVolumeDownKey());

        configProperties.put("playPauseKeyCombinationActivated", config.isPlayPauseKeyCombinationActivated());
        configProperties.put("nextSongKeyCombinationActivated", config.isNextSongKeyCombinationActivated());
        configProperties.put("previousSongKeyCombinationActivated", config.isPreviousSongKeyCombinationActivated());
        configProperties.put("volumeUpKeyCombinationActivated", config.isVolumeUpKeyCombinationActivated());
        configProperties.put("volumeDownKeyCombinationActivated", config.isVolumeDownKeyCombinationActivated());

        configProperties.put("toastEnabled", config.isToastEnabled());
        configProperties.put("toastScreenPosition", config.getToastScreenPosition());

        userConfig.put("config", configProperties);
    }
}
