package config;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.ScreenPosition;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


public class LoadConfig {

    private static Logger logger;

    public static void loadConfigFromFile() {

        logger = LoggerFactory.getLogger(LoadConfig.class);
        Config config = Config.getInstance();
        File configFile = new File(System.getProperty("user.dir") + "\\configs\\conf.json");

        if (configFile.length() > 1) {

            try (BufferedReader fileReader = new BufferedReader(new FileReader(configFile))) {

                String currentLine = fileReader.readLine();

                JSONParser parser = new JSONParser();
                JSONObject json = (JSONObject) parser.parse(currentLine);

                json = (JSONObject) json.get("config");

                config.setControlMustBePressed(convertToBoolean(json.get("controlMustBePressed")));
                config.setAltMustBePressed(convertToBoolean(json.get("altMustBePressed")));
                config.setShiftMustBePressed(convertToBoolean(json.get("shiftMustBePressed")));

                config.setPlayPauseKey(convertToInt(json.get("playPauseKey")));
                config.setNextSongKey(convertToInt(json.get("nextSongKey")));
                config.setPreviousSongKey(convertToInt(json.get("previousSongKey")));
                config.setVolumeUpKey(convertToInt(json.get("volumeUpKey")));
                config.setVolumeDownKey(convertToInt(json.get("volumeDownKey")));

                config.setPlayPauseKeyCombinationActivated(convertToBoolean(json.get("playPauseKeyCombinationActivated")));
                config.setNextSongKeyCombinationActivated(convertToBoolean(json.get("nextSongKeyCombinationActivated")));
                config.setPreviousSongKeyCombinationActivated(convertToBoolean(json.get("previousSongKeyCombinationActivated")));
                config.setVolumeUpKeyCombinationActivated(convertToBoolean(json.get("volumeUpKeyCombinationActivated")));
                config.setVolumeDownKeyCombinationActivated(convertToBoolean(json.get("volumeDownKeyCombinationActivated")));

                config.setToastEnabled(convertToBoolean(json.get("toastEnabled")));
                config.setToastScreenPosition(convertToScreenPosition(json.get("toastScreenPosition")));

                logger.debug("Successfully loaded JSON config file to an object.");

            } catch (IOException | ParseException ex) {
                logger.warn("Can't load config from file: " + ex.getMessage());
            }
        }
    }

    private static boolean convertToBoolean(Object jsonObject) {
        return Boolean.parseBoolean(jsonObject.toString());
    }

    private static int convertToInt(Object jsonObject) {
        return Integer.parseInt(jsonObject.toString());
    }

    private static ScreenPosition convertToScreenPosition(Object jsonObject) {

        String position = String.valueOf(jsonObject);

        switch (ScreenPosition.valueOf(position)) {
            case SCREEN_LEFT -> {
                return ScreenPosition.SCREEN_LEFT;
            }
            case SCREEN_RIGHT -> {
                return ScreenPosition.SCREEN_RIGHT;
            }
            case TASKBAR_END -> {
                return ScreenPosition.TASKBAR_END;
            }
            default -> {
                return ScreenPosition.TASKBAR_START;
            }
        }
    }
}
