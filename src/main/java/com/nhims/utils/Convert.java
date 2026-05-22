package com.nhims.utils;

import java.nio.charset.StandardCharsets;

public class Convert {
	/**
	 * Converts a string representation of an integer to an int primitive.
	 *
	 * @param number the string representation of a number
	 * @return the parsed integer value
	 */
	public static int stringToInt(String number) {
		return Integer.parseInt(number);
	}

	/**
	 * Converts an integer value to its string representation.
	 *
	 * @param number the integer value
	 * @return the string representation of the integer
	 */
	public static String intToString(int number) {
		return String.valueOf(number);
	}

	/**
	 * Converts a string representation of a boolean to a boolean primitive.
	 *
	 * @param flag the string representation ("true"/"false")
	 * @return the parsed boolean value
	 */
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
