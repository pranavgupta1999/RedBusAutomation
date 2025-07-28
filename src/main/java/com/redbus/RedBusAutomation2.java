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

public class RedBusAutomation2 {

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

		selectLocation(wd, wait, "Mumbai"); // For Location
		selectLocation(wd, wait, "Pune"); // To Location

		By searchButtonLocator = By.xpath("//button[contains(@class, \"searchButtonWrapper\")]");
		WebElement searchButton = wait.until(ExpectedConditions.elementToBeClickable(searchButtonLocator));
		searchButton.click();

		By primoButtonLocator = By.xpath("//div[contains(text(), \"Primo\")]");
		WebElement primosearchButton = wait.until(ExpectedConditions.elementToBeClickable(primoButtonLocator));
		primosearchButton.click();

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
		By BusesNmaeLocator = By.xpath("//div[contains(@class,\"travelsName\")]");
		List<WebElement> rowList = wait
				.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(tuppleWrapperLocator));
		System.out.println("Total Number of Buses " + rowList.size());
		for (WebElement row : rowList) {
			System.out.println(row.findElement(BusesNmaeLocator).getText());
		}

		// How to scrollin selenium WebDriver
		JavascriptExecutor js = (JavascriptExecutor) wd;
		js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth'})", rowList.get(rowList.size() - 3));

		Thread.sleep(5000);
		List<WebElement> newRowList = wait
				.until(ExpectedConditions.numberOfElementsToBeMoreThan(tuppleWrapperLocator, rowList.size()));
		System.out.println("Total Number of Buses " + newRowList.size());
	}

	private static List<WebElement> selectLocation(WebDriver wd, WebDriverWait wait, String locationData) {
		WebElement searchTextBox = wd.switchTo().activeElement(); // give me that textbox
		searchTextBox.sendKeys(locationData);

		By searchCategoryLoactor = By.xpath("//div[contains(@class,'searchCategory')]");
		List<WebElement> searchList = wait
				.until(ExpectedConditions.numberOfElementsToBeMoreThan(searchCategoryLoactor, 2));
		System.out.println(searchList.size());

		WebElement locationSearchResult = searchList.get(0);

		// Channing of Webelement
		By locationNameLocator = By.xpath(".//div[contains(@class,\"listHeader\")]");

		List<WebElement> locationList = locationSearchResult.findElements(locationNameLocator);
		System.out.println(locationList.size());

		for (WebElement location : locationList) {
			String name = location.getText();

			if (name.equalsIgnoreCase(locationData)) {
				location.click();
				break;
			}
		}
		
		return searchList;
	}

}
