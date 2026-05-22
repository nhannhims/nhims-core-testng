package com.nhims.utils;

import java.nio.charset.StandardCharsets;

public class Convert {
	public static int stringToInt(String number) {
		return Integer.parseInt(number);
	}

	public static String intToString(int number) {
		return String.valueOf(number);
	}

	public static boolean stringToBoolean(String flag) {
		return Boolean.parseBoolean(flag);
	}

	/**
	 * Ensures the string is encoded and decoded consistently as UTF-8.
	 * Java Strings are always Unicode internally, so this method simply
	 * re-encodes the string bytes in UTF-8 to sanitize any encoding mismatch.
	 */
	public static String formatStringToUTF8(String text) {
		if (text == null) return null;
		return new String(text.getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8);
	}
}
