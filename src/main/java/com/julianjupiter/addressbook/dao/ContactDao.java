package com.julianjupiter.addressbook.dao;

import com.julianjupiter.addressbook.entity.Contact;

import java.util.List;

public interface ContactDao extends Dao<Contact, Long> {
    static ContactDao create() {
        return new ContactDaoImpl();
    }

    List<Contact> findByFirstNameOrLastName(String name);
}
