# **viesure-automation project**

## Technologies

- Java 1.8
- Maven 3.8.1
- TestNG 6.9.8
- Cucumber 7.8.1
- Selenium 4.5
- RestAssured 5.2.0
- OkHttp 2.7.5
- Slf4j 1.7.5

## Execution

execute all tests

`mvn test`

execute all api tests

`mvn clean test -Dcucumber.filter.tags="@api"`

execute all ui tests

`mvn clean test -Dcucumber.filter.tags="@ui"`

## Reporting

All the test execution reports can be found here:

`target/surefire-reports/`


### Latest API tests
![API test results](/test-reports/api.png "API test results")

### Latest UI tests
![UItest results](/test-reports/ui.png "UI test results")


