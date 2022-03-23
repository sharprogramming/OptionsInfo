package com.sharprogramming.optionsinfo.models;

public class TDAOptionLeg {
    private String symbol, putCallInd, description, range;
    private Double bid, ask, strikePrice, totalVolume;

    @Override
    public String toString() {
        return "TDAOptionLeg{" +
                "symbol='" + symbol + '\'' +
                ", putCallInd='" + putCallInd + '\'' +
                ", description='" + description + '\'' +
                ", range='" + range + '\'' +
                ", bid=" + bid +
                ", ask=" + ask +
                ", strikePrice=" + strikePrice +
                ", totalVolume=" + totalVolume +
                '}';
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getPutCallInd() {
        return putCallInd;
    }

    public void setPutCallInd(String putCallInd) {
        this.putCallInd = putCallInd;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }

    public Double getBid() {
        return bid;
    }

    public void setBid(Double bid) {
        this.bid = bid;
    }

    public Double getAsk() {
        return ask;
    }

    public void setAsk(Double ask) {
        this.ask = ask;
    }

    public Double getStrikePrice() {
        return strikePrice;
    }

    public void setStrikePrice(Double strikePrice) {
        this.strikePrice = strikePrice;
    }

    public Double getTotalVolume() {
        return totalVolume;
    }

    public void setTotalVolume(Double totalVolume) {
        this.totalVolume = totalVolume;
    }
}
