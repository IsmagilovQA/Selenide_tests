package Selenide_tests;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Condition.empty;
import static com.codeborne.selenide.Selenide.back;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class Tests extends TestBase {
    /*

    @Test(description = "Test search results on Google site")
    public void userCanSearch() {
        googlePage.openPage().searchFor("selenide");
        googlePage.results().shouldHave(size(10));
        googlePage.results().get(0).shouldHave(text("Selenide: лаконичные и стабильные UI тесты на Java"));
    }

    @Test(description = "One more test")
    public void newTest() {
        googlePage.openPage().searchFor("selenide");
        googlePage.clickOnLinkText();
        googlePage.resultSet().get(2).shouldHave(text("Управление жизнедеятельностью браузера"));
        System.out.println(googlePage.resultSet().size());

        googlePage.openPage();
        googlePage.dropdown_Support_Training();
    }
*/

    @BeforeClass(description = "log in into CMS")
    public void loginToCMS() {
        googlePage.openPage();
        googlePage.loginOslomet();
        homePage.textOnHomescreen.shouldHave(text("You are logged in!"));
    }

    @BeforeMethod(description = "navigate to 'Areas' section")
    public void preconditions() {
        goTo.areas();
    }

    @Test(description = "check Areas list")
    public void areasList() {
        areaPage.areasList().shouldHaveSize(82);
        areaPage.areasList.get(3).shouldHave(text("PH131 Auditorium"));
        areaPage.areasList.last().shouldHave(text("Møterom PS439"));
        areaPage.areasList.get(5).shouldBe(visible);
        String actualURL = areaPage.clickOnView(2).getCurrentURL();
        assertThat(actualURL, is("https://oslomet.nextsignal.no/areas/3/history"));
    }

    @Test
    public void openArea() {
        areaPage.hovedinngang.click();
        areaPage.mapID.shouldBe(not(empty)).shouldHave(value("2"));
        back();
        areaPage.moterom_PS439.click();
    }

    @Test
    public void clickAnyView() {
        areaPage.clickOnView(3).titleOnView.shouldHave(text("History for area 4"));
        goTo.goBack();
        areaPage.clickOnView(79).titleOnView.shouldHave(text("No history"));
        goTo.goBack();
        goTo.places();
        placesPage.placesList.shouldHaveSize(1);
        placesPage.p35.click();
        placesPage.displayable.shouldNotBe(checked).setSelected(true);
        placesPage.voiceover_field
                .shouldBe(empty)
                .shouldHave(attribute("placeholder", "voiceover"));
        goTo.spaces();
        spacesPage.spacesList.size();

        //t

    }

}
