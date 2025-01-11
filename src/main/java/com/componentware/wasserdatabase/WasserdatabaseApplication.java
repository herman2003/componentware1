package com.componentware.wasserdatabase;

import com.componentware.wasserdatabase.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WasserdatabaseApplication implements CommandLineRunner {

	@Autowired
	private SensorSenderService sensorsenderService;

	@Autowired
	private LogistikService logistikService;

	@Autowired
	private DataInitializationProduktService dataInitializationProduktService;

	@Autowired
	private DataInitializationUserService dataInitializationUserService;

	@Autowired
	private DataInitializationSenderService dataInitializationSenderService;

	public static void main(String[] args) {
		SpringApplication.run(WasserdatabaseApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// Initialiser les utilisateurs dans la base de données en premier
		dataInitializationUserService.run();

		// Initialiser les produits après les utilisateurs
		dataInitializationProduktService.run();

		// Initialiser les senders après les produits
		dataInitializationSenderService.run();

		// Démarrer la génération et l'envoi de données
		sensorsenderService.generateAndSendData();

		// Initialiser le listener MQTT
		logistikService.setupMqttListener();
	}
}
