package ui.page_object;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import ui.util.UIHelper;

public class HomePage extends AbstractPage {

    @FindBy(xpath = "//*[@id='nav-website']")
    private WebElement header;


    @FindBy(xpath = "//button[text()='Allow all']")
    private WebElement allowAllButton;

    @FindBy(xpath = "//div[@id='desktop-menu']//input[@name='q']")
    private WebElement inputPlaceholderText;


    public HomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        UIHelper.waitForElementToBeLoaded(driver, header);
    }


    public HomePage clickOnAllowCookieButton() {
        UIHelper.waitForElementToBeLoaded(driver, allowAllButton);
        allowAllButton.click();
        return new HomePage(driver);
    }

    public void setInputPlaceholderText(String cityName) {
        UIHelper.waitForElementToBeLoaded(driver, inputPlaceholderText);
        inputPlaceholderText.sendKeys(cityName);
    }

    public String getPlaceholderText() {
        UIHelper.waitForElementToBeLoaded(driver, inputPlaceholderText);
        return inputPlaceholderText.getAttribute("placeholder");
    }
}
