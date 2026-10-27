# SauceDemo Automation (Selenium + Java + TestNG)

UI test framework for https://www.saucedemo.com/ using the Page Object Model.

## Requirements
- JDK 17+
- Maven 3.8+
- Chrome (default), Firefox or Edge installed. Selenium Manager downloads the driver automatically.

## Run
```bash
mvn clean test                                   # Chrome, headed
mvn clean test -Dheadless=true                   # headless
mvn clean test -Dbrowser=firefox                 # firefox | edge | chrome
mvn clean test -Dtest=LoginTests                 # single class (remove suiteXmlFile if needed)
```

Reports: `target/surefire-reports`. Failure screenshots: `target/screenshots`.

## Structure
```
src/main/java/com/saucedemo
  config/Config.java          reads config.properties, supports -D overrides
  driver/DriverFactory.java   ThreadLocal WebDriver (chrome/firefox/edge)
  pages/                      BasePage, LoginPage, InventoryPage, CartPage, CheckoutPage
src/test/java/com/saucedemo/tests
  BaseTest.java               setup/teardown + screenshot on failure
  LoginTests, InventoryTests, CartTests, CheckoutTests
src/test/resources
  config.properties, testng.xml
```

## CI (GitLab)
`.gitlab-ci.yml` runs the suite headless in a `maven:3.9-eclipse-temurin-17` container with Chrome installed.
Pipelines run on merge requests, the default branch, schedules and manual web triggers.
JUnit results appear in the pipeline's **Tests** tab; reports and failure screenshots are kept as job artifacts for 1 week.
Override the browser per run with the `BROWSER` CI variable.
