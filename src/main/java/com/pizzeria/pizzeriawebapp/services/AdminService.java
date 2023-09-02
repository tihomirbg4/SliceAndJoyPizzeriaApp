package com.pizzeria.pizzeriawebapp.services;

import com.pizzeria.pizzeriawebapp.enums.ERole;
import com.pizzeria.pizzeriawebapp.models.User;

import java.util.List;

public interface AdminService {

    User addEmployee(User user);
    List<User> getAllEmployees();
    void deleteEmployee(Long employeeId);
    User changeEmployeeRole(Long employeeId, ERole newRole);
}
