package pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;

public class LoginPage {
    private final SelenideElement
            username = $("#username"),
            user_password = $("userpassword"),
            submit_btn = $("button").$(withText("Войти"));

    public void enterUserCredentials(String name, String password) {
        username.setValue(name);
        user_password.setValue(password);
    }

    public void SimpleSubmitButton() {
        submit_btn.click();
        //return new MainPage();
    }

}
