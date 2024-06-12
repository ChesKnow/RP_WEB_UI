package tests.ui;

import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import pages.LoginPage;

import static org.assertj.core.api.Assertions.assertThat;

public class LoginPageTests  {

    LoginPage loginPage = new LoginPage();

    @Test
    @Feature("Регистрация")
    @Story("Пользователь может авторизоваться")
    @DisplayName("Пользователь, у которого есть УЗ может зайти через логин и пароль")
    @Owner("Роман Чесанов (@ChesKnow)")
    @Severity(SeverityLevel.CRITICAL)
    @Tag("login")
    @Tag("positive")
    void SuccessfulLogin() {
        loginPage.openLoginPage();
        loginPage.enterUserCredentials();

    }

}
