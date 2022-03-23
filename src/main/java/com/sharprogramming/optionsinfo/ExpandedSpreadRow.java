package com.sharprogramming.optionsinfo;

import com.sharprogramming.optionsinfo.models.OptionsInfo;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class ExpandedSpreadRow  {
    
    public Label pAsk, pVolume, pPrice;
    public Label pBid;
    public Label sPrice;
    public Label sVolume;
    public Label sBid;
    public Label pLast;
    public Label sAsk;
    public Label sLast;
    public GridPane root;

    public void setOptions(OptionsInfo primary, OptionsInfo secondary){
        setOptionUI(primary, pAsk, pVolume, pPrice, pBid, pLast);
        setOptionUI(secondary, sAsk, sVolume, sPrice, sBid, sLast);

    }

    public void initialize(){
        root.setBackground(new Background(new BackgroundFill(Color.WHITESMOKE, CornerRadii.EMPTY, Insets.EMPTY)));
    }
    private static void setOptionUI(OptionsInfo primary, Label ask, Label volume, Label price, Label bid, Label last ) {

        ask.setText(primary.getAskPrice().toString());
        volume.setText(primary.getVolume().toString());
        price.setText(primary.getPrice().toString());
        bid.setText(primary.getBidPrice().toString());
        last.setText(primary.getLastPrice().toString());
    }


}
