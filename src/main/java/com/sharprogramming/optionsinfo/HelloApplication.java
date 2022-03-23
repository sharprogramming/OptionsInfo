package com.sharprogramming.optionsinfo;

import com.sharprogramming.optionsinfo.models.VerticalOptionSpread;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    TDAService tdaService;

    public HelloApplication(){
        tdaService = new TDAService();
    }

    TableView<VerticalOptionSpread> table;

    @Override
    public void start(Stage stage) throws IOException {
//        table = new TableView<>();


//        table.getColumns().add(getTableColumn("Type", "optionType"));
//        table.getColumns().add(getTableColumn("Strike Price", "strikePrice"));
//        table.getColumns().add(getTableColumn("Money", "moneyValue"));
//        table.getColumns().add(getTableColumn("Mid Price", "midPrice"));
//        table.getColumns().add(getTableColumn("Premium", "premium"));
//        table.getColumns().add(getTableColumn("Last Price", "lastPrice"));
//        table.getColumns().add(getTableColumn("Last Premium", "lastPremium"));
//        table.getColumns().add(getTableColumn("Underlying Price", "underlyingPrice"));


//        VBox vBox = new VBox(table);
//        Scene scene = new Scene((vBox));
//        stage.setScene(scene);
        var loader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        VBox root = loader.load();

        Scene scene = new Scene(root, 1000, 400);
//        scene.getRoot().
        stage.setScene(scene);
        root.prefHeightProperty().bind(scene.heightProperty());
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}