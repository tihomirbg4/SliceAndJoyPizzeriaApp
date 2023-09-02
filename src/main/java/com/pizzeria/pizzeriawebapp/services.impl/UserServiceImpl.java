package com.pizzeria.pizzeriawebapp.services.impl;

import com.pizzeria.pizzeriawebapp.dtos.UserDTO;
import com.pizzeria.pizzeriawebapp.exceptions.PasswordDoNotMatchException;
import com.pizzeria.pizzeriawebapp.exceptions.UserAlreadyExistsException;
import com.pizzeria.pizzeriawebapp.models.User;
import com.pizzeria.pizzeriawebapp.payload.request.UpdatedUserRequest;
import com.pizzeria.pizzeriawebapp.repositories.RoleRepository;
import com.pizzeria.pizzeriawebapp.repositories.UserRepository;
import com.pizzeria.pizzeriawebapp.services.UserService;
import com.pizzeria.pizzeriawebapp.utils.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    @Transactional
    public UserDTO registerUser(User user) {

        if (userRepository.findByEmail(user.getEmail()).isEmpty()) {
            throw new UserAlreadyExistsException("User with this email already exists");
        }

        user.setEmail(user.getEmail());
        user.setFirstName(user.getFirstName());
        user.setLastName(user.getLastName());
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return UserMapper.userToDTO(userRepository.save(user));
    }

    public void updateUserProfile(Long userId, UpdatedUserRequest updatedUser) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (updatedUser.getCurrentPassword() == null || updatedUser.getCurrentPassword().isEmpty() || !passwordEncoder.matches(updatedUser.getCurrentPassword(), existingUser.getPassword())) {
            throw new PasswordDoNotMatchException("Current password does not match.");
        }

        existingUser.setFirstName(updatedUser.getFirstName());
        existingUser.setLastName(updatedUser.getLastName());
        existingUser.setEmail(updatedUser.getEmail());

        if (updatedUser.getNewPassword() != null && !updatedUser.getNewPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(updatedUser.getNewPassword()));
        }

        userRepository.save(existingUser);
    }
}
