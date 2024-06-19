package pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import data.HeaderSections;
import data.SubmenuSectionSend;
import data.SubmenuSectionServicesOnline;
import data.SubmenuSections;
import io.qameta.allure.Step;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.CollectionCondition.texts;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byTagAndText;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.WebDriverConditions.url;
import static org.assertj.core.api.Assertions.assertThat;

public class MainPage {

    private final SelenideElement
            title = $("#__next"),
            searchOnSite = $("[placeholder='Поиск по сайту']"),
            service = $("[data-testid='submenu']"),
            headerTitle = $("[class*='HeaderSiteTitle']"),
            searchButton = $("[data-testid=search-toggle-button]");;

    private final ElementsCollection serviceMenu = $$("[data-submenu=submenu]");


    @Step("Открыть главную страницу")
    public MainPage openMainPage() {
        open("");
        title.shouldHave(text(HeaderSections.TITLE.getName()));
        return this;
    }

    @Step("Нажимает на лупу в верхнем углу экрана для поиска по сайту")
    public MainPage startSearchOnSite() {
        searchButton.click();
        searchOnSite.shouldBe(visible);
        return this;

    }

    @Step("Вводит данные для поиска")
    public MainPage enterSearchData(String searchData) {
        searchOnSite.setValue(searchData);
        searchOnSite.shouldHave(value(searchData));
        searchOnSite.pressEnter();
        return this;
    }

    @Step("Выбирает в результатах поиска ссылку Сроки доставки")
    public void chooseSearchDataInSearchResults(String linkToResults) {
    $("[href="+linkToResults+"]").click();

    }


    @Step("Выбирает Подписаться на журнал в меню Онлайн-услуги")
    public void chooseSubscribeToMagazineInPopupMenu() {
        serviceMenu.findBy(text(SubmenuSections.SERVICES_ONLINE.getName())).hover();
        $(byTagAndText("span", SubmenuSectionServicesOnline.SUBSCRIBE_FOR_MAGAZINE.getName())).click();
        switchTo().window(1);
        actions().sendKeys(Keys.ESCAPE).perform();
        headerTitle.shouldHave(text(HeaderSections.SUBSCRIBE_FOR_PERIODICAL.getName()));

    }

    @Step("Наводит мышку на заголовок Отправить и в выпадающем меню выбирает Посылку")
    public void chooseParcelInPopupMenuSending(String parcelUrl) {
        serviceMenu.findBy(text(SubmenuSections.SEND.getName())).hover();
        service.shouldBe(visible);
        $(byTagAndText("span", SubmenuSectionSend.PARCEL.getName())).click();
        webdriver().shouldHave(url(parcelUrl));
    }

}
