package com.julianjupiter.addressbook;

import com.julianjupiter.addressbook.controller.MainController;
import com.julianjupiter.addressbook.util.View;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;

public class AddressBook extends Application {
    private Stage primaryStage;

    @Override
    public void start(Stage stage) {
        this.primaryStage = stage;
        this.initMain();
    }

    private void initMain() {
        this.primaryStage.initStyle(StageStyle.UNDECORATED);
        this.primaryStage.setMaximized(false);

        var view = View.of(MainController.class, BorderPane.class);

        var scene = new Scene(view.component());
        scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
        this.primaryStage.setScene(scene);
        JMetro jMetro = new JMetro(Style.LIGHT);
        jMetro.setScene(scene);

        var mainController = view.controller();
        mainController.setPrimaryStage(this.primaryStage);

        this.primaryStage.show();
    }

    public static void start(String[] args) {
        launch(args);
    }
}
