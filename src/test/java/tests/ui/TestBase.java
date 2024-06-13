package tests.ui;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;


public class TestBase {
    @BeforeAll
    static void beforeAll() {
        // TODO: 08.06.2022 switch for baseurl

        Configuration.baseUrl = "https://www.pochta.ru/";
        Configuration.browserSize = "1920x1080";
        Configuration.pageLoadStrategy = "normal";


    }
}
