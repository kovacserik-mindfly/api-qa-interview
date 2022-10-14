package ui.page_object;

import org.openqa.selenium.WebDriver;
import ui.util.UIHelper;

public abstract class AbstractPage {
    protected WebDriver driver;

    public void waitUntilPageIsLoaded() {
        UIHelper.waitForSpinnerToDisappear(driver);
    }
}
