package tests.ui;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.junit5.SoftAssertsExtension;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.*;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import pages.*;

import java.io.IOException;

import static com.codeborne.selenide.AssertionMode.SOFT;
import static com.codeborne.selenide.Selenide.sleep;

@Tag("e2e")
@ExtendWith(SoftAssertsExtension.class)
public class E2Escenarious extends TestBase{

    private final String
            fio = "Чесанов Роман Владимирович",
            magazine_title = "Юность",
            address = "Сергиев Посад Матросова 7 1",
            full_address = "141301, Московская обл, Сергиев Посад, Матросова ул, 7, кв 1",
            amount = "371,95 ₽",
            full_amount = "2231,70 ₽",
            total_amount = "371,95 ₽",
            new_month = "2024: Сентябрь, Ноябрь",
            new_amount = "743,90 ₽",
            month = "2024: Сентябрь",
            short_month = "Ноя",
            no_amount = "0,00 ₽";

    @Test
    @Feature("Поиск информации на сайте")
    @Story("Через поиск на сайте найти сроки доставки посылок")
    @DisplayName("Пользователь может найти сроки доставки через поиск на сайте")
    @Owner("Роман Чесанов (@ChesKnow)")
    @Severity(SeverityLevel.NORMAL)
    @Tag("positive")
    @Tag("regress")
    void customerCanFindAndDownloadDeliveryTerms() throws IOException {

        Configuration.assertionMode = SOFT;

        MainPage mainPage = new MainPage();
        SupportPage supportPage = new SupportPage();
        ShipmentsPage shipmentsPage = new ShipmentsPage();

        mainPage.openMainPageAndCheckTitle();
        mainPage.startSearchOnSite();
        mainPage.enterSearchData("Сроки");
        mainPage.chooseSearchDataInSearchResults("Сроки доставки");
        supportPage.scrollToTheBottomAndClickToTheLink("Контрольные сроки доставки посылок до 13.06.2024");
        supportPage.chooseParcelDeliveryTerms();
        shipmentsPage.fillMinimumRequiredFieldsForParcel();
        shipmentsPage.checkDeliveryTerms();
        shipmentsPage.canSeeWarningRegardingAcceptanceCondition();
        shipmentsPage.canSeeWarningRegardingDeliveryTerms();

        /*
        step пользователь заходит на сайт pochta.ru +
        step кликает на значок лупы +
        step вводит текст для поиска сроки доставки +
        step в результатах поиска выбирает сроки доставки и переходит на нее +
        step внизу страницы находит ссылку Документы и нажимает на ссылку Контрольные сроки доставки посылок +
        step ничего не понимает и возващается обратно на страницу +
        step выбирает кнопку Посылки под текстом Рассчитать сроки доставки +
        step заполняет поля +
        step смотрит сроки доставки +
        step видит предупреждение об условиях приема +
        step видит предупреждение о сроках +
        * */
    }


    @Feature("Отправка посылок онлайн")
    @Story("Создать отправку посылки онлайн")
    @DisplayName("Авторизованный Пользователь может создать отправку посылки онлайн по тарифу ускоренный")
    @Owner("Роман Чесанов (@ChesKnow")
    @Severity(SeverityLevel.CRITICAL)
    @Tag("positive")
    @Tag("regress")
    @RepeatedTest(3)
    void authorizedUserCanCreateNewParcelSending() {
        Configuration.assertionMode = SOFT;
        SelenideLogger.addListener("allure", new AllureSelenide());
        MainPage mainPage = new MainPage();
        ShipmentsPage shipmentsPage = new ShipmentsPage();
        LoginPage loginPage = new LoginPage();

        mainPage.openMainPageWithChecking();
        mainPage.chooseParcelInPopupMenuSending();

        shipmentsPage.checkRedirectedToShipmentsPage();
        shipmentsPage.fillMinimumRequiredFieldsForParcel();
        shipmentsPage.setForwardingType();
        shipmentsPage.checkTarifValueCorrespondtoForwardingTypeAndConfirm();

        loginPage.enterCredentialsWithSubmit();

        shipmentsPage.checkRedirectedToShipmentsPage();
        shipmentsPage.checkCorrectnessOfPacelFields();
        shipmentsPage.fillAddresseeName();
        shipmentsPage.chooseCheckboxValuedParcel();
        shipmentsPage.choosePaymentMethod();
        shipmentsPage.checkTotalValueAmount();

        }

    @Test
    @Feature("Оформление подписки на журнал")
    @Story("Оформить подписку на журнал для себя на нужный период с редактированием")
    @DisplayName("Пользователь может подписаться на журнал на Сентябрь, логин перед покупкой, редакт перед покупкой")
    @Owner("Роман Чесанов (@ChesKnow)")
    @Severity(SeverityLevel.CRITICAL)
    @Tag("positive")
    @Tag("regress")
    void authorizedUserCanOfferMagazineForHimselfWithChangesBeforeBuy() {



        SelenideLogger.addListener("allure", new AllureSelenide());
        PodpiskaPage podpiskaPage = new PodpiskaPage();
        MainPage mainPage = new MainPage();
        LoginPage loginPage = new LoginPage();


        mainPage.openMainPageWithChecking();
        mainPage.chooseSubscribeToMagazineInPopupMenu();

        podpiskaPage.searchToMagazineByFullTitle(magazine_title);
        podpiskaPage.confirmChooseExpectedMagazine(magazine_title);
        podpiskaPage.fillRecipientData(fio, address);
        podpiskaPage.chooseSeptemberMonthOfSubsription();
        podpiskaPage.checkAmountSumAndPutMagazineToCart(amount, full_amount, total_amount);
        podpiskaPage.redirectToCart();
        podpiskaPage.checkAddresseeAndAmountIsCorrect(magazine_title, fio, full_address, month, amount);
        podpiskaPage.customerRemindAndAddNovemberMonth(amount, new_amount, short_month);
        podpiskaPage.checkAddresseeAndAmountIsCorrect(magazine_title, fio, full_address, new_month, new_amount);
        podpiskaPage.customerStartBuyProcess();

        loginPage.enterCredentialsWithSubmit();

        podpiskaPage.customerDeleteGoodsFromCart(no_amount);

    }

}
