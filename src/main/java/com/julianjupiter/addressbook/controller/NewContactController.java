package com.julianjupiter.addressbook.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class NewContactController implements Controller, Initializable {
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

    public ContactProperty contactProperty() {
        return new ContactProperty()
                .setFirstName(firstNameTextField.getText())
                .setLastName(lastNameTextField.getText())
                .setAddress(addressTextField.getText())
                .setMobileNumber(mobileNumberTextField.getText())
                .setEmailAddress(emailAddressTextField.getText());
    }
}
