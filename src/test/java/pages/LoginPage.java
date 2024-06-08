package pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class LoginPage {
    private final SelenideElement username = $("");
    private final SelenideElement user_password = $("");
    private final SelenideElement submit_btn = $("");

    public void enterUserCredentials(String name, String password) {
        username.setValue(name);
        user_password.setValue(password);
    }

    public void chooseSimpleSubmitButton() {
        submit_btn.click();
        //return new MainPage();
    }

}
