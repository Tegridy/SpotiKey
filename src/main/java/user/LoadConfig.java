package user;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoadConfig {

    private Logger logger;

    public LoadConfig(Config config) {
        logger = Logger.getLogger(getClass().getName());

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

            logger.log(Level.INFO, "Successfully read JSON file to a object.");

        } catch (IOException | ParseException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
    }

    private boolean convertToBoolean(Object jsonObject) {
        return Boolean.parseBoolean(jsonObject.toString());
    }

    private int convertToInt(Object jsonObject) {
        return Integer.parseInt(jsonObject.toString());
    }
}
