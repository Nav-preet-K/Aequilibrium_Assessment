package com.aequilibrium.test.LogIn;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.AssertJUnit;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.aequilibrium.utils.DataProviderUtils;
import com.aequilibrium.utils.WebPageActions;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class TestLogIn {
	static WebDriver driver;
	public ExtentReports extent;
	public ExtentTest extentTest;

	@BeforeTest
	public void setExtent() {
		extent = new ExtentReports(
				System.getProperty("user.dir") + "//src//test//resources//test-reports//reports.html", true);
		extent.addSystemInfo("Host Name", "Navpreet");	
	}

	@AfterTest
	public void endReport() { 
		extent.flush();	
	    extent.close();
	    
}
	@Parameters("browser")
	@BeforeClass
	
	public void initBrowser(String browser) throws Exception {
		System.out.println(browser);
		extentTest = extent.startTest("Initializing the Browser");
		System.out.println("Test Execution Started on browser[" + "" + "] at : " + new Date());
		if (browser.equalsIgnoreCase("Mozilla")) {
			System.setProperty("webdriver.gecko.driver",
					System.getProperty("user.dir") + "\\src\\test\\resources\\driver-exes\\geckodriver.exe");
			driver = new FirefoxDriver();
		} else if (browser.equalsIgnoreCase("Chrome")) {
			System.setProperty("webdriver.chrome.driver",
					System.getProperty("user.dir") + "\\src\\test\\resources\\driver-exes\\chromedriver89.exe");
			driver = new ChromeDriver();
			driver.manage().window().maximize();
			driver.get("https://www.saucedemo.com/");
			captureSnapshot("initBrowser");
		}
	}

	@Parameters("browser")
	@Test(dataProvider = "LoginData")
	public void loginTest(String user, String pwd, String exp) throws InterruptedException {
		extentTest = extent.startTest("Login Test Case");
		WebElement txtEmail = driver.findElement(By.id("user-name"));
		WebPageActions.WaitABit();
		txtEmail.clear();
		txtEmail.sendKeys(user);

		WebElement txtPassword = driver.findElement(By.id("password"));
		WebPageActions.WaitABit();
		txtPassword.clear();
		txtPassword.sendKeys(pwd);

		driver.findElement(By.xpath("//input[@id = 'login-button']")).click(); // Login button
		WebPageActions.WaitABit();
		String exp_title = "Swag Labs";
		String act_title = driver.getTitle();

		if (exp.equals("valid")) {
			if (exp_title.equals(act_title)) {
				driver.findElement(By.xpath("//button[@id = 'react-burger-menu-btn']")).click();
				Thread.sleep(2000);
				driver.findElement(By.xpath("//a[@id = 'logout_sidebar_link']")).click();
				Thread.sleep(2000);
				AssertJUnit.assertTrue(true);
			}

			else {
				AssertJUnit.assertTrue(false);
			}
		} else if (exp.equals("Invalid")) {
			if (exp_title.equals(act_title)) {
				driver.findElement(By.xpath("//button[@id = 'react-burger-menu-btn']")).click();
				Thread.sleep(2000);
				driver.findElement(By.xpath("//a[@id = 'logout_sidebar_link']")).click();
				Thread.sleep(2000);
				AssertJUnit.assertTrue(false);
			} else {
				AssertJUnit.assertTrue(true);
			}
		}

	}

	@DataProvider(name = "LoginData")
	public String[][] getData() throws IOException {
		String path = ".\\src\\test\\resources\\test-data\\LoginData.xlsx";
		DataProviderUtils xlutil = new DataProviderUtils(path);

		int totalrows = xlutil.getRowCount("Sheet1");
		int totalcols = xlutil.getCellCount("Sheet1", totalrows);

		String loginData[][] = new String[totalrows][totalcols];

		for (int i = 1; i <= totalrows; i++) // 1
		{
			for (int j = 0; j < totalcols; j++) // 0
			{
				loginData[i - 1][j] = xlutil.getCellData("Sheet1", i, j);
			}

		}

		return loginData;
	}

	@AfterClass
	public void tearDown() {
		System.out.println("Test Execution Finished on: ");

		WebDriver driver = null;
		driver.quit();
	}

	

	public static String captureSnapshot(String testName) throws Exception {

		ThreadLocal<String> path = new ThreadLocal<String>();
		path.set(System.getProperty("user.dir") + "//src//test//resources//screenshot//"
				+ new SimpleDateFormat("MMM-dd").format(new Date()) + "//" + testName + "_"
				+ new SimpleDateFormat("hh_mm_ss").format(new Date()) + ".png");
		System.out.println(path);
		TakesScreenshot screenshot = (TakesScreenshot) driver;
		File snapshotFile = screenshot.getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(snapshotFile, new File(path.get()));

		return path.get();

	}

}
