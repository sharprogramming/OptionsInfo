package com.sharprogramming.optionsinfo;

import com.sharprogramming.optionsinfo.models.OptionsInfo;
import com.sharprogramming.optionsinfo.models.VerticalOptionSpread;
import com.sharprogramming.optionsinfo.viewmodels.FilterRangeViewModel;
import com.studerw.tda.model.option.OptionChain;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.StageStyle;
import org.controlsfx.control.table.TableRowExpanderColumn;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

public class HelloController implements Initializable {

    @FXML
    public ChoiceBox<String > expChoiceBox;

    @FXML
    public ChoiceBox<OptionsInfo.PricingModel> pricingModelChoiceBox;
    public VBox root;

    @FXML
    Label symbolLabel;

    @FXML
    Label priceLabel;

    OptionChain chain;
    @FXML
    TableView<VerticalOptionSpread> tableView;


    private ObservableList<VerticalOptionSpread> allOptions = FXCollections.observableArrayList();
    private Map<String , FilterRangeViewModel> filters = new HashMap<>();
    public HelloController(){
        System.out.println("Constructor called");
    }

    String symbol = "SPY";
    public void initialize(){
        System.out.println("Running initialize");
        var table = tableView;
        addExpanderColumn();
        table.getColumns().add(getPutCallColumn());
        table.getColumns().add(getMoneyTableColumn("Primary Strike", "primaryStrike", VerticalOptionSpread::getPrimaryStrike, 140) );
        table.getColumns().add(getMoneyTableColumn("Secondary Strike", "secondaryStrike", VerticalOptionSpread::getSecondaryStrike, 160));
        table.getColumns().add(getMoneyTableColumn("Risk","risk" , VerticalOptionSpread::getRisk, 80));
        table.getColumns().add(getMoneyTableColumn("Profit", "profit", VerticalOptionSpread::getProfit, 90));
        table.getColumns().add(getBigDecimalTableColumn("Risk/Profit", "riskProfitRatio", new DecimalFormat("0.000"), VerticalOptionSpread::getRiskProfitRatio, 120));
        table.getColumns().add(getMoneyTableColumn("Money", "moneyValue", VerticalOptionSpread::getMoneyValue, 95));
        table.getColumns().add((getMoneyTableColumn("Break Even", "breakEven", VerticalOptionSpread::getBreakEven, 120)));
        table.getColumns().add(getBigDecimalTableColumn("Volume", "volume", new DecimalFormat("0"), VerticalOptionSpread::getVolume, 100));
        table.setSelectionModel(new NullTableSelectionModel<>(table));

        VBox.setMargin(table, new Insets(10, 8, 20, 8));
//        root.prefHeightProperty().bind(root.getParent().getScene().heightProperty());
        table.setBackground(new Background(new BackgroundFill(Color.WHITESMOKE, CornerRadii.EMPTY, Insets.EMPTY)));
        table.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));
        root.setFillWidth(true);

        VBox.setVgrow(tableView, Priority.ALWAYS);

        fetchOptions();

        pricingModelChoiceBox.getItems().addAll(OptionsInfo.PricingModel.values());
        pricingModelChoiceBox.setValue(OptionsInfo.pricingModel);
        filteredList = new FilteredList<>(masterData, p -> true);
        updatePredicate();
        SortedList<VerticalOptionSpread> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(tableView.comparatorProperty());
        tableView.setItems(sortedList);
        symbolLabel.setOnMouseClicked(event -> {
            var dialog = new TextInputDialog("SPY");
            dialog.setTitle("Change Symbol");
            dialog.setHeaderText("Pick a Symbol");
            dialog.initStyle(StageStyle.UTILITY);
            dialog.setContentText("Pick the Symbol you want to view");
            dialog.setGraphic(null);
            var result = dialog.showAndWait();
            result.ifPresent(symbol -> {
                this.symbol = symbol;
                fetchOptions();
            });
        });
        symbolLabel.setTooltip(new Tooltip("Test"));
    }

    private void addExpanderColumn() {
        TableRowExpanderColumn<VerticalOptionSpread> expanderColumn = new TableRowExpanderColumn<>(
                row -> {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("expanded-spread-row.fxml"));
                    try {
                        Parent parent = loader.load();
                        ExpandedSpreadRow expandedSpreadRow = loader.getController();
                        var spread = row.getValue();
                        expandedSpreadRow.setOptions(spread.getPrimary(), spread.getSecondary());
                        return parent;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return null;
                }
        );
        expanderColumn.setPrefWidth(25.0);
        tableView.getColumns().add(expanderColumn);
    }

    private void fetchOptions() {
        TDAService tdaService = new TDAService();
        tdaService.fetchOptionChain(symbol, chain -> {
            Platform.runLater(() -> {
                this.chain = chain;
                expChoiceBox.getItems().clear();
                expChoiceBox.getItems().addAll(chain.getCallExpDateMap().keySet());
                expChoiceBox.setValue(expChoiceBox.getItems().get(0));
                symbolLabel.setText(chain.getSymbol());
                symbolLabel.getTooltip().setText(chain.getUnderlying().getDescription());
                priceLabel.setText(chain.getUnderlyingPrice().toString());
                String key = chain.getCallExpDateMap().keySet().stream().toList().get(0);
                updateTableItems(key);
            });
        });
    }

    private final ObservableList<VerticalOptionSpread> masterData = FXCollections.observableArrayList();
    private FilteredList<VerticalOptionSpread> filteredList;
    private void updateTableItems(String date) {
        masterData.clear();
        masterData.addAll(VerticalOptionSpread.createAllSpreads(chain, date));
    }

    private TableColumn<VerticalOptionSpread, BigDecimal> getMoneyTableColumn(String title, String variableName,Function< VerticalOptionSpread, BigDecimal> function, double size) {
        return getBigDecimalTableColumn(title, variableName, new DecimalFormat("$0.000"), function, size);
    }

    private TableColumn<VerticalOptionSpread, BigDecimal> getBigDecimalTableColumn(String title, String variableName, DecimalFormat currency, Function< VerticalOptionSpread, BigDecimal> function, double size) {
        TableColumn<VerticalOptionSpread, BigDecimal> column =  getTableColumn(title, variableName, size);
        column.setCellFactory(param ->
            new TableCell<>(){
                @Override
                protected void updateItem(BigDecimal item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || empty) {
                        setGraphic(null);
                    } else {
                        String text = currency.format(item);
                        if(item.intValue() == Integer.MAX_VALUE){
                            text = "Undefined";
                        }
                        setGraphic(new Label(text));
                    }
                }
            }
        );
        filters.put(variableName, new FilterRangeViewModel(function));
        var filterButton = new Button();
