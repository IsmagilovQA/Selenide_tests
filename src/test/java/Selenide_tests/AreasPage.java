package Selenide_tests;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class AreasPage {
    ElementsCollection areasList = $$("tbody tr");
    ElementsCollection viewList = $$("tbody td:nth-of-type(7)");
    SelenideElement mapID = $(By.id("map_id"));
    SelenideElement hovedinngang = $(By.linkText("Hovedinngang"));
    SelenideElement moterom_PS439 = $(By.linkText("Møterom PS439"));
    SelenideElement titleOnView = $("[class='display-4']");


    public ElementsCollection areasList() {
        return areasList;
    }


    public AreasPage clickOnView(int index) {
        viewList.get(index).find(By.linkText("View")).shouldBe(visible).click();
        return this;
    }


    public String getCurrentURL() {
        return WebDriverRunner.url();
    }
}
