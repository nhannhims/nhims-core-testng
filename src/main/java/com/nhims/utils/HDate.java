package com.nhims.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class HDate {
	/**
	 * Formats the current date/time using the given pattern.
	 * Uses Java 8 DateTimeFormatter which is thread-safe.
	 *
	 * @param pattern the date/time format pattern (e.g. "yyyy_MM_dd_hh_mm_ss")
	 * @return the formatted date/time string
	 */
	public static String formatDate(String pattern) {
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		return now.format(formatter);
	}
}
