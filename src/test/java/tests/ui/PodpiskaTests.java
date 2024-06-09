package tests.ui;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.*;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.LoginPage;
import pages.PodpiskaPage;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.sleep;

public class PodpiskaTests extends TestBase {


    @Test
    @Feature("Оформление подписки на журнал")
    @Story("Оформить подписку на журнал для себя на нужный период с редактированием")
    @DisplayName("Пользователь может подписаться на журнал на Сентябрь, логин перед покупкой, редакт перед покупкой")
    @Owner("Роман Чесанов (@ChesKnow)")
    @Severity(SeverityLevel.CRITICAL)
    void authorizedUserCanOfferMagazineForHimselfWithChangesBeforeBuy() {
        SelenideLogger.addListener("allure", new AllureSelenide());
        PodpiskaPage podpiskaPage = new PodpiskaPage();
        WebSteps webSteps = new WebSteps();
        LoginPage loginPage = new LoginPage();


        webSteps.openMainPageWithChecking();
        webSteps.chooseSubscribeToMagazineInPopupMenu();


        podpiskaPage.searchToMagazineByFullTitle("Наука и жизнь");
        podpiskaPage.confirmChooseExpectedMagazine();
        podpiskaPage.fillRecipientData("Чесанов Роман Владимирович", "Сергиев Посад Матросова 7 1");
        podpiskaPage.chooseSeptemberMonthOfSubsription();
        podpiskaPage.checkAmountSumAndPutMagazineToCart("503,68 ₽", "3022,08 ₽", "503,68 ₽");
        podpiskaPage.redirectToCart();
        podpiskaPage.checkAddresseeAndAmountIsCorrect("Наука и жизнь", "Чесанов Роман Владимирович", "141301, Московская обл, Сергиев Посад, Матросова ул, 7, кв 1", "2024: Сентябрь", "503,68 ₽");
        podpiskaPage.customerRemindAndAddNovemberMonth();
        podpiskaPage.checkAddresseeAndAmountIsCorrect("Наука и жизнь", "Чесанов Роман Владимирович", "141301, Московская обл, Сергиев Посад, Матросова ул, 7, кв 1", "2024: Сентябрь, Ноябрь", "1007,36 ₽");
        podpiskaPage.customerStartBuyProcess();
        loginPage.enterUserCredentials("roman.chesanov@russianpost.ru", "Pochtalion*23");


        podpiskaPage.customerDeleteGoodsFromCart();

        sleep(7000);

        //sleep(600_000);
    }
    /*
    step зайти на главную страницу +
    step перейти на страницу подписки https://podpiska.pochta.ru/ +
    step в поле поиска ввести полное наименование журнала и нажать Enter +
    step заполнить данные для покупки +
    step выбор месяцев подписки +
    step проверить стоимость +
    step положить товар в корзину+
    step перейти в корзину +
    step проверить параметры выбора и сумму +
    step отредактировать парметры добавив ноябрь +
    step проверить параметры выбора и сумму +
    step нажимает оплатить,
    step удалить товар из коризну и проверить. что она пустая +

    * */
}
