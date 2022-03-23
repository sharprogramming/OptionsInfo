package com.sharprogramming.optionsinfo.viewmodels;

import com.sharprogramming.optionsinfo.models.VerticalOptionSpread;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.function.Function;
import java.util.function.Predicate;


public class FilterRangeViewModel {

    private boolean maxEnabled = false;
    private BigDecimal max;
    private boolean minEnabled = false;
    private BigDecimal min;
    private final Function<VerticalOptionSpread, BigDecimal> function;

    public FilterRangeViewModel( Function<VerticalOptionSpread, BigDecimal> function){
        this.function = function;
    }

    public Predicate<VerticalOptionSpread> getPredicate(){
        Predicate<VerticalOptionSpread> minPredicate = minEnabled ? spread -> function.apply(spread).doubleValue() >= min.doubleValue() : spread -> true;
        Predicate<VerticalOptionSpread> maxPredicate = maxEnabled ? spread -> function.apply(spread).doubleValue() <= max.doubleValue() : spread -> true;
        return minPredicate.and(maxPredicate);
    }

    public BigDecimal getBottomBound(Collection<VerticalOptionSpread> spreads){
        return spreads.stream().map(function).sorted().findFirst().orElse(new BigDecimal(0));
    }

    public BigDecimal getTopBound(Collection<VerticalOptionSpread> spreads){
        return spreads.stream().map(function).sorted().reduce((first, second) -> second).orElse(new BigDecimal(0));
    }

    public boolean isMaxEnabled() {
        return maxEnabled;
    }

    public void setMaxEnabled(boolean maxEnabled) {
        this.maxEnabled = maxEnabled;
    }

    public BigDecimal getMax() {
        return max;
    }

    public void setMax(BigDecimal max) {
        this.max = max;
    }

    public boolean isMinEnabled() {
        return minEnabled;
    }

    public void setMinEnabled(boolean minEnabled) {
        this.minEnabled = minEnabled;
    }

    public BigDecimal getMin() {
        return min;
    }

    public void setMin(BigDecimal min) {
        this.min = min;
    }

    public String getStringRep(){
        String maxString = maxEnabled ? "Max = " + max : "";
        String minString = minEnabled ? "Min = " + min : "";
        return (minString + "\n" + maxString).trim();
    }

    @Override
    public String toString() {
        return "FilterRangeViewModel{" +
                "maxEnabled=" + maxEnabled +
                ", max=" + max +
                ", minEnabled=" + minEnabled +
                ", min=" + min +
                ", function=" + function +
                '}';
    }
}
