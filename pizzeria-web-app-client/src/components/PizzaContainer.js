import React from "react";
import { useQuery } from "@tanstack/react-query";
import { getAllPizzas } from "../services/PizzaService";
import PizzaCard from "./cards/PizzaCard";

const PizzaContainer = () => {
    const pizzasQuery = useQuery({
        queryKey: ["pizzas"],
        queryFn: getAllPizzas
    });

    let pizzas = null;

    if (pizzasQuery.data) {
        pizzas = Object.keys(pizzasQuery.data).map((key) => {
            return <PizzaCard key={key} details={pizzasQuery.data[key]} />;
        });
    }

    return { pizzas };
};

export default PizzaContainer;
