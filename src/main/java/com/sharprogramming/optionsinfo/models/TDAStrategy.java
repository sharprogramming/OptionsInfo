package com.sharprogramming.optionsinfo.models;

public class TDAStrategy {
    private TDAOptionLeg primaryLeg, secondaryLeg;
    private String strategyStrike;
    private Double strategyBid, StrategyAsk;

    public TDAOptionLeg getPrimaryLeg() {
        return primaryLeg;
    }

    public void setPrimaryLeg(TDAOptionLeg primaryLeg) {
        this.primaryLeg = primaryLeg;
    }

    public TDAOptionLeg getSecondaryLeg() {
        return secondaryLeg;
    }

    public void setSecondaryLeg(TDAOptionLeg secondaryLeg) {
        this.secondaryLeg = secondaryLeg;
    }

    public String getStrategyStrike() {
        return strategyStrike;
    }

    public void setStrategyStrike(String strategyStrike) {
        this.strategyStrike = strategyStrike;
    }

    public Double getStrategyBid() {
        return strategyBid;
    }

    public void setStrategyBid(Double strategyBid) {
        this.strategyBid = strategyBid;
    }

    public Double getStrategyAsk() {
        return StrategyAsk;
    }

    public void setStrategyAsk(Double strategyAsk) {
        StrategyAsk = strategyAsk;
    }
}
