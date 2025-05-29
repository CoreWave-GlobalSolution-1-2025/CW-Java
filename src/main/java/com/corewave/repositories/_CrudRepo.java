package com.corewave.repositories;

import java.util.List;
import java.util.Optional;

public interface _CrudRepo<T> {
    void add(T obj);
    List<T> get();
    Optional<T> getById(int id);
    void updateById(int id, T uObj);
    void deleteById(int id);
}
