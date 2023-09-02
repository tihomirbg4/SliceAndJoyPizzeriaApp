package com.pizzeria.pizzeriawebapp.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PizzaDTO {

    private Long id;
    private String name;
    private String size;
    private Double price;
    private String products;
    private String dough;
    private String imageUrl;
    private Long quantity;
    private boolean isAvailable;

}
