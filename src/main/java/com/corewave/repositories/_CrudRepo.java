package com.corewave.repositories;

import java.util.List;
import java.util.Optional;

public interface _CrudRepo<T> {
    void add(T obj) throws Exception;
    List<T> list() throws Exception;
    Optional<T> getById(int id) throws Exception;
    void updateById(int id, T uObj) throws Exception;
    void deleteById(int id) throws Exception;
}
