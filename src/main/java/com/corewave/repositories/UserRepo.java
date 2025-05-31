package com.corewave.repositories;

import com.corewave.models.User;

import java.util.List;
import java.util.Optional;

public class UserRepo implements _CrudRepo<User> {
    @Override
    public void add(User obj) {

    }

    @Override
    public List<User> get() {
        return List.of();
    }

    @Override
    public Optional<User> getById(int id) {
        return Optional.empty();
    }

    @Override
    public void updateById(int id, User uObj) {

    }

    @Override
    public void deleteById(int id) {

    }
}
