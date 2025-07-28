package com.redbus;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RedBusAutomation {

	public static void main(String[] args) throws InterruptedException {

		// I want to launch the chrome browser
		ChromeOptions chromeOptions = new ChromeOptions();
		chromeOptions.addArguments("--start-maximized");

		WebDriver wd = new ChromeDriver(chromeOptions);

		WebDriverWait wait = new WebDriverWait(wd, Duration.ofSeconds(30));

		wd.get("https://www.redbus.in/");

		By sourceButtonLocator = By.xpath("//div[contains(@class, \"srcDestWrapper\") and @role=\"button\"]");
//		WebElement sourceButton = wd.findElement(sourceButtonLocator);
		WebElement sourceButton = wait.until(ExpectedConditions.visibilityOfElementLocated(sourceButtonLocator));
		sourceButton.click();

		By searchLocationSuggestion = By.xpath("//div[contains(@class,'searchSuggestionWrapper')]");
		wait.until(ExpectedConditions.visibilityOfElementLocated(searchLocationSuggestion));

		WebElement searchTextBox = wd.switchTo().activeElement(); // give me that textbox
		searchTextBox.sendKeys("Mumbai");

		By searchCategoryLoactor = By.xpath("//div[contains(@class,'searchCategory')]");
		List<WebElement> searchList = wait
				.until(ExpectedConditions.numberOfElementsToBeMoreThan(searchCategoryLoactor, 2));
		System.out.println(searchList.size());

		WebElement locationSearchResult = searchList.get(0);

//      Channing of Webelement
		By locationNameLocator = By.xpath(".//div[contains(@class,\"listHeader\")]");

		List<WebElement> locationList = locationSearchResult.findElements(locationNameLocator);
		System.out.println(locationList.size());

		for (WebElement location : locationList) {
			String name = location.getText();

			if (name.equalsIgnoreCase("Mumbai")) {
				location.click();
				break;
			}
		}

		// Focus on To section

		WebElement totextBox = wd.switchTo().activeElement();
		totextBox.sendKeys("Pune");

		By tosearchCategoryLoactor = By.xpath("//div[contains(@class,'searchCategory')]");
		List<WebElement> tosearchList = wait
				.until(ExpectedConditions.numberOfElementsToBeMoreThan(tosearchCategoryLoactor, 2));
		System.out.println(searchList.size());

		WebElement tolocationSearchResult = tosearchList.get(0);

		// Channing of Webelement
		By tolocationNameLocator = By.xpath(".//div[contains(@class,\"listHeader\")]");

		List<WebElement> tolocationList = tolocationSearchResult.findElements(tolocationNameLocator);

		for (WebElement tolocation : tolocationList) {
			System.out.println(tolocation.getText());

			if (tolocation.getText().equalsIgnoreCase("Pune"))
				;
			tolocation.click();
			break;

		}

		// Click on search bus button

		By searchButtonLocator = By.xpath("//button[contains(@class, \"searchButtonWrapper\")]");
		WebElement searchButton = wait.until(ExpectedConditions.elementToBeClickable(searchButtonLocator));
		searchButton.click();

		By primoButtonLocator = By.xpath("//div[contains(text(), \"Primo\")]");
		WebElement primosearchButton = wait.until(ExpectedConditions.elementToBeClickable(primoButtonLocator));
		primosearchButton.click();

		Thread.sleep(1000);

		By TimelableButtonLocator = By.xpath("//div[contains(@class, 'label') and contains(text(), '18:00-24:00')]");
		WebElement timeprimosearchButton = wait.until(ExpectedConditions.elementToBeClickable(TimelableButtonLocator));
		timeprimosearchButton.click();

		// Search total buses
		By subTitleLocator = By.xpath("//span[contains(@class, 'subtitle')]");
		WebElement subTitle = null;

		if (wait.until(ExpectedConditions.textToBePresentInElementLocated(subTitleLocator, "buses"))) {
			subTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(subTitleLocator));
		}
		System.out.println(subTitle.getText());

		// Print all the buses

		By tuppleWrapperLocator = By.xpath("//li[contains(@class,'tupleWrapper')]");
		By BusesNmaeLocator = By.xpath(".//div[contains(@class,\"travelsName\")]");
//		List<WebElement> rowList = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(tuppleWrapperLocator));
//		System.out.println("Total Number of Buses " + rowList.size()); // 10
//		for (WebElement row : rowList) {
//
//			System.out.println(row.findElement(BusesNmaeLocator).getText());// chainning of WebElemnt
//		} // First 10 buses name will printed

		// How to scrollin selenium WebDriver
		JavascriptExecutor js = (JavascriptExecutor) wd;

//		List<WebElement> newRowList = wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(tuppleWrapperLocator, rowList.size()));
//		System.out.println("Total Number of Buses " + newRowList.size());

		while (true) { // Lazy Loading
			// Get the rows from the pages
			List<WebElement> rowList = wait
					.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(tuppleWrapperLocator));
			List<WebElement> endOfList = wd.findElements(By.xpath("//span[contains(text(),\"End of list\")]"));

			if (!endOfList.isEmpty()) {
				break; // loop exit condition from the while loop
			}

			js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth'})", rowList.get(rowList.size() - 3));
		}

		List<WebElement> rowList = wait
				.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(tuppleWrapperLocator));
		for (WebElement row : rowList) {
			String busName = row.findElement(BusesNmaeLocator).getText();
			System.out.println(busName);
		}
		System.out.println("Total Number of buses loaded with Primo and Evening Filter " + rowList.size());

	}

}
