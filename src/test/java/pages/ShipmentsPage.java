package pages;

import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import tests.ui.TestBase;

import java.time.Duration;

import static com.codeborne.selenide.CollectionCondition.texts;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byTagAndText;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverConditions.url;

public class ShipmentsPage extends TestBase {

    @Step("Проверяем, что перешли на страницу создания отправления")
    public void checkRedirectedToShipmentsPage() {
        webdriver().shouldHave(url("https://www.pochta.ru/shipment?type=PARCEL"));
    }

    @Step("Заполнеяет минимально необходимые поля для посылки обычновенной")
    public void fillMinimumRequiredFieldsForParcel() {
        $("#addressFrom").setValue("г Москва Варшавское шоссе 37");
        actions().click();
        $(byTagAndText("span", "115127")).shouldBe(visible, Duration.ofSeconds(3)).click();
        $("#addressTo").setValue("Сергиев Посад Матросова 7");
        $(byTagAndText("span", "141301")).click();
        $("#weight").setValue("1");
        $(byTagAndText("span", "1 кг")).click();
        //data-testid="shipment.dimensions-tabs"
        $("[data-testid=dimension-typical]").click();
        $("[data-testid=dimension-typical-size-m]").click();
        $("[type=submit]").click();
    }

    @Step("Выбрать вид пересылки Ускоренный")
    public void setForwardingType() {
        $("[data-testid='delivery-type.rapid']").click();
    }

    @Step("Сверяем что сумма тарифа у выбранного типа пересылки соответствует сумме в виджете Оформления")
    public void checkTarifValueCorrespondtoForwardingTypeAndConfirm() {
        String value1 = $("[data-testid='parcels.delivery-type-price']").$("span").getValue();
        String value2 = $("[data-testid='parcels.ticket-item']").$("span").getValue();
        Assertions.assertEquals(value2, value1);
        $("#validateButton").click();
    }

    @Step("Смотрит варианты сроков доставки")
    public void checkDeliveryTerms() {
        $$("[data-testid='parcels.delivery-type-time']").shouldHave(texts("2 дня", "1–2 дня", "1 день"));
    }

    @Step("Отображается предупреждение об условиях приема к пересылке")
    public void canSeeWarningRegardingAcceptanceCondition() {
        $("[data-component='NotificationWrapper']").$("div", 1).shouldBe(visible);
    }

    @Step("Ниже вариантов доставки отображается строчка об особенностях сроков доставки")
    public void canSeeWarningRegardingDeliveryTerms() {
        $$("#shipment-sidebar").findBy(text("Сроки доставки указаны без учёта дня приёма, а также не включают выходные и праздничные дни.")).shouldBe(visible);
    }

    @Step("Проверяем правильность введенных параметров посылки")
    public void checkCorrectnessOfPacelFields() {
        $("#shipment-sidebar span").shouldHave(text("Москва — Сергиев Посад."));
        //Assertions.assertEquals("Москва — Сергиев Посад.", value1);
        $("h2").parent().$("div", 1).shouldHave(text("Ускоренный, до 1–2 дней"));
        //Assertions.assertEquals("Ускоренный, до 1–2 дней. От пункта отправки до пункта выдачи", value2);
        $("#senderAddress").shouldHave(text("г Москва, ш Варшавское, д. 37"));
        $("#recipientAddress").shouldHave(text("обл Московская, г Сергиев Посад, ул Матросова, д. 7"));

    }

    @Step("Заполняет ФИО получателя")
    public void fillAddresseeName() {
        $("#recipientName").setValue("Иванов Иван Иванович").pressEscape();
    }

    @Step("Выбирает вариант ценной посылки и указывает ценность")
    public void chooseCheckboxValuedParcel() {
        $(byTagAndText("span", "Ценная посылка")).click();
        $("#insuranceSum").setValue("100");
    }

    @Step("Выбирает способ оплаты В отделении")
    public void choosePaymentMethod() {
        $("[data-testid='payment-method-select-formless'").click();
    }

    @Step("Проверяем что сумма Итого верна")
    public void checkTotalValueAmount() {
        String totalValue = $(byTagAndText("div", "Итого")).sibling(0).$("div").getValue();
        String tarif_type_value = $(byTagAndText("div", "Ускоренный тариф")).sibling(0).getValue();
        String insurance_added_value = $("[data-testid='parcels.ticket-extra-expand']").sibling(0).$("span").getValue();
    }

}
