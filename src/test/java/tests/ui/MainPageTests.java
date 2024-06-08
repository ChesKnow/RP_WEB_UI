package tests.ui;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import pages.MainPage;

import java.util.List;
import java.util.stream.Stream;

import static com.codeborne.selenide.CollectionCondition.texts;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

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
    void trackingFromMainPageLoopTest() {
        mainPage.trackingByClickOnLoop("RW052656168KZ");
    }

    @Test
    @DisplayName("Проверяем, что трекер не стартует если ввести неправильный ШПИ")
    void trackingNotStartingTest() {
        mainPage.trackingNotStartingTest("RW052656168K");
    }


    static Stream<Arguments> mainPageShouldContainsAllOfStandardSections() {
        return Stream.of(
                Arguments.of(List.of("Отправить", "Получить", "Платежи и переводы", "Услуги в отделениях", "Онлайн-сервисы"))

        );
    }
    @MethodSource()
    @ParameterizedTest
    @Tag("WEB")
    @DisplayName("На главной странице должен отображаться список секций")
    void mainPageShouldContainsAllOfStandardSections(List<String> expectedSections) {
        open("https://www.pochta.ru/");
        sleep(5000);
        $$("[data-submenu='submenu']").filter(visible).shouldHave(texts(expectedSections));

    }
}
