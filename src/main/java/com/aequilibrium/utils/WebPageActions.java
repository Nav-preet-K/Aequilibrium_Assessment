package com.aequilibrium.utils;


import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.aequilibrium.interfaces.IWebPageActions;

public class WebPageActions extends WebBrowserActions implements IWebPageActions {
	static WebDriver driver;
	static long ConditionalWaitTime = 2000;

	public static void WaitForPageToLoad(long seconds) {
		try {
			driver.manage().timeouts().pageLoadTimeout(seconds, TimeUnit.SECONDS);
		} catch (Exception e) {
			System.err.println("Unable to load Page");
			e.printStackTrace();
		}
	}

	public static void maximizeBrowser() {
		driver.manage().window().maximize();

	}

	public static void WaitABit() {
		try {
			Thread.sleep(2000);
		} catch (Exception e) {
			System.err.println("Unable to wait");
			e.printStackTrace();
		}
	}

	public static void WaitUntilElementVisible(long seconds, WebElement element) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, seconds);
			wait.until(ExpectedConditions.visibilityOf(element));
		} catch (Exception e) {
			System.out.println("Element not visible on webPage: " + e.getMessage());

		}
	}

	public static void closeBrowser() {

		driver.close();
	}
}
