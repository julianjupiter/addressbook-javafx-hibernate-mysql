package com.julianjupiter.addressbook.dao;

import com.julianjupiter.addressbook.entity.Contact;

import java.util.List;
import java.util.Optional;

public interface Dao<T, ID> {
    List<T> findAll();

    Optional<T> findById(ID id);

    void save(T t);

    void deleteById(ID id);
}
