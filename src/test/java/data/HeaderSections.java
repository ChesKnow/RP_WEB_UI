package data;

public enum HeaderSections {

    TITLE("Почта России"),
    BUSINESS("Бизнесу"),
    PHYSICAL_PERSONAL("Частным лицам"),
    OFFICES("Отделения"),
    HELP("Помощь"),
    SIGN_IN("Войти");

    private String name;

    HeaderSections(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
