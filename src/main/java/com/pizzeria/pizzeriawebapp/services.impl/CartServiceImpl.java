package com.pizzeria.pizzeriawebapp.services.impl;

import com.pizzeria.pizzeriawebapp.dtos.CartItemDTO;
import com.pizzeria.pizzeriawebapp.dtos.PizzaDTO;
import com.pizzeria.pizzeriawebapp.exceptions.CartItemNotFoundException;
import com.pizzeria.pizzeriawebapp.exceptions.InvalidCartItemException;
import com.pizzeria.pizzeriawebapp.models.Cart;
import com.pizzeria.pizzeriawebapp.models.CartItem;
import com.pizzeria.pizzeriawebapp.models.Pizza;
import com.pizzeria.pizzeriawebapp.models.User;
import com.pizzeria.pizzeriawebapp.payload.request.CartItemUpdateRequest;
import com.pizzeria.pizzeriawebapp.repositories.CartItemRepository;
import com.pizzeria.pizzeriawebapp.repositories.CartRepository;
import com.pizzeria.pizzeriawebapp.repositories.PizzaRepository;
import com.pizzeria.pizzeriawebapp.repositories.UserRepository;
import com.pizzeria.pizzeriawebapp.services.CartService;
import com.pizzeria.pizzeriawebapp.utils.CartItemMapper;
import com.pizzeria.pizzeriawebapp.utils.PizzaMapper;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService{

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private PizzaRepository pizzaRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Override
    public void addToCart(CartItem cartItem, User user) {
        Cart cart = user.getCart();

        if (cart.getCartItems().isEmpty()) {
            createCart(cartItem, user);
        } else {
            updateCart(cartItem, cart);
        }
    }

    private void updateCart(CartItem cartItem, Cart cart) {
        List<CartItem> cartItemList = cart.getCartItems();

        var optItem = cartItemList.stream().filter(x -> x.getProductId() == cartItem.getId()).findFirst();
        CartItem item;
        if (optItem.isPresent()) {
            item = optItem.get();
            Long existingQuantity = item.getQuantity();
            existingQuantity += 1;
            item.setQuantity(existingQuantity);
        } else {
            item = new CartItem();
            item.setProductId(cartItem.getId());
            item.setQuantity(cartItem.getQuantity());
            item.setCart(cart);
            item.setCreationDate(new Date());
        }

        cartItemList.add(item);

        cartItemRepository.save(item);
        cartRepository.save(cart);
    }

    @Override
    @Transactional
    public void removeItem(Long productId) {
        List<CartItem> cartItemList = getCartItems();
        Optional<CartItem> deletedItem = cartItemList.stream()
                .filter(item -> item.getProductId().equals(productId))
                .findFirst();

        CartItem deletedCartItem = deletedItem.orElseThrow(() ->
                new CartItemNotFoundException("Item with this id doesn't exist")
        );

        cartItemRepository.delete(deletedCartItem);
    }

    private void throwIfInvalid(CartItem cartItem) {
        if (cartItem == null || cartItem.getId() == null) {
            throw new InvalidCartItemException("Invalid cart item");
        }
    }

    @Transactional
    public void createCart(CartItem cartItem, User user) {
        Cart cart = user.getCart();
        List<CartItem> cartItemList = new ArrayList<>(cart.getCartItems());
        throwIfInvalid(cartItem);

        cart.setUser(user);
        cart.setCreatedDate(new Date());
        cart.setCartItems(cartItemList);

        cartItem.setCart(cart);
        cartItem.setQuantity(cartItem.getQuantity());
        cartItem.setProductId(cartItem.getId());
        cartItem.setCreationDate(new Date());
        cartRepository.save(cart);

        cartItemRepository.save(cartItem);

        cartItemList.add(new CartItem(cartItem.getId(), cartItem.getQuantity(), cart, cartItem.getProductId(), cartItem.getCreationDate()));
    }

    @Override
    public void updateCartItem(Long cartItemId, CartItemUpdateRequest newItem) {
        List<CartItem> cartItemList = getCartItems();

        Optional<CartItem> existingCartItem = cartItemList.stream().filter(item -> item.getId() == cartItemId).findFirst();

        if(existingCartItem.isEmpty()) {
            throw new CartItemNotFoundException("Item with this id doesn't exist");
        }

        if(newItem.getQuantity() == null) {
            throw new InvalidCartItemException("Invalid cart item quantity");
        }

        existingCartItem.get().setQuantity(newItem.getQuantity());

        cartItemList.add(existingCartItem.get());

        cartItemRepository.save(existingCartItem.get());
    }

    private List<CartItem> getCartItems() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        User user = userRepository.findByEmail(email).get();

        Cart cart = user.getCart();

        return cart.getCartItems();
    }

    @Override
    public List<PizzaDTO> findAllByProductId() {
        List<Pizza> pizzas = pizzaRepository.findAllById(getAllProductIds());

        return pizzas.stream().map(PizzaMapper::pizzaToDTO).collect(Collectors.toList());
    }

    private Set<Long> getAllProductIds() {
        Set<CartItem> cartItems = new HashSet<>(cartItemRepository.findAll());

        return cartItems.stream().map(CartItem::getProductId).collect(Collectors.toSet());
    }

    private Set<Long> getAllCartItemIds() {
        Set<CartItem> items = new HashSet<>(cartItemRepository.findAll());

        return items.stream().map(CartItem::getId).collect(Collectors.toSet());
    }


    @Override
    public List<CartItemDTO> findItemsInCart() {
        User user = getCurrentLoggedUser();
        Cart cart = user.getCart();

        List<CartItem> cartItemList = new ArrayList<>(cart.getCartItems());

        Collections.sort(cartItemList);

        List<Pizza> pizzas = pizzaRepository.findAllById(getAllProductIds());

        Collections.sort(pizzas);
        List<CartItemDTO> cartItemsDTO = new ArrayList<>();

        for (int i = cartItemList.size() - 1; i >= 0; i--) {
            Long productId = cartItemList.get(i).getProductId();

            Pizza matchingPizza = pizzas.stream()
                    .filter(pizza -> pizza.getId() == productId)
                    .findFirst()
                    .orElse(null);

            if (matchingPizza != null) {
                CartItemDTO cartItemDTO = CartItemMapper.cartItemToDTO(cartItemList.get(i), matchingPizza);

                cartItemsDTO.add(cartItemDTO);
            }
        }

        return cartItemsDTO;
    }

    @NotNull
    private User getCurrentLoggedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        return userRepository.findByEmail(email).get();
    }

    @Override
    public Long getTotalProductQuantitiesInCart() {
        List<CartItem> cartItems = getCartItems();

        return cartItems.stream().mapToLong(CartItem::getQuantity).sum();
    }
}
