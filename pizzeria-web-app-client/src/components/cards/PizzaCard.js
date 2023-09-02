import React from "react";
import { Link } from "react-router-dom";
import { useQueryClient } from "@tanstack/react-query";
import { ChangePizzaAvailability } from "../../services/EmployeeService";
import AuthService from "../../services/AuthService";

const PizzaCard = (props) => {
    const { id, name, products, imageUrl, available } = props.details;
    const currentLoggedUser = AuthService.getCurrentUser();
    const queryClient = useQueryClient();

    const isEmployee = currentLoggedUser?.roles?.includes("ROLE_EMPLOYEE");

    const changePizzaAvailabilityMutation =
        ChangePizzaAvailability(queryClient);

    return (
        <div
            className={`text-center border-2 border-gray-400 rounded-md shadow-md ${
                !available && !isEmployee ? "bg-red-400" : "bg-black"
            } bg-opacity-50`}
        >
            <div className="container mt-5">
                <Link
                    to={
                        isEmployee
                            ? "#"
                            : available
                            ? `/pizza-view/${id}`
                            : null
                    }
                    className={`no-underline ${
                        !available ? "cursor-default" : ""
                    }`}
                >
                    <img
                        className="h-48 w-48 m-auto"
                        src={imageUrl}
                        alt="adgadg"
                    />
                    <h3 className="text-white">{name}</h3>
                    <p className="text-white">{products}</p>
                    <div id="pizza-order-info">
                        {isEmployee ? (
                            <button
                                className="input-button w-64"
                                onClick={() =>
                                    changePizzaAvailabilityMutation.mutate(id)
                                }
                            >
                                Промени наличност
                            </button>
                        ) : (
                            <button
                                className={`input-button w-64 ${
                                    !available ? "cursor-default" : ""
                                }`}
                            >
                                {available ? "Изберете" : "Изчерпана"}
                            </button>
                        )}
                    </div>
                </Link>
            </div>
        </div>
    );
};

export default PizzaCard;
