package config;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SaveConfig {

    private static Logger logger;

    @SuppressWarnings("unchecked")
    public static void saveConfigToFile(Config config) {
        logger = LoggerFactory.getLogger(SaveConfig.class);

        JSONObject userConfig = new JSONObject();

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

        userConfig.put("config", configProperties);

        File configFile = new File(System.getProperty("user.dir") + "\\app\\configs\\conf.json");

        try (FileWriter file = new FileWriter(configFile)) {

            file.write(userConfig.toJSONString());

            logger.info("Successfully copied JSON Object to File...");
            logger.info("JSON Object: " + userConfig);

        } catch (IOException e) {
            logger.warn("Can't save config to a file: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
