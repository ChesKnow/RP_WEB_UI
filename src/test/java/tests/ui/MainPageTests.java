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

    @Test
    @Feature("Поиск информации на сайте")
    @Story("Через поиск на сайте найти сроки доставки посылок")
    @DisplayName("Пользователь может найти сроки доставки через поиск на сайте")
    @Owner("Роман Чесанов (@ChesKnow)")
    @Severity(SeverityLevel.NORMAL)
    void customerCanFindAndDownloadDeliveryTerms() throws IOException {

        MainPage mainPage = new MainPage();
        WebSteps webSteps = new WebSteps();

        mainPage.openMainPageAndCheckTitle();
        mainPage.startSearchOnSite();
        mainPage.enterSearchData("Сроки");
        mainPage.chooseSearchDataInSearchResults("Сроки доставки");
        mainPage.scrollToTheBottomAndClickToTheLink("Контрольные сроки доставки посылок до 13.06.2024");
        mainPage.chooseParcelDeliveryTerms();
        webSteps.fillMinimumRequiredFieldsForParcel();
        webSteps.checkDeliveryTerms();
        mainPage.canSeeWarningRegardingAcceptanceCondition();
        mainPage.canSeeWarningRegardingDeliveryTerms();

        /*
        step пользователь заходит на сайт pochta.ru +
        step кликает на значок лупы +
        step вводит текст для поиска сроки доставки +
        step в результатах поиска выбирает сроки доставки b переходит на нее +
        step внизу страницы находит ссылку Документы и нажимает на ссылку Контрольные сроки доставки посылок +
        step ничего не понимает и возващается обратно на страницу +
        step выбирает кнопку Посылки под текстом Рассчитать сроки доставки +
        step заполняет поля +
        step смотрит сроки доставки +
        step видит предупреждение об условиях приема +
        step видит предупреждение о сроках +
        * */
    }


}
