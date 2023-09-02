package com.pizzeria.pizzeriawebapp.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pizzeria.pizzeriawebapp.bootstrap.ParsedPizza;
import com.pizzeria.pizzeriawebapp.bootstrap.ParsedPizzaMapper;
import com.pizzeria.pizzeriawebapp.enums.ERole;
import com.pizzeria.pizzeriawebapp.models.Pizza;
import com.pizzeria.pizzeriawebapp.models.Role;
import com.pizzeria.pizzeriawebapp.models.User;
import com.pizzeria.pizzeriawebapp.repositories.PizzaRepository;
import com.pizzeria.pizzeriawebapp.repositories.RoleRepository;
import com.pizzeria.pizzeriawebapp.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DatabaseInitializerService {
    public static final String PIZZA_FILE = "src/main/java/com/pizzeria/testdata/AllPizzas.json";

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PizzaRepository pizzaRepository;

    @Autowired
    private UserRepository userRepository;

    private ParsedPizza[] parsedPizzas;

    private void loadRecipesFromJson() {
        File file = new File(PIZZA_FILE);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            parsedPizzas = objectMapper.readValue(file, ParsedPizza[].class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void populateDB() {
        loadRecipesFromJson();
        List<Pizza> allPizzas = new ArrayList<>();
        allPizzas = Arrays.stream(parsedPizzas).map(ParsedPizzaMapper::toPizza).collect(Collectors.toList());
        pizzaRepository.saveAll(allPizzas);
        seedRoles();
        seedUsers();
    }

    private void seedRoles() {
        ArrayList<Role> roles = new ArrayList<>();
        roles.add(new Role(ERole.ROLE_USER));
        roles.add(new Role(ERole.ROLE_ADMIN));
        roles.add(new Role(ERole.ROLE_EMPLOYEE));
        roleRepository.saveAll(roles);
    }

    private void seedUsers() {
        List<User> users = new ArrayList<>();

        User user = new User("Тихомир", "Иванов", "tihomirbg4@gmail.com", "$2a$12$JoyRopbR7khbKaNnIQCtUei1NLqbbyafNgWLiPKNdsWQ.0c2ArpJS");
        var roles = user.getRoles();
        Role role = roleRepository.findByName(ERole.ROLE_USER).get();
        roles.add(role);
        user.setRoles(roles);
        users.add(user);

        userRepository.saveAll(users);
    }
}
