package com.julianjupiter.addressbook.service;

import com.julianjupiter.addressbook.dao.ContactDao;
import com.julianjupiter.addressbook.entity.Contact;

import java.util.List;
import java.util.Optional;

class ContactServiceImpl implements ContactService {
    private final ContactDao contactDao;

    ContactServiceImpl(ContactDao contactDao) {
        this.contactDao = contactDao;
    }

    @Override
    public List<Contact> findAll() {
        return contactDao.findAll();
    }

    @Override
    public Optional<Contact> findById(Long id) {
        return contactDao.findById(id);
    }

    @Override
    public void save(Contact contact) {
        contactDao.save(contact);
    }

    @Override
    public void deleteById(Long id) {
        contactDao.deleteById(id);
    }
}
