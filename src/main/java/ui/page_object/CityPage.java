package ui.page_object;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import ui.util.UIHelper;

public class CityPage extends AbstractPage {

    @FindBy(xpath = "//span[@class='orange-text']")
    private WebElement dateText;


    @FindBy(xpath = "//span[@class='orange-text']/following-sibling::h2")
    private WebElement cityText;

    public CityPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        UIHelper.waitForElementToBeLoaded(driver, cityText);
    }

    public String getCityText() {
        UIHelper.waitForElementToBeLoaded(driver, cityText);
        return cityText.getText();
    }

    public String getDateText() {
        UIHelper.waitForElementToBeLoaded(driver, dateText);
        return dateText.getText();
    }

}
