package pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.Keys;

import java.time.Duration;

import static com.codeborne.selenide.CollectionCondition.texts;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byTagAndText;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverConditions.url;
import static org.assertj.core.api.Assertions.assertThat;

public class ShipmentsPage {

    private final SelenideElement
            setRecipientAddress = $("#addressTo"),
            setSenderAddress = $("#addressFrom"),
            setWeight = $("#weight"),
            dimensionStandardMenu = $("[data-testid=dimension-typical]"),
            dimensionSizeM = $("[data-testid=dimension-typical-size-m]"),
            dimensionSizeSubmit = $("[type=submit]"),
            setDimention = $("[data-testid='dimension-select']"),
            rapidDelivery = $("[data-testid='delivery-type.rapid']"),
            goToCheckout = $("#validateButton"),
            acceptanceWarning = $("[data-component='NotificationWrapper']").$("div", 1),
            deliveryTime = $("[data-testid='parcels.delivery-type-time']"),
            deliveryConditionsWarning = $$("#shipment-sidebar")
            .findBy(text("Сроки доставки указаны без учёта дня приёма, а также не включают выходные и праздничные дни.")),
            shipmentParameters = $("#shipment-sidebar span"),
            tariffParameters = $("h2").parent().$("div", 1),
            senderAddress = $("#senderAddress"),
            recipientAddress = $("#recipientAddress"),
            setRecipientName = $("#recipientName"),
            madeActiveValuedParcelCheckbox = $(byTagAndText("span", "Ценная посылка")),
            setValueAmount = $("#insuranceSum"),
            choosePaymentMethodAtPostOffice = $("[data-testid='payment-method-select-formless'"),
            getSendingTrackingNumber = $("[data-testid='parcels.ticket-submit-button-desktop']").$("span"),
            totalAmount = $(byTagAndText("div", "Итого")),
            tariffAmount = $(byTagAndText("div", "Ускоренный тариф")),
            extraAmount = $("[data-testid='parcels.ticket-extra-expand']");;


    @Step("Проверяем, что перешли на страницу создания отправления")
    public ShipmentsPage checkRedirectedToShipmentsPage(String parcelUrl) {
        webdriver().shouldHave(url(parcelUrl));
        return this;
    }

    @Step("Заполнеяет минимально необходимые поля: от кого, кому, вес, габариты")
    public void fillAddresseeAndSenderDetails(String addressFrom, String indexFrom, String addressTo, String indexTo, String weight, String dimension) {

        setSenderAddress.setValue(addressFrom);
        $(byTagAndText("span",indexFrom)).click();
        setRecipientAddress.setValue(addressTo);
        $(byTagAndText("span",indexTo)).click();
        setWeight.setValue(weight);
        dimensionStandardMenu.click();
        dimensionSizeM.click();
        dimensionSizeSubmit.click();

        assertThat(setSenderAddress.getText()).isEqualTo(addressFrom);
        assertThat(setRecipientAddress.getText()).isEqualTo(addressTo);
        assertThat(setWeight.getValue()).isEqualTo(weight);
        assertThat(setDimention.getText()).isEqualTo(dimension);

    }

    @Step("Выбрать вид пересылки Ускоренный")
    public ShipmentsPage setForwardingType() {
        rapidDelivery.click();
        return this;
    }

    @Step("Перейти к оформлению")
    public void goToCheckout() {
        goToCheckout.click();
    }

    @Step("Перейти к оформлению через станицу с вводом данных личного кабинета")
    public void goToCheckoutThrowLoginPage() {
        goToCheckout.click();
        $("h2").shouldHave(text("Вход с Почта ID"));
    }



    @Step("Отображается предупреждение об условиях приема к пересылке")
    public ShipmentsPage canSeeWarningRegardingAcceptanceCondition() {
        acceptanceWarning.shouldBe(visible);
        return this;
    }

    @Step("Ниже вариантов доставки отображается строчка об особенностях сроков доставки")
    public ShipmentsPage canSeeWarningRegardingDeliveryTerms() {
        deliveryConditionsWarning
                .shouldBe(visible);
        return this;
    }

    @Step("Проверяем правильность введенных параметров посылки")
    public void checkCorrectnessOfPacelFields(String addressFrom, String addressTo, String shipmentParams, String tariffParams) {
        shipmentParameters.shouldHave(text(shipmentParams));
        tariffParameters.shouldHave(text(tariffParams));
        senderAddress.shouldHave(text(addressFrom));
        recipientAddress.shouldHave(text(addressTo));
    }

    @Step("Заполняет ФИО получателя")
    public ShipmentsPage fillAddresseeName(String recipientName) {
        setRecipientName.setValue(recipientName).pressEscape();

        assertThat(setRecipientName.getValue()).isEqualTo(recipientName);
        return this;
    }

    @Step("Выбирает вариант ценной посылки и указывает ценность")
    public ShipmentsPage setValueOfParcel(String value) {
        madeActiveValuedParcelCheckbox.click();
        setValueAmount.setValue(value);

        assertThat(setValueAmount.getValue()).isEqualTo(value);
        return this;
    }

    @Step("Выбирает способ оплаты В отделении")
    public void choosePaymentMethod() {
        choosePaymentMethodAtPostOffice.click();

        assertThat(getSendingTrackingNumber.getText()).isEqualTo("Получить трек-номер");
    }

    @Step("Проверяем что сумма Итого верна")
    public void checkTotalValueAmount(String sendTotalAmount, String sendTariffAmount, String addCostAmount) {

        $("[data-testid='parcels.spinner']").should(disappear, Duration.ofSeconds(5));
        assertThat(totalAmount.sibling(0).$("div").getText()).
                isEqualTo(sendTotalAmount);
        assertThat(tariffAmount.sibling(0).getText())
                .isEqualTo(sendTariffAmount);
        assertThat(extraAmount.sibling(0).$("span").getText())
                .isEqualTo(addCostAmount);
    }

    @Step("Заполнить то кого")
    public void fillSenderDetails(String addressFrom) {

        setSenderAddress.setValue(addressFrom).press(Keys.ARROW_DOWN).pressEnter();
    }

    @Step("Заполнить кому")
    public void fillAddresseeDetails(String addressTo) {

        setRecipientAddress.setValue(addressTo).press(Keys.ARROW_DOWN).pressEnter();
    }

    @Step("Заполнить вес")
    public void fillItemWeight(String weight) {

        setWeight.setValue(weight).press(Keys.ARROW_DOWN).pressEnter();
    }


}
