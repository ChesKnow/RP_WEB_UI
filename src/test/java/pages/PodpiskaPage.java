package pages;

import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.Keys;
import tests.ui.TestBase;

import java.time.Duration;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byTagAndText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.WebDriverConditions.currentFrameUrl;
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
    public void chooseMonthOfSubsription(String Sept) {
        $(byTagAndText("div", "Июл")).click();
        $(byTagAndText("div", "Авг")).click();
        $(byTagAndText("div", "Окт")).click();
        $(byTagAndText("div", "Ноя")).click();
        $(byTagAndText("div", "Дек")).click();
    }


}
