package tests.ui;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.Keys;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverConditions.url;

public class WebSteps {

    @Step("Пользователь заходит на главную страницу pochta.ru")
    public void openMainPageWithChecking() {
        open("");
        webdriver().shouldHave(url("https://www.pochta.ru/"));
    }

    @Step("Наводит мышку на заголовок Отправить и в выпадающем меню выбирает Посылку")
    public void chooseParcelInPopupMenuSending() {
        $$("[data-submenu=submenu]").findBy(text("Отправить")).hover();
        $(byTagAndText("span", "Посылку")).click();
    }

    @Step("Проверяем, что перешли на страницу создания отправления")
    public void checkRedirectedToShipmentsPage() {
        webdriver().shouldHave(url("https://www.pochta.ru/shipment?type=PARCEL"));
    }

    @Step("Заполнеяет минимально необходимые поля для посылки обычновенной")
    public void fillMinimumRequiredFieldsForParcel() {
        $("#addressFrom").setValue("г Москва Варшавское шоссе 37");
        $(byTagAndText("span", "115127")).click();
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

@Step("Вводит логин и пароль")
    public void enterCredentials() {
        $("#username").setValue("roman.chesanov@russianpost.ru");
        $("#userpassword").setValue("Pochtalion*23");
        $("button").$(withText("Войти")).click();
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
            $("[name='insurance']").shouldBe(visible, Duration.ofSeconds(3)).click();
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

    @Step("Выбирает Подписаться на журнал в меню Онлайн-услуги")
    public void chooseSubscribeToMagazineInPopupMenu() {
        $$("[data-submenu=submenu]").findBy(text("Онлайн-сервисы")).hover();
        $(byTagAndText("span", "Подписаться на газету или журнал")).click();
        switchTo().window(1);
        actions().sendKeys(Keys.ESCAPE);
        //sleep(5000);
        //switchTo().frame(1);
        //$(byTagAndText("span", "Да, этой мой город")).click();

    }
}
