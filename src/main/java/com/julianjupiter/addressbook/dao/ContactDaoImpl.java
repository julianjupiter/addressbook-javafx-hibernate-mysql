package com.julianjupiter.addressbook.dao;

import com.julianjupiter.addressbook.entity.Contact;
import com.julianjupiter.addressbook.util.PersistenceManager;

import javax.persistence.EntityTransaction;
import java.util.List;
import java.util.Optional;

class ContactDaoImpl implements ContactDao {

    @Override
    public List<Contact> findAll() {
        var entityManager = PersistenceManager.entityManager();

        try {
            return entityManager
                    .createQuery("SELECT c FROM Contact c", Contact.class)
                    .getResultList();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Optional<Contact> findById(Long id) {
        var entityManager = PersistenceManager.entityManager();

        try {
            return Optional.ofNullable(entityManager.find(Contact.class, id));
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void save(Contact contact) {
        var entityManager = PersistenceManager.entityManager();
        EntityTransaction transaction = null;

        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            var id = contact.getId();
            if (id != null && id > 0) {
                entityManager.merge(contact);
            } else {
                entityManager.persist(contact);
            }
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
        var contactOptional = this.findById(id);

        contactOptional.ifPresent(contact -> {
            var entityManager = PersistenceManager.entityManager();
            EntityTransaction transaction = null;

            try {
                transaction = entityManager.getTransaction();
                transaction.begin();
                contact = entityManager.contains(contact) ? contact : entityManager.merge(contact);
                entityManager.remove(contact);
                transaction.commit();
            } catch (Exception exception) {
                exception.printStackTrace();
                if (transaction != null && transaction.isActive()) {
                    transaction.rollback();
                }
            } finally {
                entityManager.close();
            }
        });
    }
}
