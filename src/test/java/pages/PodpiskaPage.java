package pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byTagAndText;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.WebDriverConditions.url;
import static org.assertj.core.api.Assertions.assertThat;

public class PodpiskaPage {

    private final SelenideElement
            addresseeFio = $("[placeholder='ФИО Получателя']"),
            addresseeAddress = $("[placeholder='Адрес']"),
            searchField = $("[name='query']");




    @Step("Вводит полное наименование журнала")
    public void searchToMagazineByFullTitle(String magazineTitle) {
        sleep(3000);
        actions().sendKeys(Keys.ESCAPE).perform();
        searchField.setValue(magazineTitle).pressEnter();
    }

    @Step("Выбирает журнал и переходит к форме заполнения данных")
    public void confirmChooseExpectedMagazine(String magazineTitle) {
        $(byTagAndText("h3", magazineTitle)).click();
    }

    @Step("Заполняет данные получателя, предусловия: для себя и по адресу")
    public void fillRecipientData(String fio, String address) {

        addresseeFio.setValue(fio);
        addresseeAddress.setValue(address).press(Keys.ARROW_DOWN).pressEnter();

        assertThat(addresseeFio.getValue()).isEqualTo(fio);
        assertThat(addresseeAddress.getValue()).isEqualTo(address);

        $("[name='sponsorship']").shouldHave(value("SELF"));
        $("[name='deliveryType']").shouldHave(value("TO_ADDRESSEE"));


    }

    @Step("Выбирает месяц подписки только Сентябрь")
    public void chooseSeptemberMonthOfSubsription() { //todo switchcasedepent on month choosen
        $(byTagAndText("div", "Июл")).click();
        $(byTagAndText("div", "Авг")).click();
        $(byTagAndText("div", "Окт")).click();
        $(byTagAndText("div", "Ноя")).click();
        $(byTagAndText("div", "Дек")).click();

        }

    @Step("Сверка суммы за месяц, общей суммы за полугодие и итоговой суммы")
    public void checkAmountSumAndPutMagazineToCart(String amount, String fullAmount, String totalAmount) {
        Assertions.assertEquals($$("[class*='PublicationFormPricesBody']").get(0).getText(), amount);
        Assertions.assertEquals($$("[class*='PublicationFormPricesBody']").get(1).getText(), fullAmount);
        Assertions.assertEquals($("[class*='PublicationFormFullPriceRegular']").getText(), totalAmount);

        $("[class*='PublicationFormSubmitButton']").click();

    }

    @Step("Переходит в корзину")
    public void redirectToCart() {
        $("a[href='/cart']").click();
        webdriver().shouldHave(url("https://podpiska.pochta.ru/cart"));
    }

    @Step("Сверка данных ФИО, адреса, названия журнала, месяц и суммы перед оплатой")
    public void checkAddresseeAndAmountIsCorrect(String magazineTitle, String fio, String address, String month, String amount) {
        Assertions.assertEquals($("[class*='CartItemTitle']").getText(), magazineTitle);
        Assertions.assertEquals($$("[class*='CartItemText']").get(3).getText(), fio);
        $$("[class*='CartItemText']").get(2).shouldHave(text(address));
        $$("[class*='CartItemText']").get(0).shouldHave(text(month));
        Assertions.assertEquals($("[class*='CartItemPrice']").getText(), amount);
        Assertions.assertEquals($("[class*='CartItemPrice']").getText(),
                $("[class*='CartTotalPrice']").getText());
    }

    @Step("Пользователь передумал и меняет данные находясь в корзине")
    public void customerRemindAndAddNovemberMonth(String amount, String newAmount, String shortMonth) {
        $("[title='Редактировать']").click();
        $("h2[class*='PopupHeader']").shouldHave(text("Редактирование"));
        $("[class*='PublicationFormFullPriceRegular']").shouldHave(text(amount));
        $(byTagAndText("div", shortMonth)).click();
        $("[class*='PublicationFormFullPriceRegular']").shouldHave(text(newAmount));
        $("[class*='PublicationFormSubmitButton']").click();
        $("h1").shouldHave(text("Корзина"));

    }

    @Step("Нажимает кнопку Оплатить(происходит переход к авторизации)")
    public void customerStartBuyProcess() {
        $(byTagAndText("span", "Оплатить")).click();
        $("h2").shouldHave(text("Вход с Почта ID"));
    }

    @Step("Пользователь удаляет товар из корзины")
    public void customerDeleteGoodsFromCart(String noAmount) {
        $("[title='Удалить']").click();
        $("[class*='CartTotalPrice']").shouldHave(text(noAmount));
    }

}
