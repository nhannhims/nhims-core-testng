package com.nhims.controls;

import com.nhims.utils.Logger;

public class Upload extends Control {

	public Upload(String xpathOrCssSelector) {
		// TODO Auto-generated constructor stub
		super(xpathOrCssSelector);
	}

	public Upload(String xpathOrCssSelector, String iframe, String timeout) {
		// TODO Auto-generated constructor stub
		super(xpathOrCssSelector, iframe, timeout);
	}

	/***
	 * Execute select file upload
	 * 
	 * @param path : Path of the file
	 */
	public void selectFile(String path) {
		get().sendKeys(path);
		Logger.Info("> E > Upload file has path [" + path + "]");
	}
}
