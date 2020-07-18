package com.julianjupiter.addressbook.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class EditContactController implements Controller, Initializable {
    private ContactProperty contactProperty;
    @FXML
    private TextField firstNameTextField;
    @FXML
    private TextField lastNameTextField;
    @FXML
    private TextField addressTextField;
    @FXML
    private TextField mobileNumberTextField;
    @FXML
    private TextField emailAddressTextField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setContactProperty(ContactProperty contactProperty) {
        this.contactProperty = contactProperty;
        this.setContactValues();
    }

    private void setContactValues() {
        this.firstNameTextField.setText(this.contactProperty.getFirstName());
        this.lastNameTextField.setText(this.contactProperty.getLastName());
        this.addressTextField.setText(this.contactProperty.getAddress());
        this.mobileNumberTextField.setText(this.contactProperty.getMobileNumber());
        this.emailAddressTextField.setText(this.contactProperty.getEmailAddress());
    }
}
