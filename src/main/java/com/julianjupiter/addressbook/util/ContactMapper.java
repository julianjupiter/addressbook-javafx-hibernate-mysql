package com.julianjupiter.addressbook.util;

import com.julianjupiter.addressbook.controller.ContactProperty;
import com.julianjupiter.addressbook.entity.Contact;

public class ContactMapper {
    public ContactProperty fromEntityToProperty(Contact contact) {
        return new ContactProperty()
                .setId(contact.getId())
                .setLastName(contact.getLastName())
                .setFirstName(contact.getFirstName())
                .setAddress(contact.getAddress())
                .setMobileNumber(contact.getMobileNumber())
                .setEmailAddress(contact.getEmailAddress())
                .setCreatedAt(contact.getCreatedAt())
                .setUpdatedAt(contact.getUpdatedAt());
    }
}
