import { useMutation } from "@tanstack/react-query";
import api from "./api";
import authHeader from "./auth-header";

export const UpdateOrderStatus = (queryClient) => {
    return useMutation(
        async ({ orderId, newStatus }) => {
            const response = await api.put(
                `employee/orders/updateStatus`,
                { orderId, newStatus },
                {
                    headers: authHeader()
                }
            );
            return response.data;
        },
        {
            onSuccess: (data) => {
                console.log("Order status updated:", data);

                queryClient.invalidateQueries("userOrders");
            },
            onError: (error) => {
                console.error("Error updating order status:", error);
            }
        }
    );
};

export const ChangePizzaAvailability = (queryClient) => {
    return useMutation(
        async (pizzaId) => {
            const response = await api.post(
                `employee/markPizza/${pizzaId}`,
                null,
                {
                    headers: authHeader()
                }
            );
            return response.data;
        },
        {
            onSuccess: (data) => {
                console.log("Pizza availability changed:", data);

                queryClient.invalidateQueries("pizzas");
            },
            onError: (error) => {
                console.error("Error changing pizza availability:", error);
            }
        }
    );
};
