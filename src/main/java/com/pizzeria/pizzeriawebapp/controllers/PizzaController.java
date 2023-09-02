package com.pizzeria.pizzeriawebapp.controllers;

import com.pizzeria.pizzeriawebapp.models.Pizza;
import com.pizzeria.pizzeriawebapp.services.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/pizzas")
public class PizzaController {

    @Autowired
    private PizzaService pizzaService;

    @GetMapping("/all")
    public ResponseEntity<List<com.pizzeria.pizzeriawebapp.dtos.PizzaDTO>> getAllPizzas() {
        List<com.pizzeria.pizzeriawebapp.dtos.PizzaDTO> pizzas = pizzaService.getAllPizzas();

        return new ResponseEntity<>(pizzas, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<com.pizzeria.pizzeriawebapp.dtos.PizzaDTO> getPizzaById(@PathVariable("id") Long id) {
        com.pizzeria.pizzeriawebapp.dtos.PizzaDTO pizza = pizzaService.findPizzaById(id);

        return new ResponseEntity<>(pizza, HttpStatus.OK);
    }

//    @PostMapping("/add")
//    public ResponseEntity<com.pizzeria.pizzeriawebapp.dtos.PizzaDTO> createPizza(@RequestBody Pizza pizza) {
//        com.pizzeria.pizzeriawebapp.dtos.PizzaDTO newPizza = pizzaService.createPizza(pizza);
//
//        return new ResponseEntity<>(newPizza, HttpStatus.CREATED);
//    }
//
//    @PutMapping("/update/{id}")
//    public ResponseEntity<com.pizzeria.pizzeriawebapp.dtos.PizzaDTO> updatePizza(@PathVariable("id") Long id, @RequestBody Pizza pizza) {
//        com.pizzeria.pizzeriawebapp.dtos.PizzaDTO updatedPizza = pizzaService.updatePizza(id, pizza);
//
//        return new ResponseEntity<>(updatedPizza, HttpStatus.OK);
//    }
//
//    @DeleteMapping("/delete/{id}")
//    public ResponseEntity<?> deletePizza(@PathVariable("id") Long id) {
//        pizzaService.deletePizza(id);
//
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
}
