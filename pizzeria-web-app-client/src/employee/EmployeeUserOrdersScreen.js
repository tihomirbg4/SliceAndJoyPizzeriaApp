import React, { useState } from "react";
import { Screen } from "../components/Screen";
import { GetAllUserOrders } from "../services/OrderService";
import { useQueryClient } from "@tanstack/react-query";
import { handleUnauthorized } from "../services/HttpService";
import { UpdateOrderStatus } from "../services/EmployeeService";

const EmployeeUserOrdersScreen = () => {
    const { data: userOrders, isLoading, isError } = GetAllUserOrders();
    const [editedStatus, setEditedStatus] = useState({});

    const queryClient = useQueryClient();

    const updateOrderStatusMutation = UpdateOrderStatus(queryClient);

    if (isError) {
        handleUnauthorized();
    }

    if (isLoading) {
        return <p>Loading...</p>;
    }

    console.log(userOrders);

    const statusOptions = [
        "Поръчката е направена",
        "Подготовка",
        "Печене",
        "Проверка на качеството",
        "За доставка",
        "Доставена",
        "Отказана"
    ];

    const statusMapping = {
        "Поръчката е направена": "PENDING",
        Подготовка: "PROCESSING",
        Печене: "BAKING",
        "Проверка на качеството": "QUALITY_CHECK",
        "За доставка": "OUT_FOR_DELIVERY",
        Доставена: "DELIVERED",
        Отказана: "CANCELED"
    };

    const handleStatusChange = (orderId, event) => {
        const { value } = event.target;
        const englishStatus = statusMapping[value];
        {
            console.log(englishStatus);
        }
        setEditedStatus((prevStatus) => ({
            ...prevStatus,
            [orderId]: value
        }));

        // Invoke the mutation when status is changed
        updateOrderStatusMutation.mutate({
            orderId,
            newStatus: englishStatus
        });
    };

    const formatDate = (date) => {
        const options = {
            day: "2-digit",
            month: "2-digit",
            year: "numeric",
            hour: "2-digit",
            minute: "2-digit",
            hour12: false // Use 24-hour format
        };

        const formattedDate = new Intl.DateTimeFormat("en-US", options).format(
            new Date(date)
        );

        return formattedDate;
    };

    return (
        <Screen>
            <div className="flex flex-col h-screen mx-4 mt-8">
                {userOrders.length === 0 ? (
                    <h1
                        className="text-2xl font-bold mb-4"
                        style={{ color: "#55311c" }}
                    >
                        Няма активни поръчки
                    </h1>
                ) : (
                    <div>
                        <h1
                            className="text-2xl font-bold mb-4"
                            style={{ color: "#55311c" }}
                        >
                            Всички Поръчки
                        </h1>
                        <div className="flex-grow max-w-4xl mx-auto">
                            <table className="w-full border-collapse border border-gray-300">
                                <thead className="bg-gray-100">
                                    <tr>
                                        <th className="p-3">
                                            Дата на поръчката
                                        </th>
                                        <th className="p-3">
                                            Адрес за доставка
                                        </th>
                                        <th className="p-3">Обща цена</th>
                                        <th className="p-3">Статус</th>
                                        <th className="p-3">
                                            Име на потребител
                                        </th>
                                        <th className="p-3">Телефонен номер</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    {userOrders.map((order) => (
                                        <tr
                                            key={order.id}
                                            className={`border-t border-gray-300 ${
                                                order.status === "Отказана"
                                                    ? "bg-red-200"
                                                    : order.status ===
                                                      "Доставена"
                                                    ? "bg-green-200"
                                                    : "bg-white"
                                            }`}
                                        >
                                            <td className="p-3">
                                                {formatDate(
                                                    order.orderDate,
                                                    "dd.MM.yyyy HH:mm:ss"
                                                )}
                                            </td>
                                            <td className="p-3">
                                                {order.deliveryAddress}
                                            </td>
                                            <td className="p-3">
                                                {order.totalPrice.toFixed(2)}{" "}
                                                лв.
                                            </td>
                                            <td
                                                className={`p-3 text-${order.status.toLowerCase()}`}
                                            >
                                                {/* Dropdown for status */}
                                                <select
                                                    value={
                                                        editedStatus[
                                                            order.id
                                                        ] || order.status
                                                    }
                                                    onChange={(event) =>
                                                        handleStatusChange(
                                                            order.id,
                                                            event
                                                        )
                                                    }
                                                >
                                                    {statusOptions.map(
                                                        (status) => (
                                                            <option
                                                                key={status}
                                                                value={status}
                                                            >
                                                                {status}
                                                            </option>
                                                        )
                                                    )}
                                                </select>
                                            </td>
                                            <td className="p-3">
                                                {order.username}
                                            </td>
                                            <td className="p-3">
                                                {order.customerTelephone}
                                            </td>
                                        </tr>
                                    ))}
                                </tbody>
                            </table>
                        </div>
                    </div>
                )}
            </div>
        </Screen>
    );
};

export default EmployeeUserOrdersScreen;
