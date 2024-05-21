package tests.ui;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.MainPage;

public class MainPageTests extends TestBase {

    MainPage mainPage = new MainPage();

    @Test
    @DisplayName("Открываем главную страницу")
    void openMainPageTest() {
        mainPage.openMainPageAndCheckTitle();
    }

    @Test
    @DisplayName("Проверяем трекер отправлений с главной страницы по нажатию Enter")
    void trackingFromMainPageEnterTest() {
        mainPage.trackingByPressEnter("RW052656168KZ");
    }

    @Test
    @DisplayName("Проверяем трекер отправлений с главной страницы по клику на лупу")
    void trackingFromMainPageLoopTest() {mainPage.trackingByClickOnLoop("RW052656168KZ");}

    @Test
    @DisplayName("Проверяем, что трекер не стартует если ввести неправильный ШПИ")
    void trackingNotStartingTest() {mainPage.trackingNotStartingTest("RW052656168K");}
}
