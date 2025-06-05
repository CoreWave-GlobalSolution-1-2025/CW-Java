package com.corewave.services;

import com.corewave.models.User;

public class UserService {

    public static boolean checkUserToCreate(User user) {
        return
                user.getName() != null &&
                        user.getEmail() != null &&
                        user.getPassword() != null;
    }

    public static boolean checkUSerToUpdate(User user) {
        return
                user.getPassword() != null ||
                        user.getName() != null ||
                        user.getEmail() != null;
    }
}
