package com.pizzeria.pizzeriawebapp.services.impl;

import com.pizzeria.pizzeriawebapp.exceptions.UnauthorizedException;
import com.pizzeria.pizzeriawebapp.enums.ERole;
import com.pizzeria.pizzeriawebapp.models.Role;
import com.pizzeria.pizzeriawebapp.models.User;
import com.pizzeria.pizzeriawebapp.repositories.RoleRepository;
import com.pizzeria.pizzeriawebapp.repositories.UserRepository;
import com.pizzeria.pizzeriawebapp.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public User addEmployee(User employee) {
        employee.setPassword(passwordEncoder.encode(employee.getPassword()));
        Role role = roleRepository.findByName(ERole.ROLE_EMPLOYEE).get();

        if(!employee.getRoles().contains(role)) {
            employee.getRoles().add(role);
        }

        return userRepository.save(employee);
    }

    @Override
    public List<User> getAllEmployees() {
        return userRepository.findAll().stream()
                .filter(user -> user.getRoles().stream()
                        .anyMatch(role -> role.getName() == ERole.ROLE_EMPLOYEE))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteEmployee(Long employeeId) {
        Optional<User> employeeOptional = userRepository.findById(employeeId);
        if (employeeOptional.isPresent()) {
            User employee = employeeOptional.get();

            if (employee.getRoles().stream()
                    .anyMatch(role -> role.getName() == ERole.ROLE_EMPLOYEE)) {
                userRepository.delete(employee);
            } else {
                throw new UnauthorizedException("Only users with ROLE_EMPLOYEE can be deleted.");
            }
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }

    @Override
    @Transactional
    public User changeEmployeeRole(Long employeeId, ERole newRole) {
        Optional<User> userOptional = userRepository.findById(employeeId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();

            Role role = roleRepository.findByName(newRole).orElseThrow(() ->
                    new IllegalArgumentException("Role not found")
            );

            user.getRoles().clear();
            user.getRoles().add(role);

            return userRepository.save(user);
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }
}

