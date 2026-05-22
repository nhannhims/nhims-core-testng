package com.nhims.utils;

import java.io.File;
import java.nio.file.Paths;

import com.nhims.constants.FileConst;

public class HFolder {
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

	public static void createMoreFolder(String... folders) {
		String path = FileConst.MAIN_PATH;
		for (int i = 0; i < folders.length; i++) {
			path = Paths.get(path, folders[i]).toString();
			createNewFolder(path);
		}
	}
}
