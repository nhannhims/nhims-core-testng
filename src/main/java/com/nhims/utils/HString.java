package com.nhims.utils;

public class HString {
	/**
	 * Formats a template string using the provided arguments.
	 * Internally uses String.format with UTF-8 conversion.
	 *
	 * @param template the format template string
	 * @param objects  the arguments to fill into the template
	 * @return the formatted string
	 */
	public static String format(String template, Object... objects) {
		String val = String.format(Convert.formatStringToUTF8(template), objects);
		return val;
	}
}
