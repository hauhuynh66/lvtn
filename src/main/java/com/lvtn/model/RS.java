package com.lvtn.model;

public class RS {
    private String name;
    private double t;
    private double h;

    public RS() {
    }

    public RS(String name, double t, double h) {
        this.name = name;
        this.t = t;
        this.h = h;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getT() {
        return t;
    }

    public void setT(double t) {
        this.t = t;
    }

    public double getH() {
        return h;
    }

    public void setH(double h) {
        this.h = h;
    }
}
