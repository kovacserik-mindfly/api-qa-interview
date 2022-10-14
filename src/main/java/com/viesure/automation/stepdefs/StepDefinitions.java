package com.viesure.automation.stepdefs;

import api.APIHelper;
import api.util.FileUtil;
import api.util.NumberUtil;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.commons.lang3.math.NumberUtils;
import org.everit.json.schema.Schema;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import ui.page_object.CityPage;
import ui.page_object.HomePage;
import ui.page_object.ResultPage;
import ui.util.UIHelper;

import java.time.LocalDate;
import java.time.Month;
import java.util.Locale;

import static io.restassured.RestAssured.given;

public class StepDefinitions {

    Logger logger = LoggerFactory.getLogger(StepDefinitions.class);
    protected final String backendUrl = "https://backend-interview.tools.gcp.viesure.io";
    protected Response response;
    protected WebDriver driver;

    @Before
    public void setup() {
        response = null;
    }


    @Given("Launch the browser")
    public void launchTheBrowser() {
        logger.info("Start launching browser");
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        logger.info("Browser launched");
    }

    @When("I navigate to the homepage")
    public void iNavigateToTheHomepage() {
        logger.info("Nativate to home page");
        UIHelper.navigateToHomepage(driver);
        logger.info("Navigated to Homapage");
    }

    @Then("I wait until the homepage is loaded")
    public void iWaitUntilTheHomepageIsLoaded() {
        HomePage homePage = new HomePage(driver);
        logger.info("Wait until page is loaded...");
        homePage.waitUntilPageIsLoaded();
        logger.info("HomePage loaded");
        homePage.clickOnAllowCookieButton();
        logger.info("Clicked on allow cookies");
    }

    @And("The main page's search field contains correct placeholder text")
    public void theMainPageSearchFieldContainsCorrectPlaceholderText() {
        HomePage homePage = new HomePage(driver);
        String placeholderText = homePage.getPlaceholderText();
        logger.info("PlaceholderText from the UI: {}", placeholderText);
        String expected = "Weather in your city";
        Assert.assertEquals(placeholderText, expected, "Placeholder text must match!");
    }

    @Then("I search for {string}")
    public void searchForCity(String cityName) {
        HomePage homePage = new HomePage(driver);
        logger.info("Type into placeholder text: {}", cityName);
        homePage.setInputPlaceholderText(cityName);
        driver.switchTo().activeElement().sendKeys(Keys.ENTER);
        logger.info("Enter was hit");
    }

    @Then("I select {string} from the list")
    public void selectCityFromTheList(String cityName) {
        ResultPage resultPage = new ResultPage(driver);
        logger.info("Select city from the list: {}", cityName);
        resultPage.clickOnResultByName(cityName);

    }

    @And("I verify the right data on the screen for city: {string}")
    public void verifyDataOnScreen(String cityName) {
        CityPage cityPage = new CityPage(driver);
        cityPage.waitUntilPageIsLoaded();
        String dateText = cityPage.getDateText();

        // Get the month and day from the UI
        String month = dateText.split(" ")[0];
        String day = dateText.split(" ")[1].replaceAll(",", "");
        logger.info("Month from the UI: {}", month);
        logger.info("Day from the UI: {}", day);
        // Check the city name
        Assert.assertEquals(cityPage.getCityText(), cityName, "City name must be equal");

        // Get the date from Java
        LocalDate currentdate = LocalDate.now();
        int currentDay = currentdate.getDayOfMonth();
        Month currentMonth = currentdate.getMonth();
        logger.info("Current month: {}", currentDay);
        logger.info("Current day: {}", currentMonth);

        // Check the date
        Assert.assertEquals(currentDay, Integer.parseInt(day), "Days must be equal");
        Assert.assertTrue(currentMonth.toString().toLowerCase(Locale.ROOT).contains(month.toLowerCase(Locale.ROOT)), "Months must be equal");
    }


    @Given("the Fahrenheit temperature is set to {int}")
    public void setFahrenheitTemperature(int temperature) {
        boolean updateSuccessful = new APIHelper().updateTemperature(temperature);
        logger.info("Setting fahrenheit update was: {}", updateSuccessful);
    }

    @Given("the conditionId is set to {int}")
    public void setConditionId(int conditionId) {
        boolean updateSuccessful = new APIHelper().updateCondition(conditionId);
        logger.info("Setting condition id update was: {} to {}", updateSuccessful, conditionId);
    }

    @When("I get the current weather data")
    public void getCurrentWeatherData() {
        RestAssured.baseURI = backendUrl;
        response = given()
                .contentType(ContentType.JSON)
                .when()
                .get("/weather")
                .then()
                .extract().response();
        logger.info("getCurrentWeatherData response: {}", response.asPrettyString());
    }

    @Then("The weather should be {string}")
    public void theWeatherShouldBeResult(String rule) {
        String expectedDescription = String.format("The weather is %s", rule);
        logger.info("Expected description: {}", expectedDescription);
        Assert.assertEquals(200, response.statusCode(), "Status code must be 200!");
        Assert.assertEquals(response.jsonPath().getString("description"), expectedDescription);
    }

