package com.lvtn.util;

import java.util.Date;
import java.util.List;

public class Report {
    private List<ReportInfo> rp;
    private Date from;
    private Date to;

    public Report(List<ReportInfo> rp) {
        this.rp = rp;
    }

    public Report(List<ReportInfo> rp, Date from, Date to) {
        this.rp = rp;
        this.from = from;
        this.to = to;
    }

    public List<ReportInfo> getRp() {
        return rp;
    }

    public void setRp(List<ReportInfo> rp) {
        this.rp = rp;
    }

    public Date getFrom() {
        return from;
    }

    public void setFrom(Date from) {
        this.from = from;
    }

    public Date getTo() {
        return to;
    }

    public void setTo(Date to) {
        this.to = to;
    }
}
