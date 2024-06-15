package tests.ui;

import com.codeborne.pdftest.PDF;
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
import static com.codeborne.selenide.Selenide.$;

@Tag("e2e")
@ExtendWith(SoftAssertsExtension.class)
public class E2Escenarious extends TestBase {

    private final String
            fio = "Чесанов Роман Владимирович",
            magazineTitle = "Юность",
            address = "Сергиев Посад Матросова 7 1",
            addressTo = "обл Московская, г Сергиев Посад, ул Матросова, д. 7, кв. 1",
            addressFrom = "г Москва, ш Варшавское, д. 37",
            weight = "1 кг",
            fullAddress = "141301, Московская обл, Сергиев Посад, Матросова ул, 7, кв 1",
            amount = "371,95 ₽",
            fullAmount = "2231,70 ₽",
            totalAmount = "371,95 ₽",
            newMonth = "2024: Сентябрь, Ноябрь",
            newAmount = "743,90 ₽",
            month = "2024: Сентябрь",
            shortMonth = "Ноя",
            noAmount = "0,00 ₽",
            searchData = "Сроки";


    @Test
    @Feature("Поиск информации на сайте")
    @Story("Через поиск на сайте найти сроки доставки посылок")
    @DisplayName("Пользователь может найти сроки доставки через поиск на сайте")
    @Owner("Роман Чесанов (@ChesKnow)")
    @Severity(SeverityLevel.NORMAL)
    @Tag("positive")
    @Tag("regress")
    void customerCanFindAndDownloadDeliveryTerms() throws IOException {



        mainPage.openMainPageWithChecking();
        mainPage.startSearchOnSite();
        mainPage.enterSearchData(searchData);
        mainPage.chooseSearchDataInSearchResults();

        supportPage.scrollToTheBottomAndClickToTheLink();
        supportPage.chooseParcelDeliveryTerms();

        shipmentsPage.fillMinimumRequiredFieldsForParcel(addressFrom, addressTo, weight);
        shipmentsPage.checkDeliveryTerms();
        shipmentsPage.canSeeWarningRegardingAcceptanceCondition();
        shipmentsPage.canSeeWarningRegardingDeliveryTerms();


    }

    @Test
    @Feature("Отправка посылок онлайн")
    @Story("Создать отправку посылки онлайн")
    @DisplayName("Авторизованный Пользователь может создать отправку посылки онлайн по тарифу ускоренный")
    @Owner("Роман Чесанов (@ChesKnow")
    @Severity(SeverityLevel.CRITICAL)
    @Tag("positive")
    @Tag("regress")
    void authorizedUserCanCreateNewParcelSending() {
        Configuration.assertionMode = SOFT;


        mainPage.openMainPageWithChecking();
        mainPage.chooseParcelInPopupMenuSending();

        shipmentsPage.checkRedirectedToShipmentsPage();
        shipmentsPage.fillMinimumRequiredFieldsForParcel(addressFrom, addressTo, weight);
        shipmentsPage.setForwardingType();
        shipmentsPage.checkTarifValueCorrespondtoForwardingTypeAndConfirm();

        loginPage.enterCredentialsWithSubmit();

        shipmentsPage.checkRedirectedToShipmentsPage();
        shipmentsPage.checkCorrectnessOfPacelFields(addressFrom, addressTo);
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



        mainPage.openMainPageWithChecking();
        mainPage.chooseSubscribeToMagazineInPopupMenu();

        podpiskaPage.searchToMagazineByFullTitle(magazineTitle);
        Assertions.assertEquals("Результаты по запросу «Юность» 3 результата", $("h1").getText());
        podpiskaPage.confirmChooseExpectedMagazine(magazineTitle);
        podpiskaPage.fillRecipientData(fio, address);
        podpiskaPage.chooseSeptemberMonthOfSubsription();
        podpiskaPage.checkAmountSumAndPutMagazineToCart(amount, fullAmount, totalAmount);
        podpiskaPage.redirectToCart();
        podpiskaPage.checkAddresseeAndAmountIsCorrect(magazineTitle, fio, fullAddress, month, amount);
        podpiskaPage.customerRemindAndAddNovemberMonth(amount, newAmount, shortMonth);
        podpiskaPage.checkAddresseeAndAmountIsCorrect(magazineTitle, fio, fullAddress, newMonth, newAmount);
        podpiskaPage.customerStartBuyProcess();

        loginPage.enterCredentialsWithSubmit();

        podpiskaPage.customerDeleteGoodsFromCart(noAmount);

    }

}
