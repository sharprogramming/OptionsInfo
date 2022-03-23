package com.sharprogramming.optionsinfo;

import com.sharprogramming.optionsinfo.models.VerticalOptionSpread;
import com.sharprogramming.optionsinfo.viewmodels.FilterRangeViewModel;

import javafx.scene.control.CheckBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.function.Predicate;

public class FilterDialog {
    public Spinner<Double> maxSpinner;
    public Spinner<Double> minSpinner;
    public CheckBox maxCheckbox;
    public CheckBox minCheckbox;
    private FilterRangeViewModel viewModel;

    public Predicate<VerticalOptionSpread> getPredicate(){
        viewModel.setMax(BigDecimal.valueOf(maxSpinner.getValue()));
        viewModel.setMin(BigDecimal.valueOf(minSpinner.getValue()));
        return viewModel.getPredicate();
    }

    public void setViewModel(FilterRangeViewModel viewModel, Collection<VerticalOptionSpread> spreads) {
        System.out.println("view model set");
        this.viewModel = viewModel;
        double bottomBound = viewModel.getBottomBound(spreads).doubleValue();
        double topBound = viewModel.getTopBound(spreads).doubleValue();

        maxSpinner.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(bottomBound, topBound, viewModel.getMax() == null ? topBound : viewModel.getMax().doubleValue(), 1.0));
        minSpinner.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(bottomBound, topBound, viewModel.getMin() == null ? bottomBound : viewModel.getMin().doubleValue(),1.0));

        maxSpinner.setEditable(true);
        minSpinner.setEditable(true);

        minSpinner.getEditor().setOnKeyTyped( event -> updateMin());

        maxSpinner.getEditor().setOnKeyTyped(event -> updateMax());
//        minSpinner.addEventFilter(EventType.ROOT, event -> {
//            if (event instanceof KeyEvent keyEvent){
//                if (keyEvent.getCode() == KeyCode.ENTER){
//                    event.consume();
//                }
//            }
//        });
//        maxSpinner.addEventFilter(EventType.ROOT, Event::consume);

        maxSpinner.valueProperty().addListener((observable, oldValue, newValue) -> updateMax());

        minSpinner.valueProperty().addListener((observable, oldValue, newValue) -> updateMin());

        maxCheckbox.setSelected(viewModel.isMaxEnabled());
        minCheckbox.setSelected(viewModel.isMinEnabled());

        maxCheckbox.setOnAction( event -> viewModel.setMaxEnabled(maxCheckbox.isSelected()));
        minCheckbox.setOnAction( event -> viewModel.setMinEnabled(minCheckbox.isSelected()));
    }

    private void updateMin() {
        minCheckbox.setSelected(true);
        viewModel.setMinEnabled(true);
        viewModel.setMin(BigDecimal.valueOf(minSpinner.getValue()));
        if (maxSpinner.getValueFactory() instanceof SpinnerValueFactory.DoubleSpinnerValueFactory doubleSpinnerValueFactory){
            doubleSpinnerValueFactory.setMin(minSpinner.getValue());
        }
    }
    private void updateMax() {
        maxCheckbox.setSelected(true);
        viewModel.setMaxEnabled(true);
        viewModel.setMax(BigDecimal.valueOf(minSpinner.getValue()));
        if (minSpinner.getValueFactory() instanceof SpinnerValueFactory.DoubleSpinnerValueFactory doubleSpinnerValueFactory){
            doubleSpinnerValueFactory.setMax(maxSpinner.getValue());
        }
    }
    public FilterRangeViewModel getViewModel() {
        return viewModel;
    }
}
