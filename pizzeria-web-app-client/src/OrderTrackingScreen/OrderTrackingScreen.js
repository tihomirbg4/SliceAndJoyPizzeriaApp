import React, { useState, useEffect } from "react";
import { Screen } from "../components/Screen";
import { BiCheck, BiTimeFive } from "react-icons/bi";
import { useParams } from "react-router-dom";
import pizzaBackground from "../assets/img/pizzaBackground.jpg";
import { GetOrderById } from "../services/OrderService";

const OrderTrackingScreen = () => {
    const [orderStatus, setOrderStatus] = useState(1);
    const { orderId } = useParams();

    console.log(orderId);

    const { data: orderData, isLoading, isError } = GetOrderById(orderId);

    useEffect(() => {
        if (orderData) {
            setOrderStatus(orderStatusMapping[orderData.status]);
        }
    }, [orderData]);

    if (isLoading) {
        return <p>Loading...</p>;
    }

    if (isError) {
        return <p>Error fetching order data</p>;
    }

    const orderStatusMapping = {
        "Поръчката е направена": 1,
        Подготовка: 2,
        Печене: 3,
        "Проверка на качеството": 4,
        "За доставка": 5,
        Доставена: 6
    };

    const renderStep = (stepNumber, stepName) => {
        const stepIconColor =
            orderStatus >= stepNumber ? "text-green-500" : "text-gray-500";

        return (
            <div className="flex items-center">
                <div className={`mr-2 ${stepIconColor}`}>
                    {orderStatus >= stepNumber ? (
                        <BiCheck className="w-6 h-6" />
                    ) : (
                        <BiTimeFive className="w-6 h-6" />
                    )}
                </div>
                <div className="text-sm">{stepName}</div>
            </div>
        );
    };

    return (
        <Screen>
            <div
                className="flex justify-center items-center h-screen"
                style={{
                    backgroundImage: `url(${pizzaBackground})`,
                    backgroundSize: "cover",
                    backgroundPosition: "center"
                }}
            >
                <div className="bg-white p-8 rounded shadow-md w-96 h-96 absolute flex justify-center items-center px-0 py-0 right-56">
                    <div>
                        {renderStep(1, "Поръчката е направена")}
                        {renderStep(2, "Подготовка")}
                        {renderStep(3, "Печене")}
                        {renderStep(4, "Проверка на качеството")}
                        {renderStep(5, "Готова за доставка")}
                        {renderStep(6, "Доставена")}
                    </div>
                </div>
            </div>
        </Screen>
    );
};

export default OrderTrackingScreen;
