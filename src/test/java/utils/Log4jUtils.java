package utils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import io.cucumber.java.Scenario;
import org.apache.log4j.*;

public class Log4jUtils extends SeleniumUtils {
	
	public static Logger logger;

	@SuppressWarnings("deprecation")
	public static void createLog(Scenario cenario) {
		String dir = System.getProperty("user.dir");
		PropertyConfigurator.configure(dir + "\\src\\test\\resources\\log4j.properties");
		String filename = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		filename = filename.replace(".", "_");
		String scenarioName = cenario.getName();
		scenarioName = scenarioName.replace(" ", "_");
		SeleniumUtils.createDirectory(dir + "\\logs");
		logger = LogManager.getLogger(Log4jUtils.class.getName());
		try {
			Logger.shutdown();
			SimpleLayout layout = new SimpleLayout();
			FileAppender appender = new FileAppender(layout, dir + "\\logs\\" + scenarioName + "_" + filename + ".log",
					false);
			logger.addAppender(appender);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void logMessage(String message_type, String message) {
		if ("DEBUG".equals(message_type)) {
			logger.debug(message);
		} else if ("INFO".equals(message_type)) {
			logger.info(message);
		} else if ("WARN".equals(message_type)) {
			logger.warn(message);
		} else if ("ERROR".equals(message_type)) {
			logger.error(message);
		} else if ("FATAL".equals(message_type)) {
			logger.fatal(message);
		} else {
			logger.warn("Log type: " + message_type + " doesn't exist.");
			logger.warn("Message Being sent: " + message);
		}
	}
}