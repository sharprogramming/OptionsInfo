package com.sharprogramming.optionsinfo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;

public class NullTableSelectionModel<S> extends TableView.TableViewSelectionModel<S> {
    /**
     * Builds a default TableViewSelectionModel instance with the provided
     * TableView.
     *
     * @param tableView The TableView upon which this selection model should
     *                  operate.
     * @throws NullPointerException TableView can not be null.
     */
    public NullTableSelectionModel(TableView tableView) {
        super(tableView);
    }

    @Override
    public ObservableList<TablePosition> getSelectedCells() {
        return FXCollections.emptyObservableList();
    }

    @Override
    public void clearSelection(int row, TableColumn column) {

    }

    @Override
    public void clearAndSelect(int row, TableColumn column) {

    }

    @Override
    public void select(int row, TableColumn column) {

    }

    @Override
    public boolean isSelected(int row, TableColumn column) {
        return false;
    }

    @Override
    public void selectLeftCell() {

    }

    @Override
    public void selectRightCell() {

    }

    @Override
    public void selectAboveCell() {

    }

    @Override
    public void selectBelowCell() {

    }
}
