package data;

public enum SubmenuSections {
    SEND("Отправить"),
    COLLECT("Получить"),
    PAYMENT_AND_TRASFERS("Платежи и переводы"),
    SERVICES_IN_OFFICE("Услуги в отделениях"),
    SERVICES_ONLINE("Онлайн-сервисы");

    private String name;

    SubmenuSections(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
