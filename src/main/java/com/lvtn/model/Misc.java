package com.lvtn.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table
public class Misc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private double smoke;
    private double light;
    @ManyToOne
    @JoinColumn(name = "house_id", nullable = false)
    private House house;
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date date;

    public Misc() {
    }

    public Misc(double smoke, double light, House house) {
        this.smoke = smoke;
        this.light = light;
        this.date = new Date();
        this.house = house;
    }

    public Misc(double smoke, double light, Date date, House house) {
        this.smoke = smoke;
        this.light = light;
        this.date = date;
        this.house = house;
    }

    public double getSmoke() {
        return smoke;
    }

    public void setSmoke(double smoke) {
        this.smoke = smoke;
    }

    public double getLight() {
        return light;
    }

    public void setLight(double light) {
        this.light = light;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
