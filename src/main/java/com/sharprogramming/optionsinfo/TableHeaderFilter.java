package com.sharprogramming.optionsinfo;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public class TableHeaderFilter extends HBox {


    public TableHeaderFilter(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("table-header-filter.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public void setRange(List<BigDecimal> range){
        var sortedRange = range.stream().sorted().toList();
    }

}
