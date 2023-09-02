import { Screen } from "../components/Screen";
import UseId from "../hooks/useId";
import { GetPizzaByIdQuery } from "../services/PizzaService";
import React from "react";
import { AddToCart } from "../services/CartService";
import { useNavigate } from "react-router";
import menuBackgroundImage from "../assets/img/menu_background.jpg";

export default function PizzaViewScreen() {
    const id = UseId();
    const result = GetPizzaByIdQuery(id);

    const navigate = useNavigate();

    const addToCartMutation = AddToCart({
        id: id,
        quantity: 1
    });

    console.log(addToCartMutation?.error?.response?.status);

    if (addToCartMutation?.error?.response?.status === 401) {
        navigate("/signin");
    }

    if (result.isLoading) return <h1>...Loading</h1>;
    const pizza = result.data;
    if (result.isError || !pizza) return <h1>Pizza not found</h1>;

    return (
        <Screen>
            <div
                className="min-h-screen flex flex-col items-center justify-center"
                style={{
                    backgroundImage: `url(${menuBackgroundImage})`,
                    backgroundSize: "cover",
                    backgroundPosition: "center",
                    width: "100%"
                }}
            >
                <div className="container mx-auto text-center">
                    <img
                        className="h-72 w-72 m-auto"
                        src={pizza.imageUrl}
                        alt="adgadg"
                    />
                    <div className="mt-4">
                        <h3 className="text-white">{pizza.name}</h3>
                        <p className="text-white">{pizza.products}</p>
                        <h3 className="text-white">{pizza.price} лв</h3>
                    </div>
                    <button
                        className="input-button w-64 m-auto mt-4"
                        onClick={() => {
                            addToCartMutation.mutate();
                        }}
                    >
                        Добави в количката
                    </button>
                </div>
            </div>
        </Screen>
    );
}
