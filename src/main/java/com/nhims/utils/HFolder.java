package com.nhims.utils;

import java.io.File;
import java.nio.file.Paths;

import com.nhims.constants.FileConst;

public class HFolder {
	/**
	 * Creates a new directory at the specified path if it does not exist.
	 *
	 * @param folderPath the directory path to create
	 * @return true if the directory already exists, false if it had to be created
	 */
	public static boolean createNewFolder(String folderPath) {
		boolean flag = true;
		File file = new File(folderPath);
		if (!file.exists()) {
			flag = false;
			file.mkdirs();
		} else {
			flag = true;
		}
		return flag;
	}

	/**
	 * Creates a nested folder path directory by directory, starting from MAIN_PATH.
	 *
	 * @param folders varargs of subdirectory names to create sequentially
	 */
	public static void createMoreFolder(String... folders) {
		String path = FileConst.MAIN_PATH;
		for (int i = 0; i < folders.length; i++) {
			path = Paths.get(path, folders[i]).toString();
			createNewFolder(path);
		}
	}
}
