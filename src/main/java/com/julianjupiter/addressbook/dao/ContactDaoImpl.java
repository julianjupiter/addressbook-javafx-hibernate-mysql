package com.julianjupiter.addressbook.dao;

import com.julianjupiter.addressbook.entity.Contact;
import com.julianjupiter.addressbook.util.PersistenceManager;

import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

class ContactDaoImpl implements ContactDao {

    @Override
    public List<Contact> findAll() {
        var entityManager = PersistenceManager.entityManager();
        List<Contact> contacts;

        try {
            TypedQuery<Contact> contactTypedQuery = entityManager.createQuery("SELECT c FROM Contact c", Contact.class);
            contacts = contactTypedQuery.getResultList();
        } finally {
            entityManager.close();
        }

        return contacts;
    }

    @Override
    public Optional<Contact> findById(Long id) {
        var entityManager = PersistenceManager.entityManager();
        Contact contact;

        try {
            contact = entityManager.find(Contact.class, id);
        } finally {
            entityManager.close();
        }

        return Optional.ofNullable(contact);
    }

    @Override
    public void save(Contact contact) {
        var entityManager = PersistenceManager.entityManager();
        EntityTransaction transaction = null;

        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(contact);
            transaction.commit();
        } catch (Exception exception) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void deleteById(Long id) {
        Optional<Contact> contactOptional = findById(id);
        contactOptional.ifPresent(contact -> {
            var entityManager = PersistenceManager.entityManager();
            EntityTransaction transaction = null;

            try {
                transaction = entityManager.getTransaction();
                transaction.begin();
                entityManager.remove(contact);
                transaction.commit();
            } catch (Exception exception) {
                if (transaction != null && transaction.isActive()) {
                    transaction.rollback();
                }
            } finally {
                entityManager.close();
            }
        });
    }
}
