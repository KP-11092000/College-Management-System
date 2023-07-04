package com.example.college.Library;



public class Reservation {
    private String name;
    private String color;

    public Reservation() {
        // Required empty constructor for Firebase
    }

    public Reservation(String name,String color) {
        this.name = name;
        this.color=color;

    }

    public String getName() {
        return name;
    }
    public String   getColor(){return color;}
    public void setName(String name) {
        this.name = name;
    }
    public void setColor(String color){this.color=color;}


}


