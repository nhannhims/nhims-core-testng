package com.nhims.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class HDate {
	public static String formatDate(String format) {
		Date date = new Date();
		SimpleDateFormat ft = new SimpleDateFormat(format);
		String content = String.format("%s", ft.format(date));
		return content;
	}
}
