package com.lvtn.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table
public class DHT {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private double temp;
    private double humid;
    @ManyToOne
    @JoinColumn(name = "house_id", nullable = false)
    private House house;
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date date;

    public DHT() {
    }

    public DHT(double temp, double humid, House house) {
        this.temp = temp;
        this.humid = humid;
        this.date = new Date();
        this.house = house;
    }

    public DHT(double temp, double humid,Date date, House house) {
        this.temp = temp;
        this.humid = humid;
        this.date = date;
        this.house = house;
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public double getHumid() {
        return humid;
    }

    public void setHumid(double humid) {
        this.humid = humid;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
