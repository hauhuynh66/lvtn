package com.lvtn.model;

import java.util.Date;

public class JsonObject {
    public class temp {
        private double temp;
        private boolean x;
        private boolean y;

        public temp() {
        }

        public double getTemp() {
            return temp;
        }

        public void setTemp(double temp) {
            this.temp = temp;
        }

        public boolean isX() {
            return x;
        }

        public void setX(boolean x) {
            this.x = x;
        }

        public boolean isY() {
            return y;
        }

        public void setY(boolean y) {
            this.y = y;
        }
    }
    public class humid{
        private double humid;
        private boolean x;
        private boolean y;

        public humid() {
        }

        public double getHumid() {
            return humid;
        }

        public void setHumid(double humid) {
            this.humid = humid;
        }

        public boolean isX() {
            return x;
        }

        public void setX(boolean x) {
            this.x = x;
        }

        public boolean isY() {
            return y;
        }

        public void setY(boolean y) {
            this.y = y;
        }
    }
    public class light{
        private double light;
        private boolean x;
        private boolean y;

        public light() {
        }

        public double getLight() {
            return light;
        }

        public void setLight(double light) {
            this.light = light;
        }

        public boolean isX() {
            return x;
        }

        public void setX(boolean x) {
            this.x = x;
        }

        public boolean isY() {
            return y;
        }

        public void setY(boolean y) {
            this.y = y;
        }
    }
    public class smoke{
        private double smoke;
        private boolean x;
        private boolean y;

        public smoke() {
        }

        public double getSmoke() {
            return smoke;
        }

        public void setSmoke(double smoke) {
            this.smoke = smoke;
        }

        public boolean isX() {
            return x;
        }

        public void setX(boolean x) {
            this.x = x;
        }

        public boolean isY() {
            return y;
        }

        public void setY(boolean y) {
            this.y = y;
        }
    }
    private temp temp;
    private humid humid;
    private smoke smoke;
    private light light;
    private Date time;

    public JsonObject() {
    }

    public JsonObject.temp getTemp() {
        return temp;
    }

    public void setTemp(JsonObject.temp temp) {
        this.temp = temp;
    }

    public JsonObject.humid getHumid() {
        return humid;
    }

    public void setHumid(JsonObject.humid humid) {
        this.humid = humid;
    }

    public JsonObject.smoke getSmoke() {
        return smoke;
    }

    public void setSmoke(JsonObject.smoke smoke) {
        this.smoke = smoke;
    }

    public JsonObject.light getLight() {
        return light;
    }

    public void setLight(JsonObject.light light) {
        this.light = light;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return temp.temp+"-"+humid.humid+"-"+smoke.smoke+"-"+light.light;
    }
}
