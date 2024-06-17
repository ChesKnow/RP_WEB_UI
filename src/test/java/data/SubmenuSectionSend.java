package data;

public enum SubmenuSectionSend {
    LETTER("Письмо"),
    PARCEL("посылку"),
    POSTCARD("Открытку");

    private String name;

    SubmenuSectionSend(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
