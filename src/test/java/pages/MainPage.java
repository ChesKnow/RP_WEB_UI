package pages;

import com.codeborne.pdftest.PDF;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

import static com.codeborne.selenide.CollectionCondition.texts;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byTagAndText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.WebDriverConditions.url;

public class MainPage {
    //locators
    private final SelenideElement
            title = $("#__next"),
            trackingbar = $("#barcode"),
            loop = $("[aria-label='Отследить']");

    //actions
    @Step("Открываем главную страницу")
    public void openMainPageAndCheckTitle() {
        open("");
        title.shouldHave(text("Почта России"));
    }
    @Step("Открываем главную страницу и проверяем трекер через Enter")
    public void trackingByPressEnter(String barcode) {
        open("");
        trackingbar.setValue(barcode).pressEnter();
        $("#page-tracking").shouldHave(text(barcode));
    }
    @Step("Открываем главную страницу и проверяем трекер через лупу")
    public void trackingByClickOnLoop(String barcode) {
        open("");
        trackingbar.setValue(barcode);
        loop.click();
        $("#page-tracking").shouldHave(text(barcode));
    }
    @Step("Открываем главную страницу и вводим неверный ШПИ - отслеживание не происходит")
    public void trackingNotStartingTest(String barcode) {
        open("");
        trackingbar.setValue(barcode).pressEnter();
        title.shouldHave(text("Почта России"));
    }


    static Stream<Arguments> mainPageShouldContainsAllOfStandardSections() {
        return Stream.of(
                Arguments.of(List.of("Отправить", "Получить", "Платежи и переводы", "Услуги в отделениях", "Онлайн-сервисы"))

        );
    }
    @MethodSource()
    @ParameterizedTest
    @Tag("WEB")
    @DisplayName("На главной странице должен отображаться список секций")
    void mainPageShouldContainsAllOfStandardSections(List<String> expectedSections) {
        open("https://www.pochta.ru/");
        sleep(5000);
        $$("[data-submenu='submenu']").filter(visible).shouldHave(texts(expectedSections));

    }

    @Attachment(value = "Screenshot", type = "image/png", fileExtension = "png")
    public byte[] takeScreenshot() {
        return ((TakesScreenshot) WebDriverRunner.getWebDriver()).getScreenshotAs(OutputType.BYTES);
    }

    @Step("Пользователь заходит на главную страницу pochta.ru")
    public void openMainPageWithChecking() {
        open("");
        webdriver().shouldHave(url("https://www.pochta.ru/"));
    }

    @Step("Нажимает на лупу в верхнем углу экрана для поиска по сайту")
    public void startSearchOnSite() {
        $("[data-testid=search-toggle-button]").click();
    }

    @Step("Вводит данные для поиска")
    public void enterSearchData(String search_data) {
        $("[placeholder='Поиск по сайту']").setValue(search_data).pressEnter();
    }

    @Step("Выбирает в результатах поиска ссылку Сроки доставки")
    public void chooseSearchDataInSearchResults(String search_data) {
    $("[href='/support/post-rules/delivery-terms']").click();
        webdriver().shouldHave(url("https://www.pochta.ru/support/post-rules/delivery-terms"));
    }



@Step("Возвращается обратно на страницу")
    public void returnBackViaBrowserBackButton() {
        back();
}


    @Step("Выбирает Подписаться на журнал в меню Онлайн-услуги")
    public void chooseSubscribeToMagazineInPopupMenu() {
        $$("[data-submenu=submenu]").findBy(text("Онлайн-сервисы")).hover();
        $(byTagAndText("span", "Подписаться на газету или журнал")).click();
        switchTo().window(1);
        //sleep(5000);
        actions().sendKeys(Keys.ESCAPE);

        //switchTo().frame(1);
        //$(byTagAndText("span", "Да, этой мой город")).click();

    }

    @Step("Наводит мышку на заголовок Отправить и в выпадающем меню выбирает Посылку")
    public void chooseParcelInPopupMenuSending() {
        $$("[data-submenu=submenu]").findBy(text("Отправить")).hover();
        $(byTagAndText("span", "Посылку")).click();
    }

}
