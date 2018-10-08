package Selenide_tests;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class PlacesPage {
     ElementsCollection placesList = $$("tbody tr");
     SelenideElement p35 = $(By.linkText("P35"));
     SelenideElement displayable = $(By.id("displayable"));
     SelenideElement voiceover_field = $("#voiceover");
}