//        new Image("/outline_filter.png");
        var iv = new ImageView(new Image("com/sharprogramming/optionsinfo/outline_filter.png"));
        iv.setFitHeight(20.0);
        iv.setFitWidth(20.0);
//        FontIcon fontIcon = new FontIcon(FontAwesomeSolid.FILTER);
//        fontIcon.setIconSize(16);
        filterButton.setGraphic(iv);
        filterButton.setOnMouseClicked(event -> {
            FilterRangeViewModel filterViewModel = filters.get(variableName);
            showFilterWindow(filterViewModel, filterButton);
        });
        column.setGraphic(filterButton);
        return column;
    }

    private <T> TableColumn<VerticalOptionSpread, T> getTableColumn(String title, String variableName, double size) {
        var column = new TableColumn<VerticalOptionSpread, T>(title);
        column.setCellValueFactory(new PropertyValueFactory<>(variableName));
        column.setMaxWidth(Double.MAX_VALUE);

//        column.setPrefWidth(Control.USE_COMPUTED_SIZE);
        column.setPrefWidth(size);
        return column;
    }

    private void updatePredicate(){
        var filterPredicate = filters.values().stream().map(FilterRangeViewModel::getPredicate).reduce(Predicate::and).orElse(spread -> true);
        filteredList.setPredicate(putCallPredicate.and(filterPredicate));
    }
    private Predicate<VerticalOptionSpread> putCallPredicate = p -> true;
    private TableColumn<VerticalOptionSpread, String> getPutCallColumn(){
        TableColumn<VerticalOptionSpread, String> putColumn = getTableColumn("", "spreadType", 80);
        ChoiceBox<String> optionTypeChoiceBox = new ChoiceBox<>();
        optionTypeChoiceBox.getItems().addAll("PUT", "CALL", "ALL");
        optionTypeChoiceBox.setValue("ALL");
        optionTypeChoiceBox.setOnAction(event -> {
            putCallPredicate = switch (optionTypeChoiceBox.getValue()) {
                case "PUT" -> spread -> spread.getSpreadType() == OptionsInfo.OptionType.PUT;
                case "CALL" -> spread -> spread.getSpreadType() == OptionsInfo.OptionType.CALL;
                default -> spread -> true;
            };
            updatePredicate();
        });
        putColumn.setGraphic(optionTypeChoiceBox);
        return putColumn;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("initializing");
        initialize();
    }

    public void updateExpDate(ActionEvent actionEvent) {
        System.out.println(actionEvent.getEventType());

        if(expChoiceBox.getValue() == null){
            return;
        }
        updateTableItems(expChoiceBox.getValue());
    }

    public void updatePricingModel(ActionEvent actionEvent) {
        OptionsInfo.pricingModel = pricingModelChoiceBox.getValue();
        tableView.refresh();
    }

    public void refresh(ActionEvent actionEvent) {
        fetchOptions();
    }


    public void showFilterWindow(FilterRangeViewModel filterRangeViewModel, Button filterButton){
        System.out.println("Tableview skin " + tableView.getSkin());
        Dialog<Predicate<VerticalOptionSpread>> dialog = new Dialog<>();
        dialog.setTitle("Filter");
        dialog.setHeaderText("Specify Range");


        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);


        FXMLLoader loader = new FXMLLoader(getClass().getResource("filter-dialog.fxml"));
        try {
            Parent parent = loader.load();
            FilterDialog dialogController = loader.getController();
            dialogController.setViewModel(filterRangeViewModel, masterData);
            dialog.getDialogPane().setContent(parent);
            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == ButtonType.OK) {
                    return dialogController.getPredicate();
                }
                return null;
            });
            Optional<Predicate<VerticalOptionSpread>> result = dialog.showAndWait();

            result.ifPresent(predicate -> {
                String tooltip = filterRangeViewModel.getStringRep();
                Image image;
                if (!tooltip.isBlank()){
                    image = new Image("com/sharprogramming/optionsinfo/filter_filled.png");
                    filterButton.setTooltip(new Tooltip(tooltip));
                }else{
                    image = new Image("com/sharprogramming/optionsinfo/outline_filter.png");
                    filterButton.setTooltip(null);
                }
                var iv = new ImageView(image);
                iv.setFitHeight(20.0);
                iv.setFitWidth(20.0);
//                fontIcon.setIconSize(16);
                filterButton.setGraphic(iv);
               updatePredicate();
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}