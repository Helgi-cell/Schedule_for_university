package com.epam.brest;

import org.springframework.stereotype.Component;

@Component
public class LectorsSchedule {
    private int idLS;
    private String lector;
    private int pair;
    private String monday;
    private String tuesday;
    private String wednesday;
    private String thursday;
    private String friday;
    private String saturday;

    public LectorsSchedule() {
        this.monday = "";
        this.tuesday = "";
        this.wednesday = "";
        this.thursday = "";
        this.friday = "";
        this.saturday = "";
    }

    public LectorsSchedule(int idLS
                           , String lector
                           , int pair
                           , String monday
                           , String tuesday
                           , String wednesday
                           , String thursday
                           , String friday
                           , String saturday) {
        this.idLS = idLS;
        this.lector = lector;
        this.pair = pair;
        this.monday = monday;
        this.tuesday = tuesday;
        this.wednesday = wednesday;
        this.thursday = thursday;
        this.friday = friday;
        this.saturday = saturday;
    }

    public int getIdLS() {
        return idLS;
    }

    public void setIdLS(int idLS) {
        this.idLS = idLS;
    }

    public String getLector() {
        return lector;
    }

    public void setLector(String lector) {
        this.lector = lector;
    }

    public int getPair() {
        return pair;
    }

    public void setPair(int pair) {
        this.pair = pair;
    }

    public String getMonday() {
        return monday;
    }

    public void setMonday(String monday) {
        this.monday = monday;
    }

    public String getTuesday() {
        return tuesday;
    }

    public void setTuesday(String tuesday) {
        this.tuesday = tuesday;
    }

    public String getWednesday() {
        return wednesday;
    }

    public void setWednesday(String wednesday) {
        this.wednesday = wednesday;
    }

    public String getThursday() {
        return thursday;
    }

    public void setThursday(String thursday) {
        this.thursday = thursday;
    }

    public String getFriday() {
        return friday;
    }

    public void setFriday(String friday) {
        this.friday = friday;
    }

    public String getSaturday() {return saturday;}

    public void setSaturday(String saturday) {this.saturday = saturday;}
}
