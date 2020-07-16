package com.julianjupiter.addressbook.util;

import com.julianjupiter.addressbook.controller.Controller;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;
import java.net.URL;

public class View<T extends Controller, U extends Parent> {
    private static final String FXML_EXTENSION = ".fxml";
    private final Class<? extends Controller> controllerClass;
    private FXMLLoader loader;
    private U u;

    private View(Class<? extends Controller> controllerClass) throws IOException {
        this.controllerClass = controllerClass;
        this.load();
    }

    public static <T extends Controller, U extends Parent> View<T, U> of(Class<T> controllerClass, Class<U> componentClass) {
        try {
            return new View(controllerClass);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void load() throws IOException {
        this.loader = new FXMLLoader(fxmlUrl());
        this.u = this.loader.load();
    }

    private URL fxmlUrl() {
        return controllerClass.getResource(controllerClass.getSimpleName() + FXML_EXTENSION);
    }

    public U component() {
        return u;
    }

    public T controller() {
        return loader.getController();
    }
}
