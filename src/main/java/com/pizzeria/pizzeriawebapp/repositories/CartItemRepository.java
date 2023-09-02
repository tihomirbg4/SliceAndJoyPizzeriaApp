package com.pizzeria.pizzeriawebapp.repositories;

import com.pizzeria.pizzeriawebapp.models.Cart;
import com.pizzeria.pizzeriawebapp.models.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    Cart findCartByCartId(Long cartId);
//    Set<Long> findQuantitiesByCartItemIds(Set<Long> productIds);
}
