package com.lvtn.util;

import java.util.Date;

public class ReportInfo {
    private double data;
    private Date time;
    private String des;

    public ReportInfo(double data, Date time, String des) {
        this.data = data;
        this.time = time;
        this.des = des;
    }

    public double getData() {
        return data;
    }

    public Date getTime() {
        return time;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getDes() {
        return des;
    }
}
