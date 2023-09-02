package com.pizzeria.pizzeriawebapp.repositories;

import com.pizzeria.pizzeriawebapp.enums.ERole;
import com.pizzeria.pizzeriawebapp.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(ERole name);
}
