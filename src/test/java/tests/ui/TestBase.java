package tests.ui;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import helpers.Attach;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.chrome.ChromeOptions;
import pages.*;


public class TestBase {

    MainPage mainPage = new MainPage();
    SupportPage supportPage = new SupportPage();
    ShipmentsPage shipmentsPage = new ShipmentsPage();
    LoginPage loginPage = new LoginPage();
    PodpiskaPage podpiskaPage = new PodpiskaPage();

    @BeforeAll
    static void beforeAll() {


        Configuration.baseUrl = "https://www.pochta.ru/";
        Configuration.browserSize = "1920x1080";
        Configuration.browserVersion = "125";


        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-features=OptimizationHints, OptimizationHintsFetching, Translate");
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-popup-blocking");
        Configuration.browserCapabilities = options;



    }

    @BeforeEach
    void addAllureListener() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());
    }

    @AfterEach
    void addAttachments() {
        Attach.screenshotAs("Last screenshot");
        Attach.pageSource();
        Attach.browserConsoleLogs();
        //Attach.addVideo();
    }
}
