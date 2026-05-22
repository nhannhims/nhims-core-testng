package com.nhims.utils;

import org.slf4j.LoggerFactory;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.nhims.constants.Configs.ConfigFile;
import com.nhims.constants.FileConst;

public class Logger {
	private static final org.slf4j.Logger log = LoggerFactory.getLogger(Logger.class);
	private static final boolean logFlag = Convert.stringToBoolean(HFile.getConfig(ConfigFile.logger));

	/**
	 * Logs an info-level message.
	 *
	 * @param infoMessage the message to log
	 */
	public static void info(String infoMessage) {
		if (logFlag) {
			log.info(infoMessage);
		}
	}

	/**
	 * Logs a warning-level message.
	 *
	 * @param warMessage the message to log
	 */
	public static void warning(String warMessage) {
		if (logFlag) {
			log.warn(warMessage);
		}
	}

	/**
	 * Logs an error-level message.
	 *
	 * @param errMessage the message to log
	 */
	public static void error(String errMessage) {
		if (logFlag) {
			log.error(errMessage);
		}
	}

	/**
	 * Logs a system-level event message and writes it to a file.
	 *
	 * @param folderName the folder name under MAIN_PATH to write the log file
	 * @param fileName   the name of the log file (without extension)
	 * @param messsage   the message to log
	 */
	public static void system(String folderName, String fileName, String messsage) {
		if (logFlag) {
			log.info("[SYSTEM] {}", messsage);
			writeLog(folderName, fileName, "[SYSTEM] " + messsage);
		}
	}

	/**
	 * Synchronized helper to write a log message to the specified file.
	 *
	 * @param folderName the directory name
	 * @param logName    the log file name
	 * @param message    the message to write
	 */
	private static synchronized void writeLog(String folderName, String logName, String message) {
		HFolder.createMoreFolder(folderName);
		String filePath = Paths.get(FileConst.MAIN_PATH, folderName, logName + ".txt").toString();
		try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath, true), StandardCharsets.UTF_8))) {
			addLogToFile(writer, message);
		} catch (Exception e) {
			log.error("Logs file not found at " + filePath, e);
			throw new RuntimeException("Logs file not found at " + filePath, e);
		}
	}

	/**
	 * Formats and appends a log entry to a Writer stream.
	 *
	 * @param w    the Writer stream
	 * @param text the message text to append
	 */
	private static void addLogToFile(Writer w, String text) {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
		String content = HString.format("%s >>> %s", sdf.format(date), text);
		try {
			w.append(content);
			w.append("\r\n");
		} catch (Exception e) {
			log.error("Cannot write text into file", e);
			throw new RuntimeException("Cannot write text into file", e);
		}
	}
}
