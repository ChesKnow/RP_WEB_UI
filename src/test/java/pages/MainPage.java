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

@Step("Прокручивает страницу вниз и скачивает файл по ссылке")
    public void scrollToTheBottomAndClickToTheLink(String link_name) throws IOException {

    File downloaded = $(byTagAndText("a", link_name)).download();
    PDF pdf = new PDF(downloaded);
    Assertions.assertEquals("Боробова Динна Леонидовна", pdf.author);

}

@Step("Возвращается обратно на страницу")
    public void returnBackViaBrowserBackButton() {
        back();
}

@Step("Нажимает на кнопку Посылки под текстом Рассчитать стоимость доставки")
    public void chooseParcelDeliveryTerms() {
        $$("[href='https://www.pochta.ru/parcels']").findBy(visible).click();
        //switchTo().window(1);
}

@Step("Отображается предупреждение об условиях приема к пересылке")
    public void canSeeWarningRegardingAcceptanceCondition() {
    $("[data-component='NotificationWrapper']").$("div", 1).shouldBe(visible);
}

@Step("Ниже вариантов доставки отображается строчка об особенностях сроков доставки")
    public void canSeeWarningRegardingDeliveryTerms() {
        $$("#shipment-sidebar").findBy(text("Сроки доставки указаны без учёта дня приёма, а также не включают выходные и праздничные дни.")).shouldBe(visible);
}

}
