package tests.ui;

import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import pages.MainPage;

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

import static com.codeborne.selenide.CollectionCondition.texts;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class MainPageTests extends TestBase {


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
        mainPage.openMainPage();
        $$("[data-submenu='submenu']").filter(visible).shouldHave(texts(expectedSections));

    }


}
