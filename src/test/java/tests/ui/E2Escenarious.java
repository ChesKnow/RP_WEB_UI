package tests.ui;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.junit5.SoftAssertsExtension;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.*;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.IOException;

import static com.codeborne.selenide.AssertionMode.SOFT;
import static com.codeborne.selenide.Selenide.$;
import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.Assertions.assertThat;

@Tag("e2e")
@ExtendWith(SoftAssertsExtension.class)
public class E2Escenarious extends TestBase {

    private final String
            fio = "Чесанов Роман Владимирович",
            magazineTitle = "Юность",
            address = "Сергиев Посад Матросова 7 1",
            addressTo = "обл Московская, г Сергиев Посад, ул Матросова, д. 7, кв. 1",
            indexFrom = "115127",
            addressFrom = "г Москва, ш Варшавское, д. 37",
            indexTo = "141301",
            weight = "1 кг",
            fullAddress = "141301, Московская обл, Сергиев Посад, Матросова ул, 7, кв 1",
            amount = "371,95 ₽",
            fullAmount = "2231,70 ₽",
            totalAmount = "371,95 ₽",
            newMonth = "2024: Сентябрь, Ноябрь",
            newAmount = "743,90 ₽",
            month = "2024: Сентябрь",
            searchData = "Сроки",
            dimension = "30x20x15 см",
            shipmentParams = "Москва — Сергиев Посад.",
            tariffParams = "Ускоренный, до 1–2 дней",
            recipientName = "Иванов Иван Иванович",
            parcelUrl = "https://www.pochta.ru/shipment?type=PARCEL",
            linkToResults = "/support/post-rules/delivery-terms",
            valueOfParcel = "100",
            sendTotalAmount = "369,60 ₽",
            sendTariffAmount = "366,00 ₽",
            addCostAmount = "3,60 ₽";


    @Test
    @Feature("Поиск информации на сайте")
    @Story("Через поиск на сайте найти сроки доставки посылок")
    @DisplayName("Находит сроки доставки через поиск на сайте, скачивает их -> заполняет данные и отображаются сроки интерактивно")
    @Owner("Роман Чесанов (@ChesKnow)")
    @Severity(SeverityLevel.NORMAL)
    @Tag("positive")
    @Tag("regress")
    @Tag("e2e")
    void canFindAndDownloadDeliveryTerms() throws IOException {



        mainPage.openMainPage();
        mainPage.startSearchOnSite();
        mainPage.enterSearchData(searchData);
        mainPage.chooseSearchDataInSearchResults(linkToResults);

        supportPage.scrollToTheBottomAndClickToTheLink();
        supportPage.chooseParcelDeliveryTerms();

        shipmentsPage.fillAddresseeAndSenderDetails(addressFrom, indexFrom, addressTo, indexTo, weight, dimension);

        shipmentsPage.canSeeWarningRegardingAcceptanceCondition();
        shipmentsPage.canSeeWarningRegardingDeliveryTerms();


    }

    @Test
    @Feature("Отправка посылок онлайн")
    @Story("Создать отправку посылки онлайн")
    @DisplayName("Клиент может создать отправку посылки онлайн по тарифу ускоренный с оплатой в отделении связи")
    @Owner("Роман Чесанов (@ChesKnow)")
    @Severity(SeverityLevel.CRITICAL)
    @Tag("positive")
    @Tag("regress")
    void canCreateNewParcelSendingThrowPostOfficePayment() {
        Configuration.assertionMode = SOFT;


        step("Открываем главную страницу и выбрать в меню услуг отправить посылку", () -> {
            mainPage.openMainPage()
                    .chooseParcelInPopupMenuSending(parcelUrl);
        });
        step("Заполнить минимально необходимые поля для отправки", () -> {
             shipmentsPage.fillAddresseeAndSenderDetails(addressFrom, indexFrom, addressTo, indexTo, weight, dimension);
        });
        step("Выбрать вид пересылки Ускоренный", () ->{
            shipmentsPage.setForwardingType();
        });
        step("Перейти к оформлению через станицу с вводом данных личного кабинета" , () -> {
             shipmentsPage.goToCheckoutThrowLoginPage();
        });
        step("Ввести логин и пароль и нажать Войти", () -> {
             loginPage.enterCredentialsWithSubmit();
        });
        step("Проверить переход на страницу и правильность введенных данных", () -> {
              shipmentsPage
                       .checkRedirectedToShipmentsPage(parcelUrl)
                       .checkCorrectnessOfPacelFields(addressFrom, addressTo, shipmentParams, tariffParams);
        });
        step("Заполнить поле фио получателя", () -> {
              shipmentsPage.fillAddresseeName(recipientName);
        });
        step("Установить ценность посылки "+ valueOfParcel,  () -> {
              shipmentsPage.setValueOfParcel(valueOfParcel);
        });
        step("Проверить что сумма Итого верна", () -> {
            shipmentsPage.checkTotalValueAmount(sendTotalAmount, sendTariffAmount, addCostAmount);
        });
        step("Выбрать метод оплаты в отделении связи", () -> {
            shipmentsPage.choosePaymentMethod();
        });



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

        mainPage.openMainPage();
        mainPage.chooseSubscribeToMagazineInPopupMenu();
        podpiskaPage.searchToMagazineByFullTitle(magazineTitle);
        Assertions.assertEquals("Результаты по запросу «Юность» 3 результата", $("h1").getText());
        podpiskaPage.confirmChooseExpectedMagazine(magazineTitle);
        podpiskaPage
                .fillRecipientData(fio, address)
                .chooseBuyForWhom("SELF")
                .chooseMethodOfDelivery("TO_ADDRESSEE")
                .chooseMonthOfSubsription()
                .checkAmountSum(amount, fullAmount, totalAmount)
                .putGoodsIntoTheCart()
                .redirectToCart()
                .checkDataInTheCart(magazineTitle, fio, fullAddress, month, amount)
                .changeDataInTheCart();

        podpiskaPage
                .addMonthOfSubsription()
                .acceptChanges();

        podpiskaPage
                .checkDataInTheCart(magazineTitle, fio, fullAddress, newMonth, newAmount)
                .startBuyProcess();

    }

}
