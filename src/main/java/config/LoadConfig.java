package config;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoadConfig {

    private static Logger logger;

    public static void loadConfigFromFile() {
        logger = Logger.getLogger(LoadConfig.class.getName());
        Config config = Config.getInstance();

        File configFile = new File(System.getProperty("user.dir") + "\\src\\main\\resources\\configs\\conf.json");

        try(BufferedReader fileReader = new BufferedReader(new FileReader(configFile))) {

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

            logger.log(Level.INFO, "Successfully read JSON config file to an object.");

        } catch (IOException | ParseException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
    }


    private static boolean convertToBoolean(Object jsonObject) {
        return Boolean.parseBoolean(jsonObject.toString());
    }

    private static int convertToInt(Object jsonObject) {
        return Integer.parseInt(jsonObject.toString());
    }
}
