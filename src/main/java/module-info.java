module com.sharprogramming.optionsinfo {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.core;
    requires org.kordamp.ikonli.fontawesome5;
    requires org.kordamp.ikonli.materialdesign2;
    requires eu.hansolo.tilesfx;
    requires okhttp3;
    requires org.apache.commons.lang3;
    requires td.ameritrade.client;

    opens com.sharprogramming.optionsinfo.models to javafx.base;
    exports com.sharprogramming.optionsinfo;
    exports com.sharprogramming.optionsinfo.models to com.fasterxml.jackson.databind;
    opens com.sharprogramming.optionsinfo to javafx.base, javafx.fxml;
}