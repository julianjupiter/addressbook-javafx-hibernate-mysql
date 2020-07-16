package com.julianjupiter.addressbook.service;

import com.julianjupiter.addressbook.dao.ContactDao;
import com.julianjupiter.addressbook.entity.Contact;

import java.util.List;
import java.util.Optional;

public interface ContactService {
    static ContactService create(ContactDao contactDao) {
        return new ContactServiceImpl(contactDao);
    }

    List<Contact> findAll();

    Optional<Contact> findById(Long id);

    void save(Contact contact);

    void deleteById(Long id);
}
