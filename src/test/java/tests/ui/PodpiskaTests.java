package tests.ui;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.*;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.PodpiskaPage;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.sleep;

public class PodpiskaTests extends TestBase {



    @Test
    @Feature("Оформление подписки на журнал")
    @Story("Оформить подписку на журнал для себя на нужный период с редактированием")
    @DisplayName("Пользователь может подписаться на журнал на Сентябрь, логин перед покупкой, редакт перед покупкой")
    @Owner("Роман Чесанов (@ChesKnow")
    @Severity(SeverityLevel.CRITICAL)
    void authorizedUserCanOfferMagazineForHimselfWithChangesBeforeBuy() {
        SelenideLogger.addListener("allure", new AllureSelenide());
        PodpiskaPage podpiskaPage = new PodpiskaPage();
        WebSteps webSteps = new WebSteps();

        //open("https://www.pochta.ru/");
        //podpiskaPage.openMainPage();
        webSteps.openMainPageWithChecking();
        webSteps.chooseSubscribeToMagazineInPopupMenu();

        //sleep(600_000);
        podpiskaPage.searchToMagazineByFullTitle("Наука и жизнь");
        podpiskaPage.confirmChooseExpectedMagazine();
        podpiskaPage.fillRecipientData("Чесанов Роман Владимирович", "Сергиев Посад Матросова 7 1");
        podpiskaPage.chooseMonthOfSubsription("Сен");

        //sleep(600_000);
    }
    /*
    step зайти на главную страницу +
    step перейти на страницу подписки https://podpiska.pochta.ru/ +
    step в поле поиска ввести полное наименование журнала и нажать Enter +
    step заполнить данные для покупки
    step выбор месяцев подписки
    step проверить стоимость
    step положить товар в корзину
    step перейти в корзину
    step проверить параметры выбора и сумму
    step отредактировать парметры
    step проверить параметры выбора и сумму
    step удалить товар из коризну и проверить. что она пустая

    * */
}
