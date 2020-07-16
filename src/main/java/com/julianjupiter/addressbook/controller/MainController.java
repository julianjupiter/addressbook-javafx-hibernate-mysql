package com.julianjupiter.addressbook.controller;

import com.julianjupiter.addressbook.dao.ContactDao;
import com.julianjupiter.addressbook.service.ContactService;
import com.julianjupiter.addressbook.util.ContactMapper;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.kordamp.ikonli.javafx.FontIcon;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class MainController implements Controller, Initializable {
    private Stage primaryStage;
    @FXML
    private HBox headerHBox;
    private double xOffset = 0;
    private double yOffset = 0;
    @FXML
    private FontIcon closeWindowFontIcon;
    @FXML
    private FontIcon minimizeWindowFontIcon;
    @FXML
    private Label applicationTitle;

    @FXML
    private TableView<ContactProperty> contactTableView;
    @FXML
    private TableColumn<ContactProperty, String> firstNameTableColumn;
    @FXML
    private TableColumn<ContactProperty, String> lastNameTableColumn;

    private ObservableList<ContactProperty> contactPropertiesObservable = FXCollections.observableArrayList();

    private final ContactService contactService;
    private final ContactMapper contactMapper;

    public MainController() {
        this.contactService = ContactService.create(ContactDao.create());
        this.contactMapper = new ContactMapper();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.initWindowEvents();

        this.initContactTableView();
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    private void initWindowEvents() {
        headerHBox.setOnMousePressed(mouseEvent -> {
            if (!primaryStage.isMaximized()) {
                xOffset = mouseEvent.getSceneX();
                yOffset = mouseEvent.getSceneY();
            }
        });

        headerHBox.setOnMouseDragged(mouseEvent -> {
            if (!primaryStage.isMaximized()) {
                primaryStage.setX(mouseEvent.getScreenX() - xOffset);
                primaryStage.setY(mouseEvent.getScreenY() - yOffset);
            }
        });

        closeWindowFontIcon.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Close Application");
            alert.setHeaderText("You are about to close the application.");
            alert.setContentText("Do you want to continue?");

            alert.initModality(Modality.WINDOW_MODAL);
            alert.initOwner(primaryStage);

            alert.showAndWait()
                    .filter(buttonType -> buttonType == ButtonType.OK)
                    .ifPresent(buttonType -> Platform.exit());
        });

        minimizeWindowFontIcon.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            primaryStage.setIconified(true);
        });
    }

    private void initContactTableView() {
        this.firstNameTableColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        this.lastNameTableColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());

        List<ContactProperty> contactProperties = this.contactService.findAll().stream()
                .map(contactMapper::fromEntityToProperty)
                .collect(Collectors.toUnmodifiableList());
        this.contactPropertiesObservable.addAll(contactProperties);
        this.contactTableView.setItems(this.contactPropertiesObservable);
    }
}
