package com.pizzeria.pizzeriawebapp.utils;

import com.pizzeria.pizzeriawebapp.dtos.UserDTO;
import com.pizzeria.pizzeriawebapp.models.User;

public class UserMapper {

    private UserMapper() {

    }

    public static UserDTO userToDTO(User user) {
        UserDTO userDTO = new UserDTO();

        userDTO.setId(user.getId());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setEmail(user.getEmail());
        userDTO.setRoles(user.getRoles());

        return userDTO;
    }
}
