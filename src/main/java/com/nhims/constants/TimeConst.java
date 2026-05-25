package com.nhims.constants;

import com.nhims.constants.Configs.ConfigFile;
import com.nhims.utils.Convert;
import com.nhims.utils.HFile;

public class TimeConst {
	public static final int SEC_MINIMUM_WAIT = 1;
	public static final int SEC_SHORT_WAIT = 5;
	public static final int SEC_NORMAL_WAIT = 10;
	public static final int SEC_DEFAULT_WAIT = 15;
	public static final int SEC_PAGE_LOAD_WAIT = 30;
	public static final int SEC_MEDIUM_WAIT = 40;
	public static final int SEC_LONG_WAIT = 60;

	/**
	 * Maximum retry attempts for stale element exceptions.
	 * Loaded from configs.properties (key: maxRetry), defaults to 2 if not set.
	 */
	public static final int MAX_RETRY = getMaxRetry();

	private static int getMaxRetry() {
		try {
			String value = HFile.getConfig(ConfigFile.maxRetry);
			if (value != null && !value.trim().isEmpty()) {
				return Convert.stringToInt(value.trim());
			}
		} catch (Exception e) {
			// Fall through to default
		}
		return 2;
	}
}
