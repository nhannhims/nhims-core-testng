package com.nhims.scripts;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.nhims.browsers.Navigation;
import com.nhims.constants.Configs.EnvironmentConfig;
import com.nhims.data.FlyMeeConst.CHILD_MENU;
import com.nhims.data.FlyMeeConst.MAIN_MENU;
import com.nhims.listeners.TestListener;
import com.nhims.pages.GeneralPage;
import com.nhims.pages.ProductDetailPage;
import com.nhims.pages.SearchPage;
import com.nhims.utils.HFile;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

@Listeners(TestListener.class)
@Story("FlyMee E-Commerce")
public class TestExample {
	@Test(testName = "TC0001", description = "This is testcase 001")
	@Description("Verify favourite button changes status when clicked on a product")
	@Severity(SeverityLevel.CRITICAL)
	public void testCase001() {
		Navigation.visitTo(HFile.getConfigEnvironment(EnvironmentConfig.applicationUrl));
		GeneralPage.executeSearchProduct("工房PCボード");
		SearchPage.selectProduct("工房PCボード");
		ProductDetailPage.clickFavouriteButton();
		ProductDetailPage.verifyFavouriteButtonChangeStatus();
	}

	@Test(testName = "TC0002", description = "This is testcase 002")
	@Description("Search product, add to favourites, then navigate through category menu filters")
	@Severity(SeverityLevel.NORMAL)
	public void testCase002() {
		Navigation.visitTo(HFile.getConfigEnvironment(EnvironmentConfig.applicationUrl));
		GeneralPage.executeSearchProduct("工房PCボード");
		SearchPage.selectProduct("工房PCボード");
		ProductDetailPage.clickFavouriteButton();
		GeneralPage.selectMenuOnNavigationBar(MAIN_MENU.カラー);
		GeneralPage.selectChildMenuOnNavigationBar(MAIN_MENU.カラー, CHILD_MENU.イエロー);
	}
}
