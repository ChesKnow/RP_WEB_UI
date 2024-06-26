package pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import data.ShortMonthsNames;
import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.Keys;

import java.time.Duration;

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
            searchField = $("[name='query']"),
            magazineFullPrice = $("[class*='PublicationFormFullPriceRegular']"),
            buyButton = $("[class*='PublicationFormSubmitButton']"),
            cartItemName = $("[class*='CartItemTitle']"),
            cartItemPrice = $("[class*='CartItemPrice']"),
            cartItemTotalPrice = $("[class*='CartTotalPrice']"),
            makeChanges = $("[title='Редактировать']"),
            fullPrice = $("[class*='PublicationFormFullPriceRegular']"),
            acceptChanges = $("[class*='PublicationFormSubmitButton']"),
            chooseBuyForWhom = $("[name='sponsorship']"),
            chooseMethodOfDelivery = $("[name='deliveryType']"),
            goToTheCart = $("a[href='/cart']"),
            startBuyProcess = $(byTagAndText("span", "Оплатить")),
            deleteGoods = $("[title='Удалить']");

    private final ElementsCollection
            publicationForm = $$("[class*='PublicationFormPricesBody']"),
            cartItemsFields = $$("[class*='CartItemText']");




    @Step("Поиск по полному наименованию журнала")
    public PodpiskaPage searchToMagazineByFullTitle(String magazineTitle) {

        searchField.setValue(magazineTitle).pressEnter();
        return this;
    }

    @Step("Подтвердить выбор журнала")
    public PodpiskaPage confirmChooseExpectedMagazine(String magazineTitle) {
        $(byTagAndText("h3", magazineTitle)).click();
        return this;
    }

    @Step("Заполнить данные получателя")
    public PodpiskaPage fillRecipientData(String fio, String address) {

        addresseeFio.setValue(fio);
        addresseeAddress.setValue(address).press(Keys.ARROW_DOWN).pressEnter();

        assertThat(addresseeFio.getValue()).isEqualTo(fio); // to test
        assertThat(addresseeAddress.getValue()).isEqualTo(address); // to test

        return this;
    }

       @Step("Выбрать кому купить журнал")
               public PodpiskaPage chooseBuyForWhom(String toWhom) {
           chooseBuyForWhom.shouldHave(value(toWhom));
           return this;//assert to test
       }


       @Step("Выбрать метод доставки журнала")
               public PodpiskaPage chooseMethodOfDelivery(String methodOfDelivery) {
           chooseMethodOfDelivery.shouldHave(value(methodOfDelivery));
           return this;//assert to test
       }




    @Step("Выбрать месяц подписки")
    public PodpiskaPage chooseMonthOfSubsription() {

        $(byTagAndText("div", "Июл")).click();
        $(byTagAndText("div", "Авг")).click();
        $(byTagAndText("div", "Окт")).click();
        $(byTagAndText("div", "Ноя")).click();
        $(byTagAndText("div", "Дек")).click();


        return this;
    }

    @Step("Добавить месяц подписки")
    public PodpiskaPage addMonthOfSubsription() {

        $(byTagAndText("div", "Ноя")).click();
        return this;
    }

    @Step("Сверка суммы за месяц, общей суммы за полугодие и итоговой суммы")
    public PodpiskaPage checkAmountSum(String amount, String fullAmount, String totalAmount) {

        Assertions.assertEquals(publicationForm.get(0).getText(), amount);
        Assertions.assertEquals(publicationForm.get(1).getText(), fullAmount);
        Assertions.assertEquals(magazineFullPrice.getText(), totalAmount);

        return this;
    }

    @Step("Положить журнал в корзину")
    public PodpiskaPage putGoodsIntoTheCart() {
        buyButton.shouldBe(clickable, Duration.ofSeconds(3)).click();
        return this;
    }

    @Step("Переход в корзину")
    public PodpiskaPage redirectToCart() {
        goToTheCart.click();
        webdriver().shouldHave(url("https://podpiska.pochta.ru/cart"));
        return this;
    }

    @Step("Сверка данных ФИО, адреса, названия журнала, месяц и суммы перед оплатой")
    public PodpiskaPage checkDataInTheCart(String magazineTitle, String fio, String address, String month, String amount) {

        Assertions.assertEquals(cartItemName.getText(), magazineTitle);
        Assertions.assertEquals(cartItemsFields.get(3).getText(), fio);

        cartItemsFields.get(2).shouldHave(text(address));
        cartItemsFields.get(0).shouldHave(text(month));

        Assertions.assertEquals(cartItemPrice.getText(), amount);
        Assertions.assertEquals(cartItemPrice.getText(), cartItemTotalPrice.getText());

        return this;
    }

    @Step("Нажать кнопку Редактировать")
    public void changeDataInTheCart() {
        makeChanges.click();
        $("h2[class*='PopupHeader']").shouldHave(text("Редактирование"));
    }


    @Step("Нажать кнопку Сохранить")
            public void acceptChanges(){
        acceptChanges.click();
        $("h1").shouldHave(text("Корзина"));
    }


    @Step("Нажать кнопку Оплатить")
    public void startBuyProcess() {
        startBuyProcess.click();
        $("h2").shouldHave(text("Вход с Почта ID"));
    }

    @Step("Пользователь удаляет товар из корзины")
    public void deleteGoodsFromCart(String noAmount) {

        deleteGoods.click();
        $("[class*='CartTotalPrice']").shouldHave(text("0,00 ₽"));
    }

}
