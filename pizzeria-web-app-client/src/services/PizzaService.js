import { useQuery } from "@tanstack/react-query";
import authHeader from "./auth-header";
import api from "./api";

export const GetAllPizzasQuery = () => {
    const { data, isError, isLoading } = useQuery({
        queryKey: ["pizzas"],
        queryFn: async () =>
            await api.get("pizzas/all", {
                headers: authHeader()
            })
    });

    return { data: data?.data, isError, isLoading };
};

export const GetPizzaByIdQuery = (id) => {
    const { data, isError, isLoading } = useQuery({
        queryKey: ["pizza", id],
        queryFn: async () => await api.get(`pizzas/${id}`)
    });

    return { data: data?.data, isError, isLoading };
};
