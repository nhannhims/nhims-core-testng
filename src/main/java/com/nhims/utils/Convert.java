package com.nhims.utils;

import java.nio.charset.Charset;

public class Convert {
	public static int stringToInt(String number) {
		return Integer.parseInt(number);
	}

	public static String intToString(int number) {
		return String.valueOf(number);
	}
	
	public static boolean stringToBoolean(String flag) {
		return Boolean.valueOf(flag);
	}

	public static String formatStringToUTF8(String text) {
		String val = new String(text.getBytes(), Charset.forName("UTF-8"));
		return val;
	}
}
