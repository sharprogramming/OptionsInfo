package com.sharprogramming.optionsinfo.models;

import com.studerw.tda.model.option.Option;
import com.studerw.tda.model.option.OptionChain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class VerticalOptionSpread {
    private final OptionsInfo primary;
    private final OptionsInfo secondary;

    public VerticalOptionSpread(OptionsInfo primary, OptionsInfo secondary){
        this.primary = primary;
        this.secondary = secondary;
    }

    public static List<VerticalOptionSpread> createAllSpreads(OptionChain chain, String date){
        var callSpreads = createSpreads(chain, chain.getCallExpDateMap().get(date));
        var putSpread = createSpreads(chain, chain.getPutExpDateMap().get(date));
        var results = new ArrayList<VerticalOptionSpread>();
        results.addAll(callSpreads);
        results.addAll(putSpread);
        return results;
    }
    public static List<VerticalOptionSpread> createSpreads(OptionChain chain, Map<BigDecimal, List<Option>> optionMap){
        var options = prepareOptionList(chain, optionMap);
        List<VerticalOptionSpread> spreads = new ArrayList<>();
        for(int i = 0; i < options.size(); i++){
            for(int j = i + 1; j < options.size(); j++){
                spreads.add(new VerticalOptionSpread(options.get(i), options.get(j)));
            }
        }
        return spreads;
    }
    private static List<OptionsInfo> prepareOptionList(OptionChain chain, Map<BigDecimal, List<Option>> optionMap) {
        return optionMap.values().stream()
                .flatMap(Collection::stream).map(option -> new OptionsInfo(chain, option))
//                .filter((option -> option.getMoneyValue().doubleValue() < 0))
                .sorted(Comparator.comparing(OptionsInfo::getMoneyValue).reversed())
                .toList();
    }


    public BigDecimal getProfit(){
        return switch (OptionsInfo.pricingModel){
            case MAX -> getMaxProfit();
            case MIN -> getMinProfit();
            // If it is one of the following, both options follow the same pricing model, we can delegate the pricing model logic to the option level
            case ASK, BID, MID, LAST -> primary.getPrice().subtract(secondary.getPrice());
        };
    }

    public BigDecimal getVolume(){
        return new BigDecimal(Math.min(primary.getVolume(), secondary.getVolume()));
    }
    public BigDecimal getMidProfit(){
        return primary.getMidPrice().subtract(secondary.getMidPrice());
    }

    public BigDecimal getLastProfit(){
        return primary.getLastPrice().subtract(secondary.getMidPrice());
    }

    public BigDecimal getMinProfit(){
        return primary.getBidPrice().subtract(secondary.getAskPrice());
    }

    public BigDecimal getMaxProfit(){
        return primary.getAskPrice().subtract(secondary.getBidPrice());
    }

    public BigDecimal getRisk(){
        return primary.getStrikePrice().subtract(secondary.getStrikePrice()).abs();
    }

    public BigDecimal getPrimaryStrike(){
        return primary.getStrikePrice();
    }

    public BigDecimal getSecondaryStrike(){
        return secondary.getStrikePrice();
    }

    public OptionsInfo.OptionType getSpreadType(){
        return primary.getOptionType();
    }

    public BigDecimal getMoneyValue(){
        return primary.getMoneyValue();
    }

    public BigDecimal getBreakEven(){
        var multiplier = getSpreadType() == OptionsInfo.OptionType.CALL ? 1 : -1;
        return primary.getStrikePrice().add(getProfit().multiply(BigDecimal.valueOf(multiplier)));
    }

    public BigDecimal getRiskProfitRatio(){
        if(getProfit().doubleValue() > 0){

            return getRisk().divide(getProfit(), 2, RoundingMode.HALF_UP);
        }
        return BigDecimal.valueOf(Integer.MAX_VALUE);
    }

    public OptionsInfo getPrimary() {
        return primary;
    }

    public OptionsInfo getSecondary() {
        return secondary;
    }
}

