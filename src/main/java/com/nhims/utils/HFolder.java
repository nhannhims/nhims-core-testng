package com.nhims.utils;

import java.io.File;

import com.nhims.constants.FileConst;

public class HFolder {
	public static boolean createNewFolder(String folderPath) {
		boolean flag = true;
		File file = new File(folderPath);
		if (!file.exists()) {
			flag = false;
			file.mkdir();
		} else {
			flag = true;
		}
		return flag;
	}

	public static void createMoreFolder(String... folders) {
		for (int i = 0; i < folders.length; i++) {
			createNewFolder(FileConst.MAIN_PATH + "//" + "folders[i]");
		}
	}
}
