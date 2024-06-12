package pages;

import com.codeborne.selenide.SelenideElement;
import config.AuthConfig;
import io.qameta.allure.Step;
import org.aeonbits.owner.ConfigFactory;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.assertj.core.api.Assertions.assertThat;

public class LoginPage {
    private final SelenideElement
            username = $("#username"),
            user_password = $("#userpassword"),
            submit_btn = $("button").$(withText("Войти")),
            title = $("#__next");

    AuthConfig authConfig = ConfigFactory.create(AuthConfig.class, System.getProperties());


    @Step("Открывает страницу регистрации")
    public LoginPage openLoginPage() {
        open("");
        title.shouldHave(text("Почта России"));

        return this;
    }

    @Step("Вводит логин и пароль и нажимает Войти")
    public void enterCredentialsWithSubmit() {
       username.setValue(authConfig.getUserName());
        user_password.setValue(authConfig.getUserPassword());
        assertThat(authConfig.getUserName()).isEqualTo(username.getText());
        assertThat(authConfig.getUserPassword()).isEqualTo(user_password.getText());
        submit_btn.click();
    }

    @Step("Пользователь вводит свой логин и пароль")
    public void enterUserCredentials() {
        username.setValue(authConfig.getUserName());
        user_password.setValue(authConfig.getUserPassword());
    }

    @Step("Нажимает кнопку Войти")
    public void SimpleSubmitButton() {
        submit_btn.click();

    }
}
