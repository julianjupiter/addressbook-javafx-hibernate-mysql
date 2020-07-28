package com.julianjupiter.addressbook.dao;

import com.julianjupiter.addressbook.entity.Contact;

public interface ContactDao extends Dao<Contact, Long> {
    static ContactDao create() {
        return new ContactDaoImpl();
    }
}
