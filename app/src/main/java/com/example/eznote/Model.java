package com.example.eznote;

public class Model {
    String name, number;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Model(String name, String number) {
        this.name = name;
        this.number = number;
    }
}
