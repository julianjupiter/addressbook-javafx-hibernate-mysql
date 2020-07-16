module com.julianjupiter.addressbook {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires org.jfxtras.styles.jmetro;
    requires org.kordamp.iconli.core;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.materialdesign;

    requires java.persistence;

    opens com.julianjupiter.addressbook to javafx.fxml;
    opens com.julianjupiter.addressbook.controller to javafx.fxml;
    exports com.julianjupiter.addressbook;
    exports com.julianjupiter.addressbook.controller;
}