package com.aequilibrium.utils;

import org.openqa.selenium.WebDriver;


public class WebBrowserActions {
	static WebDriver driver;
	static String Browser;

	public static void maximizeBrowser() {
		try {
			driver.manage().window().maximize();
		} catch (Exception e) {
			System.err.println("Failed to maximize window");
			e.printStackTrace();
		}
	}

	public static void closeBrowser() {
		try {
			driver.close();
		} catch (Exception e) {
			System.err.println("Failed to closeBrowser");
			e.printStackTrace();
		}
	}
}
