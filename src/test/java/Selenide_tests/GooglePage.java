package Selenide_tests;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class GooglePage {
    SelenideElement linkText = $(By.linkText("Что такое Selenide"));
    SelenideElement loginButton = $(By.linkText("LOGIN"));
    SelenideElement emailField = $("#email");
    SelenideElement passwordField = $("#password");
    ElementsCollection results = $$(".srg .g");



    public GooglePage openPage() {
        //Selenide.open("https://www.google.com");
        //Selenide.open("https://saucelabs.com/resources/articles/selenium-tips-css-selectors");
        Selenide.open("https://oslomet.nextsignal.no/");
        return this;
    }

    public void searchFor(String text) {
        $(By.name("q")).val(text).pressEnter();
    }

    public ElementsCollection results() {
        return results;
    }

    public void clickOnLinkText() {
        linkText.click();
    }

    public ElementsCollection resultSet() {
        return $$(".blog_post_content ul:nth-child(11) li");
    }

    public void dropdown_Support_Training() {
        $(By.linkText("SUPPORT")).click();
        $(By.linkText("TRAINING")).click();
    }

    public void loginOslomet() {
        loginButton.click();
        emailField.val("larscyril@nextsignal.no");
        passwordField.val("Yqt-U9P-NSN-do6").pressEnter();
    }
}
