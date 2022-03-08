package com.example.medi_assist;

public class Reminder {
    private String name;
    private String time;



    public Reminder(String name, String time) {
        this.name = name;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public String getTime() {
        return time;
    }
}
