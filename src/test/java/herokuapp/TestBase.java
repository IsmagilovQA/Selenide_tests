package herokuapp;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.*;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.util.Arrays;

public class TestBase {

    // Setup Selenoid from scretch                              https://www.youtube.com/watch?v=mxHqCMhpnaE&t=7s
                                                                //https://aerokube.com/selenoid/latest/#_quick_start_guide

    // Run Selenoid + see live browser screen                  ./cm selenoid start --vnc --force
    // Stop Selenoid                                           ./cm selenoid stop

    // Run Selenoid-UI                                         ./cm selenoid-ui start --force
    // Stop Selenoid-ui                                        ./cm selenoid-ui stop

    // Open web page for displaying selenoid-UI                 http://localhost:8080

    // Status                                                  ./cm selenoid status
    // Help with all commands                                  ./cm selenoid --help


    // Lists running containers      docker ps -a
    // List of images                docker images

    // Stop container with ID        docker stop 'my_container ID'
    // Remove container with ID      docker rm 'my_container ID'

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

    // STEP INSTRUCTIONS:
    /*
    1. Run docker
    2. Run Selenoid VNC - ./cm selenoid start --vnc --force
    3. Run Selenoid UI  - ./cm selenoid-ui start --force
    4. Open browser  -    http://localhost:8080
    5. Make sure both indicators are green
    6. Add Selenoid conficurations to test (@Before) - see the code below in @BeforeSuite
    7. Run tests
    8. After work:
    9. Display lists with running containers  -  docker ps -a
    10. Stop containers (both Selenoid and Selenoid-ui containres) -  docker stop 'my_container ID'
    11. Remove both containers -  docker rm 'my_container ID'
    12. Make sure no containers any more  -  docker ps -a
    13. Stop Selenoid-ui  -  ./cm selenoid-ui stop
    14. Stop Selenoid -  ./cm selenoid stop
    15. Clean Selenoid configuration -  ./cm selenoid cleanup
    16. Close docker
     */

    Logger logger = LoggerFactory.getLogger(TestBase.class);

    @BeforeSuite
    public void setup() throws MalformedURLException {

        /*
        // Selenoid conficurations
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("enableVideo", false);
        capabilities.setCapability("enableVNC", true);
        Configuration.browserCapabilities = capabilities;
        Configuration.remote = "http://localhost:4444/wd/hub";
        Configuration.browser = "chrome";
        Configuration.browserSize = "1280x1024";
        */

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
