package ui.page_object;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import ui.util.UIHelper;

import java.util.List;
import java.util.OptionalInt;
import java.util.stream.IntStream;

public class ResultPage extends AbstractPage {

    @FindBy(xpath = "//*[@id='nav-website']")
    private WebElement header;


    @FindBy(xpath = "//div[@id='forecast_list_ul']//tr")
    private List<WebElement> resultList;


    private WebDriver driver;

    public ResultPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        UIHelper.waitForElementToBeLoaded(driver, header);
    }

    public HomePage clickOnResultByName(String cityName) {
        OptionalInt indexOpt = IntStream.range(0, resultList.size())
                .filter(i -> resultList.get(i).getText().startsWith(cityName)).findFirst();
        WebElement element = resultList.get(indexOpt.getAsInt());
        element.findElement(By.xpath(".//a")).click();
        return new HomePage(driver);
    }

}
