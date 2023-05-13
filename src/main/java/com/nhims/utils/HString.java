package com.nhims.utils;

public class HString {
	public static String replace(String text, Object... objects) {
		String val = String.format(Convert.formatStringToUTF8(text), objects);
		return val;
	}
}
