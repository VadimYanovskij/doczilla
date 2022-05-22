package com.doczilla.students.repository;

import java.util.List;

public interface CommonRepository<T> {

    void save(T model);

    List<T> findAll();

    void delete(T model);
}
