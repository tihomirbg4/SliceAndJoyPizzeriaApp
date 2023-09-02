package com.pizzeria.pizzeriawebapp.enums;

public enum OrderStatusEnum {
    PENDING("Поръчката е направена"),
    PROCESSING("Подготовка"),
    BAKING("Печене"),
    QUALITY_CHECK("Проверка на качеството"),
    OUT_FOR_DELIVERY("За доставка"),
    DELIVERED("Доставена"),
    CANCELED("Отказана");

    private final String status;

    OrderStatusEnum(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}