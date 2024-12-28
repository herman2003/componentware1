package com.componentware.wasserdatabase;

import com.componentware.wasserdatabase.service.SensorsenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WasserdatabaseApplication implements CommandLineRunner {
	@Autowired
	SensorsenderService sensorsenderService;
	public static void main(String[] args) {
		SpringApplication.run(WasserdatabaseApplication.class, args);
	}
	@Override
	public void run(String... args) throws Exception {
		sensorsenderService.generateAndSendData();
	}
}
