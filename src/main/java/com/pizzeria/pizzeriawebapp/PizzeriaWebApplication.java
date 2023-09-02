package com.pizzeria.pizzeriawebapp;

import com.pizzeria.pizzeriawebapp.repositories.PizzaRepository;
import com.pizzeria.pizzeriawebapp.services.impl.DatabaseInitializerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
@Slf4j
@RequiredArgsConstructor
public class PizzeriaWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(PizzeriaWebApplication.class, args);
	}

	private final DatabaseInitializerService databaseInitializerService;
	private final PizzaRepository pizzaRepository;

	@EventListener
	void initializeDatabase(ContextRefreshedEvent event) {
		if (pizzaRepository.count() == 0) {
			log.info("Initialize database!");
			databaseInitializerService.populateDB();
			log.info("Database initialized!");
		}
	}
}
