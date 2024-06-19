package data;

public enum ShortMonthsNames {
    JAN("Янв"),
    FEB("Фев"),
    MAR("Мар"),
    APR("Апр"),
    MAY("Май"),
    JUN("Июн"),
    JUL("Июл"),
    AUG("Авг"),
    SEP("Сен"),
    OKT("Окт"),
    NOV("Ноя"),
    DEC("Дек");



    private String name;

    ShortMonthsNames(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
