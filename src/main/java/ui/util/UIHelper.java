package ui.util;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ui.page_object.HomePage;

import java.time.Duration;

public class UIHelper {

    private static final String url = "https://openweathermap.org/";

    public static HomePage navigateToHomepage(WebDriver driver) {
        driver.get(url);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        return new HomePage(driver);
    }

    public static void waitForElementToBeLoaded(WebDriver driver, WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public static void waitForSpinnerToDisappear(WebDriver driver) {
        String spinnerXpath = "//*[@aria-label='Loading']";
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(spinnerXpath)));
    }

}
