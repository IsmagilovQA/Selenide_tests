package Selenide_tests;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.back;

public class NavigationHelper {
    SelenideElement areasButton = $(By.linkText("Areas"));
    SelenideElement placesButton = $(By.linkText("Places"));
    SelenideElement spacesButton = $(By.linkText("Spaces"));


    public void areas() {
        areasButton.click();
    }


    public void places() {
        placesButton.click();
    }


    public void spaces() {
        spacesButton.click();
    }

    public void goBack() {
        back();
    }
}
