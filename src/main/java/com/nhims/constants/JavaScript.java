package com.nhims.constants;

public class JavaScript {
	public static String PAGE_LOADING = "return document.readyState;";
	public static String SCROLL_TO_ELEMENT = "arguments[0].scrollIntoView();";
	public static String SCROLL_TO_BOTTOM = "window.scrollTo(0, document.body.scrollHeight);";
	public static String SCROLL_TO_TOP = "window.scrollTo(0,0);";

	public static String ACTION_CLICK = "arguments[0].click();";
}
