package com.nhims.utils;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.nhims.constants.Configs.ConfigFile;
import com.nhims.constants.FileConst;

public class Logger {
	private static Writer writer;
	private static String logName = "Log_" + HDate.formatDate("yyyyMMddhhmmss");
	private static boolean logFlag = Convert.stringToBoolean(HFile.getConfig(ConfigFile.logger).toString());

	public static void Info(String infoMessage) {
		if (logFlag == true) {
			writeLog("[INFO] " + infoMessage);
		}
		System.out.println("[INFO] " + infoMessage);
	}

	public static void Warning(String warMessage) {
		if (logFlag == true) {
			writeLog("[WARNING] " + warMessage);
		}
		System.out.println("[WARNING] " + warMessage);
	}

	public static void Error(String errMessage) {
		if (logFlag == true) {
			writeLog("[ERROR] " + errMessage);
		}
		System.out.println("[ERROR] " + errMessage);
	}

	public static void System(String folderName, String fileName, String messsage) {
		if (logFlag == true) {
			writeLog(folderName, fileName, "[SYSTEM] " + messsage);
		}
		System.out.println("[SYSTEM] " + messsage);
	}

	private static void writeLog(String message) {
		HFolder.createMoreFolder("test-reports", "logs");
		String fileName = logName;
		String filePath = HString.replace(FileConst.LOG_FILE, fileName);
		try {
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath, true), "utf-8"));
			addLogToFile(writer, message);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Logs file not found at " + filePath);
		} finally {
			try {
				writer.close();
			} catch (Exception ex) {
				ex.getMessage();
			}
		}
	}

	private static void writeLog(String folderName, String logName, String message) {
		HFolder.createMoreFolder(folderName);
		String fileName = logName;
		String filePath = FileConst.MAIN_PATH + "//" + folderName + "//" + fileName + ".txt";
		try {
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath, true), "utf-8"));
			addLogToFile(writer, message);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Logs file not found at " + filePath);
		} finally {
			try {
				writer.close();
			} catch (Exception ex) {
				ex.getMessage();
			}
		}
	}

	private static void addLogToFile(Writer w, String text) {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY.MM.dd hh:mm:ss");
		String content = HString.replace("%s >>> %s", sdf.format(date), text);
		try {
			w.append(content);
			w.append("\r\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException("Cannot write text in to file");
		}
	}
}
