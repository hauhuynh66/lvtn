package com.lvtn.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.List;

@Entity
public class House {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;
    public String name;
    @OneToMany(mappedBy = "house")
    private List<DHT> dhtList;
    @OneToMany(mappedBy = "house")
    private List<Misc> miscList;

    public House() {
    }

    public House(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<DHT> getDhtList() {
        return dhtList;
    }

    public void setDhtList(List<DHT> dhtList) {
        this.dhtList = dhtList;
    }

    public List<Misc> getMiscList() {
        return miscList;
    }

    public void setMiscList(List<Misc> miscList) {
        this.miscList = miscList;
    }
}
