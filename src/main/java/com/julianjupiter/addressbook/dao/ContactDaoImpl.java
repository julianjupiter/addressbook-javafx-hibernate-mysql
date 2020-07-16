package com.julianjupiter.addressbook.dao;

import com.julianjupiter.addressbook.entity.Contact;
import com.julianjupiter.addressbook.util.PersistenceManager;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

class ContactDaoImpl implements ContactDao {
    private final EntityManager entityManager;
    private EntityTransaction transaction;

    ContactDaoImpl() {
        this.entityManager = PersistenceManager.entityManager();
    }

    @Override
    public List<Contact> findAll() {
        List<Contact> contacts;

        try {
            TypedQuery<Contact> contactTypedQuery = entityManager.createQuery("SELECT c FROM Contact c", Contact.class);
            contacts = contactTypedQuery.getResultList();
        } finally {
            entityManager.close();
            PersistenceManager.close();
        }

        return contacts;
    }

    @Override
    public Optional<Contact> findById(Long id) {
        Contact contact;

        try {
            contact = entityManager.find(Contact.class, id);
        } finally {
            entityManager.close();
            PersistenceManager.close();
        }

        return Optional.ofNullable(contact);
    }

    @Override
    public void save(Contact contact) {
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(contact);
            transaction.commit();
        } catch (Exception exception) {
            transaction.rollback();
        } finally {
            entityManager.close();
            PersistenceManager.close();
        }
    }

    @Override
    public void deleteById(Long id) {
        Optional<Contact> contactOptional = findById(id);
        contactOptional.ifPresent(contact -> {
            try {
                transaction = entityManager.getTransaction();
                transaction.begin();
                entityManager.remove(contact);
                transaction.commit();
            } catch (Exception exception) {
                transaction.rollback();
            } finally {
                entityManager.close();
                PersistenceManager.close();
            }
        });
    }
}
