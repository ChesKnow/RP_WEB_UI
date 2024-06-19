package pages;

import com.codeborne.selenide.SelenideElement;
import config.AuthConfig;
import io.qameta.allure.Step;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.Assertions;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byTagAndText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.assertj.core.api.Assertions.assertThat;

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
        assertThat(authConfig.getUserName()).isEqualTo(username.getValue());
        assertThat(authConfig.getUserPassword()).isEqualTo(user_password.getValue());
        submit_btn.click();
    }


}
