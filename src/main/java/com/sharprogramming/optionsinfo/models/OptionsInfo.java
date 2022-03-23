package com.sharprogramming.optionsinfo.models;

import com.studerw.tda.model.option.Option;
import com.studerw.tda.model.option.OptionChain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public class OptionsInfo {
    private final String symbol;
    private final BigDecimal strikePrice;
    private final BigDecimal lastPrice;
    private final BigDecimal askPrice;
    private final BigDecimal bidPrice;
    private final BigDecimal underlyingPrice;
    private final OptionType optionType;
    private final Long volume;

    public static PricingModel pricingModel = PricingModel.MID;
    public OptionsInfo(OptionChain chain, Option option){
        this.symbol = chain.getSymbol();
        this.underlyingPrice = chain.getUnderlyingPrice();
        this.strikePrice = option.getStrikePrice();
        this.lastPrice = option.getLastPrice();
        this.askPrice = option.getAskPrice();
        this.bidPrice = option.getBidPrice();
        this.optionType = switch (option.getPutCall()){
            case CALL -> OptionType.CALL;
            case PUT -> OptionType.PUT;
        };
        this.volume = option.getTotalVolume();
        option.getTradeTimeInLong();
    }

    public BigDecimal getMidPrice(){
        BigDecimal difference = askPrice.subtract( bidPrice);
        return bidPrice.add(difference.divide(BigDecimal.valueOf(2), RoundingMode.HALF_UP));
    }

    public BigDecimal getMoneyValue(){
        return switch (this.optionType){
            case CALL -> this.underlyingPrice.subtract(this.strikePrice);
            case PUT -> this.strikePrice.subtract(this.underlyingPrice);
        };
    }

    public Long getVolume() {
        return volume;
    }

    public enum OptionType{
        CALL,
        PUT;
    }

    public String getSymbol() {
        return symbol;
    }

    public BigDecimal getStrikePrice() {
        return strikePrice;
    }

    public BigDecimal getLastPrice() {
        return lastPrice;
    }

    public BigDecimal getAskPrice() {
        return askPrice;
    }

    public BigDecimal getBidPrice() {
        return bidPrice;
    }

    public BigDecimal getUnderlyingPrice() {
        return underlyingPrice;
    }

    public OptionType getOptionType() {
        return optionType;
    }

    public BigDecimal getPremium(){
        return getMidPrice().subtract(getMoneyValue());
    }

    public BigDecimal getLastPremium(){
        return getLastPrice().subtract(getMoneyValue());
    }

    public BigDecimal getPrice(){
        return switch (pricingModel){
            case ASK, MAX -> getAskPrice();
            case MID -> getMidPrice();
            case LAST -> getLastPrice();
            case BID, MIN -> getBidPrice() ;
        };
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OptionsInfo that = (OptionsInfo) o;
        return Objects.equals(strikePrice, that.strikePrice)  && optionType == that.optionType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(strikePrice, optionType);
    }

    public enum PricingModel {
        MID, LAST, BID, ASK, MIN, MAX;
    }
}
