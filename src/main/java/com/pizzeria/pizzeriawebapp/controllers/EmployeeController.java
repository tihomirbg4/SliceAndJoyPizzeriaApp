package com.pizzeria.pizzeriawebapp.controllers;

import com.pizzeria.pizzeriawebapp.payload.request.UpdateOrderStatusRequest;
import com.pizzeria.pizzeriawebapp.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PreAuthorize(" hasRole('EMPLOYEE')")
    @PutMapping("/employee/orders/updateStatus")
    public void updateOrderStatus(@RequestBody UpdateOrderStatusRequest request) {
        employeeService.updateOrderStatus(request.getOrderId(), request.getNewStatus());
    }

    @PreAuthorize(" hasRole('EMPLOYEE')")
    @PostMapping("/employee/markPizza/{pizzaId}")
    public ResponseEntity<String> changePizzaAvailability(@PathVariable Long pizzaId) {
        employeeService.changePizzaAvailability(pizzaId);
        return ResponseEntity.ok("Pizza availability changed successfully");
    }
}
