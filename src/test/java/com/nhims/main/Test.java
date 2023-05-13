package com.nhims.main;

import com.nhims.browsers.BrowserExtensions;
import com.nhims.browsers.Navigation;
import com.nhims.constants.Configs.DriverLoad;
import com.nhims.constants.Configs.DriverStatus;
import com.nhims.constants.Configs.EnvironmentConfig;
import com.nhims.drivers.DriverController;
import com.nhims.drivers.DriverExtensions;
import com.nhims.utils.HFile;

public class Test extends BrowserExtensions {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DriverController.instance.startChromeDriver();
		Navigation.visitTo(HFile.getConfigEnvironment(EnvironmentConfig.applicationUrl));
		DriverExtensions.createNewDriver(DriverLoad.Chrome);
		DriverExtensions.switchWebDriver(DriverStatus.New);
		Navigation.visitTo("https://wikipedia.vn");
		DriverExtensions.stopNewDriver();
		DriverExtensions.switchWebDriver(DriverStatus.Current);
		DriverController.instance.stopDriver();
	}

}
