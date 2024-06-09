package pages;

import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.Keys;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byTagAndText;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.WebDriverConditions.url;

public class PodpiskaPage {


    public PodpiskaPage openPodpiskaPage() {
        open("https://podpiska.pochta.ru/");
        return this;

    }

    @Step("Вводит полное наименование журнала")
    public void searchToMagazineByFullTitle(String magazine_title) {

        $("[name='query']").setValue(magazine_title).pressEnter();
        Assertions.assertEquals("Результаты по запросу «Наука и жизнь» 1 результат", $("h1").getText());
    }

    @Step("Выбирает журнал и переходит к форме заполнения данных")
    public void confirmChooseExpectedMagazine() {
        $(byTagAndText("h3", "Наука и жизнь")).click();
    }

    @Step("Заполняет данные получателя, предусловия: для себя и по адресу")
    public void fillRecipientData(String fio, String address) {
        $("[name='sponsorship']").shouldHave(value("SELF"));
        $("[name='deliveryType']").shouldHave(value("TO_ADDRESSEE"));
        $("[placeholder='ФИО Получателя']").setValue(fio);
        $("[placeholder='Адрес']").setValue(address).press(Keys.ARROW_DOWN).pressEnter();
    }

    @Step("Выбирает месяц подписки только Сентябрь")
    public void chooseSeptemberMonthOfSubsription() { //todo switchcasedepent on month choosen
        $(byTagAndText("div", "Июл")).click();
        $(byTagAndText("div", "Авг")).click();
        $(byTagAndText("div", "Окт")).click();
        $(byTagAndText("div", "Ноя")).click();
        $(byTagAndText("div", "Дек")).click();
    }

    @Step("Сверка суммы за месяц, общей суммы за полугодие и итоговрй суммы")
    public void checkAmountSumAndPutMagazineToCart(String amount, String full_amount, String total_amount) {
        Assertions.assertEquals($$("[class*='PublicationFormPricesBody']").get(0).getText(), amount);
        Assertions.assertEquals($$("[class*='PublicationFormPricesBody']").get(1).getText(), full_amount);
        Assertions.assertEquals($("[class*='PublicationFormFullPriceRegular']").getText(), total_amount);

        $("[class*='PublicationFormSubmitButton']").click();

    }

    @Step("Переходит в корзину")
    public void redirectToCart() {
        $("a[href='/cart']").click();
        webdriver().shouldHave(url("https://podpiska.pochta.ru/cart"));
    }

    @Step("Сверка данных ФИО, адреса, названия журнала, месяц и суммы перед оплатой")
    public void checkAddresseeAndAmountIsCorrect(String magazine_title, String fio, String address, String month, String amount) {
        Assertions.assertEquals($("[class*='CartItemTitle']").getText(), magazine_title);
        Assertions.assertEquals($$("[class*='CartItemText']").get(3).getText(), fio);
        $$("[class*='CartItemText']").get(2).shouldHave(text(address));
        //Assertions.assertTrue($$("[class*='CartItemTitle']").get(2).getText().contains(address));
        $$("[class*='CartItemText']").get(0).shouldHave(text(month));
        //Assertions.assertTrue($$("[class*='CartItemTitle']").get(0).getText(), contains(month));

        Assertions.assertEquals($("[class*='CartItemPrice']").getText(), amount);
        Assertions.assertEquals($("[class*='CartItemPrice']").getText(), $("[class*='CartTotalPrice']").getText());
    }

    @Step("Пользователь передумал и меняет данные находясь в корзине")
    public void customerRemindAndAddNovemberMonth() {
        $("[title='Редактировать']").click();
        $("[class*='PublicationFormFullPriceRegular']").shouldHave(text("503,68 ₽"));

        $(byTagAndText("div", "Ноя")).click();
        $("[class*='PublicationFormFullPriceRegular']").shouldHave(text("1007,36 ₽"));

        $("[class*='PublicationFormSubmitButton']").click();

    }

    @Step("Нажимает кнопку Оплатить(переход к авторизации")
    public void customerStartBuyProcess() {
        $(byTagAndText("span", "Оплатить")).click();
    }

    @Step("Пользователь удаляет товар из корзины")
    public void customerDeleteGoodsFromCart() {
        $("[title='Удалить']").click();
        $("[class*='CartTotalPrice']").shouldHave(text("0,00 ₽"));
    }

}
