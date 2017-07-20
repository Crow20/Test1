package com.sike.courses.mvp.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by agritsenko on 29.05.2017.
 */

public class Course{

    @SerializedName("name")
    private String name;

    @SerializedName("subname")
    private String subname;

    @SerializedName("date")
    private String date;

    @SerializedName("color")
    private String color;

    @SerializedName("value1")
    private String value1;

    @SerializedName("value2")
    private String value2;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubname() {
        return subname;
    }

    public void setSubname(String subname) {
        this.subname = subname;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getValue1() {
        return value1;
    }

    public void setValue1(String value1) {
        this.value1 = value1;
    }

    public String getValue2() {
        return value2;
    }

    public void setValue2(String value2) {
        this.value2 = value2;
    }

    public boolean isSubnameNull(Course obj){
        switch (obj.getName()){
            case "EUR/USD":
                return true;
            case "ММВБ":
                return true;
            case "РТС":
                return true;
            case "Brent":
                return true;
            case "DJIA":
                return true;
            case "Nasdaq":
                return true;
            case "FTSE":
                return true;
            default:
                return false;
        }
    }
}


