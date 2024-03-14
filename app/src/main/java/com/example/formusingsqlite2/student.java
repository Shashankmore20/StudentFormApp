package com.example.formusingsqlite2;

import androidx.annotation.NonNull;

public class student {
    int id;
    String roll, name, clas;

    public student(int id, String roll, String name, String clas) {
        this.id = id;
        this.roll = roll;
        this.name = name;
        this.clas = clas;
    }

    public int getId() {
        return id;
    }

    public String getRoll() {
        return roll;
    }

    public String getName() {
        return name;
    }

    public String getClas() {
        return clas;
    }
}

