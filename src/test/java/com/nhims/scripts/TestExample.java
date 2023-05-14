package com.nhims.scripts;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.nhims.browsers.Navigation;
import com.nhims.listeners.TestListener;
import com.nhims.pages.GenaralPage;
import com.nhims.pages.ProductDetailPage;
import com.nhims.pages.SearchPage;

@Listeners(TestListener.class)
public class TestExample {
	@Test(testName = "TC0001", description = "This is testcase 001")
	public void Test_Case_001() {
		Navigation.visitTo("https://flymee.jp/");
		GenaralPage.executeSearchProduct("工房PCボード");
		SearchPage.selectProduct("工房PCボード");
		ProductDetailPage.clickFavouriteButton();
		ProductDetailPage.verifyFavouriteButtonChangeStatus();
	}

	@Test(testName = "TC0002", description = "This is testcase 002")
	public void Test_Case_002() {
		System.out.println("This is test case 002");
	}
}