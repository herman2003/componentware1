package com.componentware.wasserdatabase;

import com.componentware.wasserdatabase.service.DataInitializationProduktService;
import com.componentware.wasserdatabase.service.LogistikService;
import com.componentware.wasserdatabase.service.SensorSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WasserdatabaseApplication implements CommandLineRunner {
	@Autowired
	SensorSenderService sensorsenderService;
	@Autowired
	LogistikService logistikService;
	@Autowired
	DataInitializationProduktService dataInitializationProduktService;
	public static void main(String[] args) {
		SpringApplication.run(WasserdatabaseApplication.class, args);
	}
	@Override
	public void run(String... args) throws Exception {
		sensorsenderService.generateAndSendData();
		dataInitializationProduktService.run();
		logistikService.setupMqttListener();
	}
}
