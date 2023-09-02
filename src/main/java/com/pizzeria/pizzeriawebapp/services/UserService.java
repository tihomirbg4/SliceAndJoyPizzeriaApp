package com.pizzeria.pizzeriawebapp.services;

import com.pizzeria.pizzeriawebapp.dtos.UserDTO;
import com.pizzeria.pizzeriawebapp.models.User;
import com.pizzeria.pizzeriawebapp.payload.request.UpdatedUserRequest;

public interface UserService {

    UserDTO registerUser(User user);
    void updateUserProfile(Long userId, UpdatedUserRequest updatedUser);
}
