package com.doczilla.students.dao;

import com.doczilla.students.exception.DaoException;

import java.util.List;

public interface AbstractDao<Entity, Key> {
    void save(Entity model) throws DaoException;

    List<Entity> findAll() throws DaoException;

    void delete(Key key) throws DaoException;
}
