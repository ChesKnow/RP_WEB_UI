package pages;

import com.codeborne.pdftest.PDF;
import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import tests.ui.TestBase;

import java.io.File;
import java.io.IOException;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byTagAndText;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverConditions.url;

public class SupportPage extends TestBase {

    @Step("Прокручивает страницу вниз и скачивает файл по ссылке")
    public void scrollToTheBottomAndClickToTheLink(String link_name) throws IOException {

        File downloaded = $(byTagAndText("a", link_name)).download();
        PDF pdf = new PDF(downloaded);

        Assertions.assertEquals("Боробова Динна Леонидовна", pdf.author);
    }

    @Step("Нажимает на кнопку Посылки под текстом Рассчитать стоимость доставки")
    public void chooseParcelDeliveryTerms() {
        $$("[href='https://www.pochta.ru/parcels']").findBy(visible).click();
        webdriver().shouldHave(url("https://www.pochta.ru/shipment?type=PARCEL"));
    }
}
