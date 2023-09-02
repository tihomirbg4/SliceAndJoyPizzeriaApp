import { Screen } from "../components/Screen";
import React from "react";
import PizzaCard from "../components/cards/PizzaCard";
import { GetAllPizzasQuery } from "../services/PizzaService";
import menuBackgroundImage from "../assets/img/menu_background.jpg";

export default function MenuScreen() {
    const { data, isLoading, isError } = GetAllPizzasQuery();

    if (isLoading) {
        return <h1>...Loading</h1>;
    }
    if (isError || !data) {
        return <h1>Pizza not found</h1>;
    }

    const pizzas = Object.keys(data).map((key) => data[key]);

    console.log(pizzas);

    return (
        <Screen>
            <div className="overflow-hidden">
                <div
                    className="min-h-screen flex flex-col items-center justify-center"
                    style={{
                        backgroundImage: `url(${menuBackgroundImage})`,
                        backgroundSize: "cover",
                        backgroundPosition: "center",
                        width: "100%"
                    }}
                >
                    <div className="container mx-auto">
                        <div className="grid grid-cols-1 lg:grid-cols-4 gap-5 mb-24">
                            {pizzas.map((pizza) => (
                                <PizzaCard key={pizza.id} details={pizza} />
                            ))}
                        </div>
                    </div>
                </div>
            </div>
        </Screen>
    );
}
