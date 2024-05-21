package pages;

import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class MainPage {
    //locators
    private SelenideElement
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

    @Attachment(value = "Screenshot", type = "image/png", fileExtension = "png")
    public byte[] takeScreenshot() {
        return ((TakesScreenshot) WebDriverRunner.getWebDriver()).getScreenshotAs(OutputType.BYTES);
    }
}
