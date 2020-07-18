package com.julianjupiter.addressbook.controller;

import com.julianjupiter.addressbook.dao.ContactDao;
import com.julianjupiter.addressbook.entity.Contact;
import com.julianjupiter.addressbook.service.ContactService;
import com.julianjupiter.addressbook.util.ContactMapper;
import com.julianjupiter.addressbook.util.View;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.TextAlignment;
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
    private BorderPane headerBorderPane;
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
    @FXML
    private BorderPane contactBorderPane;
    @FXML
    private BorderPane contactActionBorderPane;
    @FXML
    private Label contactActionLabel;
    @FXML
    private BorderPane firstActionBorderPane;
    @FXML
    private BorderPane secondActionBorderPane;
//    @FXML
    private FontIcon editFontIcon;
//    @FXML
    private FontIcon deleteFontIcon;
//    @FXML
    private FontIcon cancelFontIcon;
//    @FXML
    private FontIcon updateFontIcon;

    private ObservableList<ContactProperty> contactPropertiesObservable = FXCollections.observableArrayList();

    private final ContactService contactService;
    private final ContactMapper contactMapper;
    private ContactProperty selectedContactProperty;

    public MainController() {
        this.contactService = ContactService.create(ContactDao.create());
        this.contactMapper = new ContactMapper();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.initWindowEvents();

        this.initContactTableView();
        this.initContactAction();
        this.initContactActionFontIcons();
        this.initEditContact();
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    private void initWindowEvents() {
        headerBorderPane.setOnMousePressed(mouseEvent -> {
            if (!primaryStage.isMaximized()) {
                xOffset = mouseEvent.getSceneX();
                yOffset = mouseEvent.getSceneY();
            }
        });

        headerBorderPane.setOnMouseDragged(mouseEvent -> {
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

        this.contactTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            this.viewContact(newValue);
        });
    }

    private void initContactAction() {
        this.contactActionLabel.setVisible(false);
    }

    private void initContactActionFontIcons() {
        this.editFontIcon = new FontIcon();
        this.editFontIcon.setIconColor(Paint.valueOf("#3F51B5"));
        this.editFontIcon.setIconLiteral("mdi-pencil-box-outline");
        this.editFontIcon.setIconSize(18);

        this.deleteFontIcon = new FontIcon();
        this.deleteFontIcon.setIconColor(Paint.valueOf("#3F51B5"));
        this.deleteFontIcon.setIconLiteral("mdi-delete");
        this.deleteFontIcon.setIconSize(18);

        this.cancelFontIcon = new FontIcon();
        this.cancelFontIcon.setIconColor(Paint.valueOf("#3F51B5"));
        this.cancelFontIcon.setIconLiteral("mdi-close");
        this.cancelFontIcon.setIconSize(18);
        this.cancelFontIcon.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            this.viewContact(this.selectedContactProperty);
        });

        this.updateFontIcon = new FontIcon();
        this.updateFontIcon.setIconColor(Paint.valueOf("#3F51B5"));
        this.updateFontIcon.setIconLiteral("mdi-content-save");
        this.updateFontIcon.setIconSize(18);
    }

    private void viewContact(ContactProperty contactProperty) {
        this.selectedContactProperty = contactProperty;
        var viewContactView = View.of(ViewContactController.class, AnchorPane.class);
        var viewContactController = viewContactView.controller();
        viewContactController.setContactProperty(contactProperty);
        var anchorPane = viewContactView.component();
        this.contactBorderPane.setCenter(anchorPane);
        this.contactActionBorderPane.setStyle("-fx-background-color: #CCCCCC");
        this.contactActionLabel.setStyle("-fx-text-fill: #3F51B5");
        this.contactActionLabel.setText("View Contact");
        this.contactActionLabel.setVisible(true);
        this.firstActionBorderPane.setCenter(this.editFontIcon);
        this.secondActionBorderPane.setCenter(this.deleteFontIcon);
    }

    private void initEditContact() {
        this.editFontIcon.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            this.contactActionLabel.setText("Edit Contact");
            var editContactView = View.of(EditContactController.class, AnchorPane.class);
            var editContactController = editContactView.controller();
            editContactController.setContactProperty(this.selectedContactProperty);
            var anchorPane = editContactView.component();
            this.contactBorderPane.setCenter(anchorPane);
            this.contactActionLabel.setText("Edit Contact");
            this.firstActionBorderPane.setCenter(this.cancelFontIcon);
            this.secondActionBorderPane.setCenter(this.updateFontIcon);
        });
    }
}
