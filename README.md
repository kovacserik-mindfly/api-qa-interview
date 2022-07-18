# backend-qa-interview


## Weather 'application' overview

You are assigned to a team which is developing weather related application's MVP backend. 
Your task is to create automated tests for API that the application will use.

The app will use one API, that tells the weather for a fixed city for the MVP. (Condition, description, temperatures ...etc.).

The API will return the following fields: `city`, `condition`, `icon`, `description` `conditionId` `tempInFahrenheit` and `tempInCelsius`


## Weather backend's acceptance criteria-s

* The **city field** would give a fixed city name as a string
* The **conditionID field** gives an ID of the current condition
  
  | ID   | Value   |
  | ---- | ------  |
  | 1    | clear   |
  | 2    | windy   |
  | 3    | mist    |
  | 4    | drizzle |
  | 5    | dust    |

* The **condition field** would tell the weather condition as a string based on condition id
  * Can have the following values `clear`, `windy`, `mist`, `drizzle` and `dust`
* The **icon field** gives a `png` type image (string) that correlates (same as) the current condition
* The **weather object** contains the temperature in both Fahrenheit and Celsius
  * The **tempInFahrenheit field** contains the current temperature in Fahrenheits
    * Should have 0 digits
  * The **tempInCelsius field** contains the current remperature in Celsius
    * Should have 0 digits
    * Calculated from the tempInFahrenheit field
    * Uses normal rounding rules
* The **description field** returns a description text of the current weather
  * The description is a fixed text with a suffix
  * The description suffix is calculated based on the temperature in celsius
  * Calculation rules:
  
| Rule            | Description   |
| -------------   | ------------- |
| celsius <= 0    | freezing      |
| celsius < 10    | cold          |
| celsius < 20    | mild          |
| celsius < 25    | warm          |
| celsius >= 25   | hot           |


### The API:
**GET .../weather**
* You can fetch the endpoint through  `https://backend-interview.tools.gcp.viesure.io/weather` with the following data structure:
```curl
curl -X 'GET' \
  'https://backend-interview.tools.gcp.viesure.io/weather' \
  -H 'accept: application/json'
```
```json
{
  "city"        : "Vienna",
  "condition"   : "clear",
  "icon"        : "clear.PNG",
  "conditionId" : 1,
  "description" : "The weather is mild",
  "weather": {
    "tempInFahrenheit" : 60,
    "tempInCelsius"    : 15
  }
}
```

### Helper API-s

You are also provided 2 helper API-s to be able to set the states for the GET weather API

#### PUT .../weather/temp

You can set the Fahrenheit temperature with this API, by sending an Integer
* You can call the endpoint through  `https://backend-interview.tools.gcp.viesure.io/weather/temp`
```cURL
curl -X 'PUT' \
  'https://backend-interview.tools.gcp.viesure.io/weather/temp' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "tempInFahrenheit": 17
}'
```
#### PUT .../weather/condition

You can set the Condition ID with this API, by sending an Integer
* You can call the endpoint through  `https://backend-interview.tools.gcp.viesure.io/weather/condition`
```cURL
curl -X 'PUT' \
  'https://backend-interview.tools.gcp.viesure.io/weather/condition' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "condition": 1
}'
```


## Your task
You need to implement a test automation for the weather API (see above) using Cucumber and Selenium
* Define Cucumber scenarios
* Use the following farmeworks/technologies within the automation project:
    * Java
    * Selenium
    * Maven and POM
    * BDD and Executed cucumber tests.
    * Reporting framework of your choice.
* Include the final automated test result/report in the readme.
* Open a pull request containing automation source code and an informative readme.

## Challenge

To also make use of selenium, test a few fields from `https://openweathermap.org/`

#### Task1
Verify the main page's title field text

![Title](./resources/openweather_title.png)

#### Task2
* Search for `Sydney`, and select `Sydney, AU` from the list
* Verify the selected city' title
* Verify the date
* Verify the time


![Title](./resources/openweather_search.png)


## Next Step
Our QA and Development team will review your task carefully and contact you as soon as possible.