    @Then("The condition should be {string}")
    public void theConditionShouldBeResult(String condition) {
        Assert.assertEquals(200, response.statusCode(), "Status code must be 200!");
        Assert.assertEquals(response.jsonPath().getString("condition"), condition, "Conditions must be equal");
    }

    @Then("The image icon should be {string}")
    public void theImageIconShouldBeResult(String condition) {
        String expectedIcon = condition + ".png";
        logger.info("Expected icon: {}", expectedIcon);
        Assert.assertEquals(200, response.statusCode(), "Status code must be 200!");
        Assert.assertEquals(response.jsonPath().getString("icon"), expectedIcon, "Icons must be equal");
    }

    @Then("The city field would give a fixed city name as a string")
    public void theCityFieldWouldGiveAFixedCityNameAsAString() {
        String expectedCity = "Vienna";
        logger.info("Expected city: {}", expectedCity);
        Assert.assertEquals(200, response.statusCode(), "Status code must be 200!");
        Assert.assertEquals(response.jsonPath().getString("city"), expectedCity);
    }

    @Then("The description field is a fixed text with a suffix")
    public void theDescriptionFieldIsAFixedTextWithASuffix() {
        String prefix = "The weather is";
        Assert.assertEquals(200, response.statusCode(), "Status code must be 200!");
        Assert.assertTrue(response.jsonPath().getString("description").startsWith(prefix), "Description must start with the prefix!");
    }

    @Then("The weather object contains the temperature in both Fahrenheit and Celsius")
    public void theWeatherObjectContainsTheTemperatureInBothFahrenheitAndCelsius() {
        Assert.assertEquals(200, response.statusCode(), "Status code must be 200!");
        Assert.assertTrue(response.jsonPath().getMap("weather").keySet().contains("tempInFahrenheit"), "Weather must contain tempInFahrenheit");
        Assert.assertTrue(response.jsonPath().getMap("weather").keySet().contains("tempInCelsius"), "Weather must contain tempInFahrenheit");
        Assert.assertEquals(response.jsonPath().getMap("weather").keySet().size(), 2, "There must be 2 elements in weather object!");

    }

    @Then("The {string} field contains the current temperature in Fahrenheits")
    public void fieldShouldContains(String field) {
        String currentFahrenheit = response.jsonPath().getString("weather." + field);
        logger.info("Current fahrenheit: {}", currentFahrenheit);
        Assert.assertEquals(200, response.statusCode(), "Status code must be 200!");

        // Check it's a number
        Assert.assertTrue(NumberUtils.isCreatable(currentFahrenheit), "It must be a number!");
    }


    @Then("The {string} field contains the current temperature in Celsius")
    public void theFieldFieldContainsTheCurrentTemperatureInCelsius(String field) {
        String currentCelsius = response.jsonPath().getString("weather." + field);
        logger.info("Current celsuis: {}", currentCelsius);
        Assert.assertEquals(200, response.statusCode(), "Status code must be 200!");

        // Check it's a number
        Assert.assertTrue(NumberUtils.isCreatable(currentCelsius), "It must be a number!");

    }

    @Then("The tempInCelsius Calculated from the tempInFahrenheit field")
    public void theTempInCelsiusCalculatedFromTheTempInFahrenheitField() {
        int currentFahrenheit = response.jsonPath().getInt("weather.tempInFahrenheit");
        int currentCelsius = response.jsonPath().getInt("weather.tempInCelsius");
        int expectedNumber = NumberUtil.fahrenheitToCelsius(currentFahrenheit);

        logger.info("currentFahrenheit: {}", currentFahrenheit);
        logger.info("currentCelsius: {}", currentCelsius);
        logger.info("expectedNumber: {}", expectedNumber);

        // Check the data
        Assert.assertEquals(currentCelsius, expectedNumber, "Current celsius must match to expectedNumber");
    }

    @And("The Celsius value should be {int}")
    public void theCeliusValueShouldBeCelsius(int expectedCelsiusValue) {
        int currentCelsius = response.jsonPath().getInt("weather.tempInCelsius");

        logger.info("currentCelsius: {}", currentCelsius);
        logger.info("expectedNumber: {}", expectedCelsiusValue);

        // Check the data
        Assert.assertEquals(currentCelsius, expectedCelsiusValue, "Current celsius must match to expectedNumber");

    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.close();
        }
        response = null;
    }

    @Then("The response should match to the schema")
    public void theResponseShouldMatchToTheSchema() {

        // load the schema from the file
        JSONObject jsonObject = new JSONObject(new JSONTokener(FileUtil.getSchemaStream()));
        Schema schema = SchemaLoader.load(jsonObject);

        JSONObject jsonFromResponse = new JSONObject(response.asString());

        // Do the validation using the built-in validate function
        schema.validate(jsonFromResponse);
    }
}
