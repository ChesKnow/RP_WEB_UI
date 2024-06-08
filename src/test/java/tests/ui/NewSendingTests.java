package tests.ui;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.junit5.SoftAssertsExtension;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.*;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static com.codeborne.selenide.AssertionMode.SOFT;
import static com.codeborne.selenide.Selenide.sleep;

@ExtendWith(SoftAssertsExtension.class)
public class NewSendingTests extends TestBase {

    @Test
    @Feature("Отправка посылок онлайн")
    @Story("Создать отправку посылки онлайн")
    @DisplayName("Авторизованный Пользователь может создать отправку посылки онлайн по тарифу ускоренный")
    @Owner("Роман Чесанов (@ChesKnow")
    @Severity(SeverityLevel.CRITICAL)
    void authorizedUserCanCreateNewParcelSending() {
        Configuration.assertionMode = SOFT;
        SelenideLogger.addListener("allure", new AllureSelenide());
        WebSteps steps = new WebSteps();

        steps.openMainPageWithChecking();
        steps.chooseParcelInPopupMenuSending();

        steps.checkRedirectedToShipmentsPage();
        steps.fillMinimumRequiredFieldsForParcel();
        steps.setForwardingType();
        steps.checkTarifValueCorrespondtoForwardingTypeAndConfirm();

        steps.enterCredentials();

        steps.checkRedirectedToShipmentsPage();
        steps.checkCorrectnessOfPacelFields();
        steps.fillAddresseeName();
        steps.chooseCheckboxValuedParcel();
        steps.choosePaymentMethod();
        steps.checkTotalValueAmount();

        sleep(5000);


        /*
        step открыть главную страницу +
        step в меню Отправить выбрать Посылка +
        step Проверить что перешел на страницу Shipments +
        step заполнить минимально необходимые параметры доставки (откуда, куда вес, габариты) +
        step выбрать тариф Ускоренный +
        step проверить что сумма за тариф изменилась и нажать перейти к оформлению +
        step ввести логин и пароль +
        step Проверить что перешел на страницу +
        step проверить что введенные ранее данные верны +
        step заполнить данные получателя +
        step выбрать чекбокс Ценная
        step выбрать в отделении связи +
        step проверить сумму оплаты

        */

    }


}
