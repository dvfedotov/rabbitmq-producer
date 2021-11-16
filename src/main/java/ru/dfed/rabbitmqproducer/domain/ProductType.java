package ru.dfed.rabbitmqproducer.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum ProductType {
    @JsonProperty("liquid")
    LIQUID("liquid"),

    @JsonProperty("countable")
    COUNTABLE("countable");

    private String type;

    ProductType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
