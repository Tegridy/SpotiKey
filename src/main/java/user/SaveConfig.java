package user;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SaveConfig {

    private Logger logger;

    @SuppressWarnings("unchecked")
    public SaveConfig(Config config) {

        logger = Logger.getLogger(getClass().getName());

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

        userConfig.put("config", configProperties);

        File configFile = new File(System.getProperty("user.dir") + "\\src\\main\\resources\\configs\\conf.json");

        try(FileWriter file = new FileWriter(configFile)) {

            file.write(userConfig.toJSONString());

            logger.log(Level.INFO, "Successfully Copied JSON Object to File...");
            logger.log(Level.INFO, "JSON Object: " + userConfig);

        } catch (IOException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
    }
}
