package Selenide_tests;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.back;

public class NavigationHelper {
    //private SelenideElement areasButton = $(By.linkText("Areas"));
    //private SelenideElement placesButton = $(By.linkText("Places"));
    //private SelenideElement spacesButton = $(By.linkText("Spaces"));


    public void areas() {
        $(By.linkText("Areas")).click();
    }


    public void places() {
        $(By.linkText("Places")).click();
    }


    public void spaces() {
        $(By.linkText("Spaces")).click();
    }

    public void goBack() {
        back();
    }
}
