package com.pizzeria.pizzeriawebapp.services.impl;

import com.pizzeria.pizzeriawebapp.exceptions.PizzaNotFoundException;
import com.pizzeria.pizzeriawebapp.models.Pizza;
import com.pizzeria.pizzeriawebapp.repositories.PizzaRepository;
import com.pizzeria.pizzeriawebapp.services.PizzaService;
import com.pizzeria.pizzeriawebapp.utils.PizzaMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PizzaServiceImpl implements PizzaService {

    @Autowired
    private PizzaRepository pizzaRepository;

    @Override
    public List<com.pizzeria.pizzeriawebapp.dtos.PizzaDTO> getAllPizzas() {
        return pizzaRepository.findAll().stream()
                .map(PizzaMapper::pizzaToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public com.pizzeria.pizzeriawebapp.dtos.PizzaDTO findPizzaById(Long id) {
        Optional<Pizza> pizza = pizzaRepository.findById(id);

        if(pizza.isEmpty()) {
            throw new PizzaNotFoundException("Pizza with this id doesn't exist");
        }

        return pizza.map(PizzaMapper::pizzaToDTO).orElse(null);
    }

    @Override
    public com.pizzeria.pizzeriawebapp.dtos.PizzaDTO createPizza(Pizza pizza) {
        return PizzaMapper.pizzaToDTO(pizzaRepository.save(pizza));
    }

    @Override
    @Transactional
    public com.pizzeria.pizzeriawebapp.dtos.PizzaDTO updatePizza(Long id, Pizza pizza) {
        pizza.setId(id);

        return PizzaMapper.pizzaToDTO(pizzaRepository.save(pizza));
    }

    @Override
    @Transactional
    public void deletePizza(Long id) {
        pizzaRepository.deletePizzaById(id);
    }
}
