package controller;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import view.GameBoard;


/**
 * Every application have it status updating
 * @author PEER-OLAF Siebers
 * 
 */

public class GameLogger extends Logger {

    /**
     * Title of game logger
     */
    private static Logger logger = Logger.getLogger("GameLogger");
    /**
     * format for current date and time
     */
    private DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    /**
     * get instance of calendar
     */
    private Calendar calendar = Calendar.getInstance();

    /**
     * constructor
     * @throws IOException input/output exception
     */
    public GameLogger() throws IOException {
        super("GameLogger", null);

        File directory = new File(System.getProperty("user.dir") + "/" + "logs");
        directory.mkdirs();

        FileHandler fh = new FileHandler(directory + "/" + GameBoard.GAME_NAME + ".log");
        logger.addHandler(fh);
        SimpleFormatter formatter = new SimpleFormatter();
        fh.setFormatter(formatter);
    }

    /**
	 * show logger date message
	 * @param message message
	 * @return date message
	 */
    private String createFormattedMessage(String message) {
        return dateFormat.format(calendar.getTime()) + " -- " + message;
    }
 
    /**
     * create logger message
     * @param message message
     * @throws IOException input/output
     */
    public void info(String message) {
        logger.info(createFormattedMessage(message));
    }

    /**
     * logger warning message
     * @param message warning message
     * @throws IOException input/output
     */
    public void warning(String message) {
        logger.warning(createFormattedMessage(message));
    }

    /**
     * logger severe message
     * @param message severe message
     * @throws IOException input/output
     */
    public void severe(String message) {
        logger.severe(createFormattedMessage(message));
    }
}