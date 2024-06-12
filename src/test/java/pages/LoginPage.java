package pages;

import com.codeborne.selenide.SelenideElement;
import config.AuthConfig;
import io.qameta.allure.Step;
import org.aeonbits.owner.ConfigFactory;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;

public class LoginPage {
    private final SelenideElement
            username = $("#username"),
            user_password = $("#userpassword"),
            submit_btn = $("button").$(withText("Войти"));

    AuthConfig authConfig = ConfigFactory.create(AuthConfig.class, System.getProperties());



    @Step("Вводит логин и пароль и нажимает Войти")
    public void enterCredentialsWithSubmit() {
       username.setValue(authConfig.getUserName());
        user_password.setValue(authConfig.getUserPassword());
        submit_btn.click();
    }

    public void enterUserCredentials() {
        username.setValue(authConfig.getUserName());
        user_password.setValue(authConfig.getUserPassword());
    }

    public void SimpleSubmitButton() {
        submit_btn.click();

    }
}
