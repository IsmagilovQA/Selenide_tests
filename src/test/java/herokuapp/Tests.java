package herokuapp;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.apache.commons.io.FileUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.hamcrest.Matchers;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.ByText;
import static com.codeborne.selenide.Selenide.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.assertTrue;

public class Tests extends TestBase {


    @Test(description = "Basic Auth")
    public void basic_Auth() {
        open("basic_auth", "", "admin", "admin");
        $x("//div[@id='content']//p")
                .shouldHave(exactText("Congratulations! You must have the proper credentials."));
    }


    @Test(description = "Broken images using 'apache.HttpClient'")
    public void broken_images() {
        open("broken_images");
        List<SelenideElement> all_images = $$x("//div[@id='content']//img");

        for (SelenideElement image : all_images) {
            HttpClient client;
            client = HttpClientBuilder.create().build();
            HttpResponse response = null;
            try {
                response = client.execute(new HttpGet(image.getAttribute("src")));
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (response.getStatusLine().getStatusCode() != 200) {
                System.out.println("Broken image: " + image);
            }
        }
    }


    @Test(description = "click buttons with unique IDs, select elements on the table (different rows/colomns)")
    public void challenging_DOM() {
        open("challenging_dom");

        SelenideElement blue_button = $(".button:nth-of-type(1)");
        SelenideElement red_button = $(".alert");
        SelenideElement green_button = $(".success");

        blue_button.click();
        red_button.click();
        green_button.click();

        SelenideElement second_Row_3d_Colomn = $("tbody tr:nth-of-type(2) td:nth-of-type(3)");
        SelenideElement third_Row_edit_button = $("tbody tr:nth-of-type(3) [href='#edit']");
        SelenideElement forth_Row_delete_button = $("tbody tr:nth-of-type(4) [href='#delete']");

        third_Row_edit_button.click();
        forth_Row_delete_button.click();
    }


    @Test(description = "check/uncheck checkboxes with assertions")
    public void checkboxes() {
        open("checkboxes");
        SelenideElement checkbox_1 = $x("//form[@id='checkboxes']/input[1]");
        SelenideElement checkbox_2 = $("#checkboxes [type='checkbox']:nth-of-type(2)");

        checkbox_1.shouldNotBe(selected); // or checkbox_2.shouldBe(checked);
        checkbox_1.setSelected(true);
        checkbox_1.shouldBe(checked);

        checkbox_1.setSelected(false);
        checkbox_1.shouldNotBe(selected); // or checkbox_1.shouldNotBe(checked);
    }


    @Test(description = "4 options of manipulating with dropdown list")
    public void dropdown_List() {
        open("dropdown");
        SelenideElement menu = $(By.id("dropdown"));

        menu.selectOption(1);
        menu.selectOption("Option 2");
        menu.selectOptionContainingText("ion 1");
        menu.selectOptionByValue("2");
    }


    @Test(enabled = true, description = "check last image on block after updating the page using do-while loop")
    public void dynamic_Content() {
        open("dynamic_content");
        SelenideElement el = $(".row [src='/img/avatars/Original-Facebook-Geek-Profile-Avatar-1.jpg']");

        do {
            SelenideElement update_button = $(By.linkText("click here"));
            update_button.click();
        } while (!el.isDisplayed());
    }


    @Test(description = "check/unckeck checkboxes and waiting for loading using waitUntil + assertions")
    public void dynamic_Controls() {
        open("dynamic_controls");
        SelenideElement checkbox = $("input");
        SelenideElement remove_button = $("#checkbox-example [autocomplete]");
        SelenideElement add_button = $("#checkbox-example [autocomplete]");
        SelenideElement its_gone_message = $("#message");
        SelenideElement its_back_message = $("#message");

        checkbox.setSelected(true).shouldBe(checked);
        remove_button.click();
        its_gone_message.waitUntil(appear, 5000).shouldHave(text("It's gone!"));

        add_button.click();
        its_back_message.waitUntil(appear, 5000).shouldHave(exactText("It's back!"));
        checkbox.shouldNotBe(selected);
    }


    @Test(description = "Waiting for elements (2 examples) using waitWhile / waitUntil + assertions")
    public void dynamic_Loading() {

        SelenideElement start_button = $x("//div[@id='start']/button[.='Start']");
        SelenideElement expected_text = $("#finish h4");
        SelenideElement loading_brogressBar = $("#loading");

        // Example 1: Element on page that is hidden
        open("dynamic_loading/1");
        start_button.click();
        loading_brogressBar.waitWhile(appear, 7000);
        expected_text.shouldBe(visible).shouldHave(exactText("Hello World!"));

        // Example 2: Element rendered after the fact
        open("dynamic_loading/2");
        start_button.click();
        loading_brogressBar.waitUntil(disappear, 7000);
        expected_text.should(appear).shouldHave(exactText("Hello World!"));
    }


    @Test(description = "check download feature 'File.class' + verify format / file name + Hamcrest/TestNG assertions")
    public void file_downloader() throws FileNotFoundException {

        String dirpath = "/Users/ismagilov/IdeaProjects/selenide_tests/build/reports/tests";
        SelenideElement link_format_jpg = $(By.linkText("15355371749013254739699674591536.jpg"));

        open("download");
        link_format_jpg.download();
        // All files are stored inside project folder by default > build > reports > tests

        System.out.println(getLatestFilefromDir(dirpath));
        File getLatestFile = getLatestFilefromDir(dirpath);
        String fileName = getLatestFile.getName();
        System.out.println(fileName);

        // Hamcrest assertions:
        assertThat(fileName, is("15355371749013254739699674591536.jpg"));
        assertThat(isFileDownloaded_Ext(dirpath, "jpg"), equalTo(true));
        assertThat(isFileDownloaded(dirpath, "15355371749013254739699674591536.jpg"), equalTo(true));

        // Or using TestNG assertions:
        assertTrue(isFileDownloaded(dirpath, "15355371749013254739699674591536.jpg"), "Failed to download Expected document");
        assertTrue(isFileDownloaded_Ext(dirpath, "jpg"), "Failed to download document which has extension .jpg");
        assertTrue(fileName.equals("15355371749013254739699674591536.jpg"), "Downloaded file name is not matching with expected file name");
    }

    // Methods helpers
    // Get the latest file from a specific directory
    public File getLatestFilefromDir(String dirPath) {
        File dir = new File(dirPath);
        File[] files = dir.listFiles();
        if (files == null || files.length == 0) {
            return null;
        }

        File lastModifiedFile = files[0];
        for (int i = 1; i < files.length; i++) {
            if (lastModifiedFile.lastModified() < files[i].lastModified()) {
                lastModifiedFile = files[i];
            }
        }
        return lastModifiedFile;
    }


    //Check the file from a specific directory with extension
    public boolean isFileDownloaded_Ext(String dirPath, String ext) {
        boolean flag = false;
        File dir = new File(dirPath);
        File[] files = dir.listFiles();
        if (files == null || files.length == 0) {
            flag = false;
        }

        for (int i = 1; i < files.length; i++) {
            if (files[i].getName().contains(ext)) {
                flag = true;
            }
        }
        return flag;
    }


    //Check the file from a specific directory with name
    public boolean isFileDownloaded(String dirpath, String fileName) {
        boolean flag = false;
        File dir = new File(dirpath);
        File[] dir_contents = dir.listFiles();
        for (int i = 0; i < dir_contents.length; i++) {
            if (dir_contents[i].getName().equals(fileName))
                return flag = true;
        }

        return flag;
    }

    // Compare byte to byte
    File file1 = new File("$Path1");
    File file2 = new File("$Path2");
    boolean equalFiles;

    {
        try {
            equalFiles = FileUtils.contentEquals(file1, file2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
       Path firstFilePath = Paths.get("<Path-To-Your-First-File>");
       Path secondFilePath = Paths.get("<Path-To-Your-Second-File>");

       try {
       boolean isEqual = FileUtils.contentEquals(firstFilePath.toFile(), secondFilePath.toFile());
       if(isEqual) {
       //do business logic
       }
       } catch (IOException e) {
       //handle exception
       }
     */

    @Test
    public void file_Uploader() {

        SelenideElement upload_button = $("#file-submit");
        SelenideElement choose_file = $("#file-upload");
        SelenideElement alert_successful = $("h3");
        SelenideElement displaying_fileName = $("#uploaded-files");
        SelenideElement drag_drop_area = $("#drag-drop-upload");
        String pathName = "build/reports/tests/15355371749013254739699674591536.jpg";

        open("upload");
        File file = choose_file.uploadFile(new File(pathName));
        upload_button.click();
        alert_successful.shouldHave(textCaseSensitive("File Uploaded!"));
        displaying_fileName.shouldHave(exactText("15355371749013254739699674591536.jpg"));

    }


    @Test(description = "Handling floating menu after scroll")
    public void floating_Menu() {

        SelenideElement news_menu = $(By.linkText("News"));
        SelenideElement contact_menu = $(By.linkText("Contact"));
        SelenideElement scroll_target = $(ByText.partialLinkText("Elemental Selenium"));

        open("floating_menu");
        scroll_target.scrollTo();
        contact_menu.click();
        news_menu.click();
    }


    @Test(description = "send email and verify exect text")
    public void forgot_password() {

        SelenideElement email_field = $(By.id("email"));
        SelenideElement retrieve_password = $("#form_submit");
        SelenideElement confirm_text = $("#content");

        open("forgot_password");
        email_field.val("ismagilov.vel@gmail.com");
        retrieve_password.click();
        confirm_text.should(appear).shouldHave(exactText("Your e-mail's been sent!"));
    }


    @Test(description = "login/logout with verifications")
    public void login() {

        SelenideElement username = $("#username");
        SelenideElement password = $("[type='password']");
        SelenideElement login_button = $("[class ='fa fa-2x fa-sign-in']");
        SelenideElement confirm_alert = $("#flash");
        SelenideElement logout_button = $("[class='icon-2x icon-signout']");

        open("login");
        username.val("tomsmith");
        password.val("SuperSecretPassword!");
        login_button.click();
        confirm_alert.should(visible)
                .shouldHave(text("You logged into a secure area!"));
        logout_button.click();
        confirm_alert.shouldBe(visible)
                .shouldHave(text("You logged out of the secure area!"));
    }


    @Test(description = "working with iframe")
    public void iframe() {

        SelenideElement iframe = $("#mce_0_ifr");
        SelenideElement text_area = $x("//body[@id='tinymce']");
        SelenideElement make_bold = $(".mce-i-bold");

        open("iframe");
        switchTo().frame(iframe);
        text_area.clear();
        text_area.sendKeys("Text in iframe");
        text_area.sendKeys(Keys.chord(Keys.COMMAND, "a"));
    }


    @Test(description = "using dragAndDropBy for interacting with horizontal slider + verification with Selenide and Hamcrest")
    public void horizontal_slider() {

        SelenideElement slider = $("input");
        SelenideElement verify_result = $("#range");

        open("horizontal_slider");
        actions().dragAndDropBy(slider, 10, 0).build().perform();
        verify_result.shouldHave(text("3"));
        // or using hamcrest
        assertThat(verify_result.getText(), is("3"));
    }

    @Test(description = "hover + verification")
    public void hovers() {

        SelenideElement hover_image1 = $(".example .figure:nth-child(3) [src]");
        SelenideElement verify_user1 = $(".example .figure:nth-child(3) h5");
        SelenideElement viewProfile_link = $(By.linkText("View profile"));
        SelenideElement alert = $("h1");

        open("hovers");
        hover_image1.hover();
        verify_user1.shouldBe(visible).shouldHave(text("user1"));
        viewProfile_link.click();
        alert.should(appear).shouldHave(exactText("Not Found"));
    }


    @Test(description = "Handle jquery menu")
    public void jquery_ui_menu() {

        SelenideElement enabled_menu_item = $(By.linkText("Enabled"));
        SelenideElement download_menu_item = $(By.linkText("Downloads"));
        SelenideElement excel_menu_item = $(By.linkText("Excel"));

        open("jqueryui/menu");
        enabled_menu_item.hover();
        download_menu_item.hover();
        excel_menu_item.click();
    }


    @Test(description = "handle js alerts, js confirm, js prompt")
    public void javascript_alerts() {
        SelenideElement js_alert_button = $x("//button[.='Click for JS Alert']");
        SelenideElement js_confirm_button = $x("//button[.='Click for JS Confirm']");
        SelenideElement js_prompt_button = $x("//button[.='Click for JS Prompt']");
        SelenideElement result = $("#result");

        open("javascript_alerts");
        js_alert_button.click();
        confirm();
        js_confirm_button.click();
        dismiss();
        js_prompt_button.click();
        prompt("I am a JS prompt", "hi!");
        result.shouldHave(exactText("You entered: hi!"));
    }


    @Test
    public void windows() {
        SelenideElement clickHere_button = $(By.linkText("Click Here"));
        SelenideElement verifyText = $("h3");

        open("windows");
        clickHere_button.click();
        switchTo().window(1);
        verifyText.shouldBe(visible).shouldHave(exactText("New Window"));
    }


    @Test
    public void notification_message() {
        SelenideElement notification = $("#flash");
        SelenideElement clickHere_link = $(By.linkText("Click here"));

        open("notification_message_rendered");
        clickHere_link.click();
        notification
                .shouldBe(visible)
                .shouldHave(or("Action unsuccesful, please try again", text("Action unsuccesful x"), text("Action unsuccesful, please try again")));
    }


    @Test
    public void redirection() {
        SelenideElement here_link = $(By.linkText("here"));
        SelenideElement header = $("h3");
        SelenideElement link_500 = $(By.linkText("500"));
        SelenideElement verify_500_text = $x("//div[@id='content']//p");

        open("redirector");
        here_link.click();
        header.shouldBe(visible).shouldHave(exactText("Status Codes"));
        link_500.click();
        verify_500_text.shouldBe(visible).shouldHave(text("This page returned a 500 status code."));
    }


    @Test
    public void sortable_tables() {
        ElementsCollection list_names_1 = $$(".example .tablesorter:nth-child(5) td:nth-of-type(2)");
        SelenideElement first_name_sort = $(".example .tablesorter:nth-child(5) tr .header:nth-of-type(2)");
        ElementsCollection list_due = $$(".example .tablesorter:nth-child(5) td:nth-of-type(4)");

        open("tables");
        first_name_sort.click();

        assertThat(list_names_1.texts(), Matchers.contains("Frank", "Jason", "John", "Tim")); // check the order after sorting
        assertThat(list_names_1.get(1).getText(), is("Jason")); // check the 2nd element
        assertThat(list_names_1.texts(), Matchers.containsInAnyOrder("John", "Jason", "Frank", "Tim")); // without order
        list_names_1.shouldHave(size(4)); // check size of collection
        list_names_1.last().shouldHave(exactText("Tim")); // check last item
        assertThat(list_names_1.texts(), both(hasItems(endsWith("n"))).and(hasItem(startsWith("T")))); // play with several assertions (also we have anyOf, AllOf)
        list_due.get(0).shouldHave(exactText("$51.00"));

        System.out.println(list_due.texts());
        System.out.println(list_due.get(1).getText());
    }


    @Test
    public void typos() {
        SelenideElement second_line = $(".example p:nth-child(3)");

        open("typos");
        while (!second_line.has(exactText("Sometimes you'll see a typo, other times you won,t."))) {
            refresh();
        }
    }


    @Test //http://internetka.in.ua/selenium-webdriver-tinymce/ - expand all structure and find required iframe locators
    public void tinymce_iframe() {
        SelenideElement iframe = $("#mce_0_ifr");
        SelenideElement textArea = $("#tinymce");
        SelenideElement make_bold_text = $(".mce-i-bold");


        open("tinymce");
        switchTo().frame(iframe);
        textArea.clear();
        textArea.sendKeys("TEXT TEXT TEXT");
        textArea.sendKeys(Keys.COMMAND + "a");
        switchTo().defaultContent();
        make_bold_text.click();
    }

}