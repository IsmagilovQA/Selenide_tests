package herokuapp;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

public class TestBase {

    @BeforeSuite
    public void setup() {
        Configuration.timeout = 4000;
        Configuration.browser = "chrome";
        Configuration.headless = true;
        Configuration.startMaximized = false;
        Configuration.holdBrowserOpen = false;
        Configuration.screenshots = true;
        Configuration.reportsFolder = "build/reports";
        Configuration.baseUrl = "http://the-internet.herokuapp.com/";
        Configuration.fastSetValue = true; //fill in with JavaScript
        Configuration.proxyEnabled = true;

    }

    @AfterSuite
    public void testShutDown() {
        WebDriverRunner.clearBrowserCache();
    }
}
