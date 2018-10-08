package herokuapp;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

public class TestBase {

    @BeforeSuite
    public void setup() {
        SelenideLogger.addListener("allure", new AllureSelenide()); // attach screenshots to allure report if test failed
        Configuration.timeout = 4000;
        Configuration.browser = "chrome";
        Configuration.headless = true;
        Configuration.startMaximized = false;
        //Configuration.browserSize = "1024x768";
        Configuration.holdBrowserOpen = false;
        //Configuration.screenshots = true; // it's need if you want to have screenshots in particular folder
        //Configuration.reportsFolder = "build/allure-results"; // particular folder
        Configuration.baseUrl = "http://the-internet.herokuapp.com/";
        Configuration.fastSetValue = true; //fill in with JavaScript (inserting the whole text)
        //Configuration.proxyEnabled = true;

    }

    @AfterSuite
    public void testShutDown() {
        SelenideLogger.removeListener("allure"); // attach screenshots to allure report if test failed
        WebDriverRunner.clearBrowserCache();
        //Selenide.clearBrowserCookies();
        Selenide.close();
    }
}
