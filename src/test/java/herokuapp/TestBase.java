package herokuapp;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.util.Arrays;

public class TestBase {

    // Run Selenoid + see live browser screen                  ./cm selenoid start --vnc --force
    // Stop Selenoid                                           ./cm selenoid stop

    // Run Selenoid-UI                                         ./cm selenoid-ui start --force)
    // Status                                                  ./cm selenoid status
    // Help with all commands                                  ./cm selenoid --help

    // ./cm selenoid-ui stop

    // Lists running containers      docker ps -a
    // List of images                docker images

    // Stop container with ID        docker stop my_container
    // Remove container with ID      docker rm my_container
    // Help                          docker --help

    // check port   lsof -i:4444

    /*
    docker stop $(docker ps -a -q);
    docker rm $(docker ps -a -q);
    docker volume rm $(docker volume ls -qf dangling=true)
    docker network rm $(docker network ls -q)
    sudo lsof -nP | grep LISTEN
    sudo kill -9 1548
     */

    Logger logger = LoggerFactory.getLogger(TestBase.class);

    @BeforeSuite
    public void setup() throws MalformedURLException {

        // Selenoid conficurations
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("enableVideo", false);
        capabilities.setCapability("enableVNC", true);
        Configuration.browserCapabilities = capabilities;
        Configuration.remote = "http://localhost:4444/wd/hub";
        Configuration.browser = "chrome";
        Configuration.browserSize = "1280x1024";

        SelenideLogger.addListener("allure", new AllureSelenide()); // attach screenshots to allure report if test failed
        Configuration.timeout = 4000;
        //Configuration.browser = "chrome"; // Chrome in selenide 5.0.0 is a default browser
        Configuration.headless = false;
        //Configuration.startMaximized = false;
        //Configuration.browserSize = "1366x768"; // most popular size
        Configuration.holdBrowserOpen = false;
        //Configuration.screenshots = true; // it's need if you want to have screenshots in particular folder
        //Configuration.reportsFolder = "build/allure-results"; // particular folder
        Configuration.baseUrl = "http://the-internet.herokuapp.com/";
        Configuration.fastSetValue = true; //fill in with JavaScript (inserting the whole text)
        //Configuration.proxyEnabled = true;
        System.setProperty("java.util.logging.SimpleFormatter.format", "%1$tT %4$s %5$s%6$s%n"); // for pretty logging in console

    }

    @AfterSuite(alwaysRun = true)
    public void testShutDown() {
        SelenideLogger.removeListener("allure"); // attach screenshots to allure report if test failed
        WebDriverRunner.clearBrowserCache();
        Selenide.clearBrowserCookies();
        Selenide.close();
    }

    @BeforeMethod(alwaysRun = true, description = "always run needed not to skip this method if test failed")
    public void logTestStart(Method m, Object[] p) {
        logger.info("Start test - " + m.getName() + " with parameters: " + Arrays.asList(p));

    }

    @AfterMethod(alwaysRun = true)
    public void logTestStop(Method m, Object[] p) {
        logger.info("Stop test - " + m.getName() + " with parameters: " + Arrays.asList(p));

    }
}
