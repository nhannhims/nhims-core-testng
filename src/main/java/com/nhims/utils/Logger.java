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

	public static void info(String infoMessage) {
		if (logFlag) {
			log.info(infoMessage);
		}
	}

	public static void warning(String warMessage) {
		if (logFlag) {
			log.warn(warMessage);
		}
	}

	public static void error(String errMessage) {
		if (logFlag) {
			log.error(errMessage);
		}
	}

	public static void system(String folderName, String fileName, String messsage) {
		if (logFlag) {
			log.info("[SYSTEM] {}", messsage);
			writeLog(folderName, fileName, "[SYSTEM] " + messsage);
		}
	}

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
