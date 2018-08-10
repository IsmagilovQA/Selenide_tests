package Selenide_tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

public class TestBase {
    public AreasPage areaPage;
    public NavigationHelper goTo;
    public HomePage homePage;
    public GooglePage googlePage;
    public PlacesPage placesPage;
    public SpacesPage spacesPage;


    @BeforeSuite
    public void setup() {
        Configuration.timeout = 4000;
        Configuration.browser = "chrome";
        Configuration.headless = false;
        Configuration.startMaximized = false;
        Configuration.holdBrowserOpen = false;
        Configuration.screenshots = false;
        //Configuration.baseUrl = "";
        Configuration.fastSetValue = true; //fill in with JavaScript

        googlePage = new GooglePage();
        homePage = new HomePage();
        goTo = new NavigationHelper();
        areaPage = new AreasPage();
        placesPage = new PlacesPage();
        spacesPage = new SpacesPage();

    }

    @AfterSuite
    public void testShutDown() {
        WebDriverRunner.clearBrowserCache();
    }
}
