package Selenide_tests;

import com.codeborne.selenide.ElementsCollection;

import static com.codeborne.selenide.Selenide.$$;

public class SpacesPage {
    ElementsCollection spacesList = $$("tbody tr");
}
