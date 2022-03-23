package com.sharprogramming.optionsinfo.models;

import java.util.ArrayList;

public class TDAStraddleOptionChain {
    private String month;

    @Override
    public String toString() {
        return "TDAStraddleOptionChain{" +
                "month='" + month + '\'' +
                ", secondaryMonth='" + secondaryMonth + '\'' +
                ", type='" + type + '\'' +
                ", secondaryType='" + secondaryType + '\'' +
                ", year=" + year +
                ", day=" + day +
                ", daysToExp=" + daysToExp +
                ", secondaryYear=" + secondaryYear +
                ", secondaryDay=" + secondaryDay +
                ", secondaryDaysToExpire=" + secondaryDaysToExp +
                ", optionStrategyList=" + optionStrategyList +
                ", leap=" + leap +
                ", secondaryLeap=" + secondaryLeap +
                '}';
    }

    private String secondaryMonth;
    private String type;
    private String secondaryType;
    private Integer year, day, daysToExp, secondaryYear, secondaryDay, secondaryDaysToExp;
    private ArrayList<TDAStrategy> optionStrategyList;
    private Boolean leap, secondaryLeap;

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getSecondaryMonth() {
        return secondaryMonth;
    }

    public void setSecondaryMonth(String secondaryMonth) {
        this.secondaryMonth = secondaryMonth;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSecondaryType() {
        return secondaryType;
    }

    public void setSecondaryType(String secondaryType) {
        this.secondaryType = secondaryType;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public Integer getDaysToExp() {
        return daysToExp;
    }

    public void setDaysToExp(Integer daysToExp) {
        this.daysToExp = daysToExp;
    }

    public Integer getSecondaryYear() {
        return secondaryYear;
    }

    public void setSecondaryYear(Integer secondaryYear) {
        this.secondaryYear = secondaryYear;
    }

    public Integer getSecondaryDay() {
        return secondaryDay;
    }

    public void setSecondaryDay(Integer secondaryDay) {
        this.secondaryDay = secondaryDay;
    }

    public Integer getSecondaryDaysToExp() {
        return secondaryDaysToExp;
    }

    public void setSecondaryDaysToExp(Integer secondaryDaysToExp) {
        this.secondaryDaysToExp = secondaryDaysToExp;
    }

    public ArrayList<TDAStrategy>getOptionStrategyList() {
        return optionStrategyList;
    }

    public void setOptionStrategyList(ArrayList<TDAStrategy> optionStrategyList) {
        this.optionStrategyList = optionStrategyList;
    }

    public Boolean getLeap() {
        return leap;
    }

    public void setLeap(Boolean leap) {
        this.leap = leap;
    }

    public Boolean getSecondaryLeap() {
        return secondaryLeap;
    }

    public void setSecondaryLeap(Boolean secondaryLeap) {
        this.secondaryLeap = secondaryLeap;
    }
}
