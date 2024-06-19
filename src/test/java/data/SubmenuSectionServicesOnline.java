package data;

public enum SubmenuSectionServicesOnline {
    SUBSCRIBE_FOR_MAGAZINE("Подписаться на газету или журнал"),
    SEND_TELEGRAM("Отправить телеграмму"),
    RENT_POSTBOX("Аренда абонементного ящика");

    private String name;

    SubmenuSectionServicesOnline(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
