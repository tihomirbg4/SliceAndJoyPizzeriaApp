package com.pizzeria.pizzeriawebapp.utils;

import com.pizzeria.pizzeriawebapp.dtos.OrderDTO;
import com.pizzeria.pizzeriawebapp.models.Order;
import com.pizzeria.pizzeriawebapp.enums.OrderStatusEnum;
import com.pizzeria.pizzeriawebapp.models.User;

public class OrderMapper {

    public static OrderDTO convertToOrderDTO(Order order, User user) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(order.getId());
        orderDTO.setDeliveryAddress(order.getDeliveryAddress());
        orderDTO.setCustomerTelephone(order.getCustomerTelephone());
        orderDTO.setOrderDate(order.getOrderDate());
        orderDTO.setStatus(mapStatusToString(order.getStatus()));
        orderDTO.setTotalPrice(order.getTotalPrice());
        orderDTO.setUsername(user.getFirstName());

        return orderDTO;
    }

    public static String mapStatusToString(OrderStatusEnum status) {
        switch (status) {
            case PENDING:
                return "Поръчката е направена";
            case PROCESSING:
                return "Подготовка";
            case BAKING:
                return "Печене";
            case QUALITY_CHECK:
                return "Проверка на качеството";
            case OUT_FOR_DELIVERY:
                return "За доставка";
            case DELIVERED:
                return "Доставена";
            case CANCELED:
                return "Отказана";
            default:
                return null;
        }
    }
}
