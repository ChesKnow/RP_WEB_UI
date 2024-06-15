package pages;

import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import tests.ui.TestBase;

import java.time.Duration;

import static com.codeborne.selenide.CollectionCondition.sizeNotEqual;
import static com.codeborne.selenide.CollectionCondition.texts;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byTagAndText;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverConditions.url;
import static org.assertj.core.api.Assertions.assertThat;

public class ShipmentsPage {

    @Step("Проверяем, что перешли на страницу создания отправления")
    public void checkRedirectedToShipmentsPage() {
        webdriver().shouldHave(url("https://www.pochta.ru/shipment?type=PARCEL"));
    }

    @Step("Заполнеяет минимально необходимые поля для посылки обычновенной")
    public void fillMinimumRequiredFieldsForParcel(String addressFrom, String addressTo, String weight) {
        $("#addressFrom").setValue(addressFrom);

        $(byTagAndText("span", "115127")).shouldBe(visible, Duration.ofSeconds(3)).click();
        $("#addressTo").setValue(addressTo);
        $(byTagAndText("span", "141301")).click();
        $("#weight").setValue(weight);
        $(byTagAndText("span", "1 кг")).click();
        $("[data-testid=dimension-typical]").click();
        $("[data-testid=dimension-typical-size-m]").click();
        $("[type=submit]").click();

        assertThat($("#addressFrom").getText()).isEqualTo(addressFrom);
        assertThat($("#addressTo").getText()).isEqualTo(addressTo);
        assertThat($("#weight").getValue()).isEqualTo(weight);

    }

    @Step("Выбрать вид пересылки Ускоренный")
    public void setForwardingType() {
        $("[data-testid='delivery-type.rapid']").click();
    }

    @Step("Сверяем что сумма тарифа у выбранного типа пересылки соответствует сумме в виджете Оформления")
    public void checkTarifValueCorrespondtoForwardingTypeAndConfirm() {
        String value1 = $("[data-testid='parcels.delivery-type-price']").$("span").
                getValue();
        String value2 = $("[data-testid='parcels.ticket-item']").$("span").
                getValue();
        Assertions.assertEquals(value2, value1);
        $("#validateButton").click();
    }

    @Step("Смотрит варианты сроков доставки")
    public void checkDeliveryTerms() {
        $$("[data-testid='parcels.delivery-type-time']").
                shouldBe(sizeNotEqual(0));

    }

    @Step("Отображается предупреждение об условиях приема к пересылке")
    public void canSeeWarningRegardingAcceptanceCondition() {
        $("[data-component='NotificationWrapper']").$("div", 1).
                shouldBe(visible);
    }

    @Step("Ниже вариантов доставки отображается строчка об особенностях сроков доставки")
    public void canSeeWarningRegardingDeliveryTerms() {
        $$("#shipment-sidebar")
                .findBy(text("Сроки доставки указаны без учёта дня приёма, а также не включают выходные и праздничные дни."))
                .shouldBe(visible);
    }

    @Step("Проверяем правильность введенных параметров посылки")
    public void checkCorrectnessOfPacelFields(String addressFrom, String addressTo) {
        $("#shipment-sidebar span").shouldHave(text("Москва — Сергиев Посад."));
        $("h2").parent().$("div", 1).shouldHave(text("Ускоренный, до 1–2 дней"));
        $("#senderAddress").shouldHave(text(addressFrom));
        $("#recipientAddress").shouldHave(text(addressTo));

    }

    @Step("Заполняет ФИО получателя")
    public void fillAddresseeName() {
        $("#recipientName").setValue("Иванов Иван Иванович").pressEscape();
        assertThat($("#recipientName").getValue()).isEqualTo("Иванов Иван Иванович");
    }

    @Step("Выбирает вариант ценной посылки и указывает ценность")
    public void chooseCheckboxValuedParcel() {
        $(byTagAndText("span", "Ценная посылка")).click();
        $("#insuranceSum").setValue("100");

        assertThat($("#insuranceSum").getValue()).isEqualTo("100");
    }

    @Step("Выбирает способ оплаты В отделении")
    public void choosePaymentMethod() {
        $("[data-testid='payment-method-select-formless'").click();
        $("[data-testid='parcels.ticket-submit-button-desktop']").$("span").
            shouldHave(text("Получить трек-номер"));
    }

    @Step("Проверяем что сумма Итого верна")
    public void checkTotalValueAmount() {
        sleep(2000);
        assertThat($(byTagAndText("div", "Итого")).sibling(0).$("div").getText()).
                isEqualTo("369,60 ₽");
        assertThat($(byTagAndText("div", "Ускоренный тариф")).sibling(0).getText())
                .isEqualTo("366,00 ₽");
        assertThat($("[data-testid='parcels.ticket-extra-expand']").sibling(0).$("span").getText())
                .isEqualTo("3,60 ₽");
    }

}
