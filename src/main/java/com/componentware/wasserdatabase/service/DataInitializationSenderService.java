package com.componentware.wasserdatabase.service;

import com.componentware.wasserdatabase.entity.*;
import com.componentware.wasserdatabase.repository.*;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Random;

@Component
public class DataInitializationSenderService implements CommandLineRunner {

    @Autowired
    private SenderRepository senderRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SensorRepository sensorRepository;
    @Autowired
    private ContainerRepository containerRepository;

    @Override
    public void run(String... args) throws Exception{
        // Vérifier si l'utilisateur "admin@example.com" existe
        User defaultUser = userRepository.findByEmail("admin@example.com");
        if (defaultUser == null) {
            throw new RuntimeException("Aucun utilisateur trouvé. Veuillez initialiser un utilisateur.");
        }

        // Créer 5 Senders pour l'utilisateur
        Random random = new Random();
        for (int i = 1; i <= 5; i++) {
            String location = "Location" + i;
            float minimalStand = 20 + random.nextFloat() * 100;  // Valeur aléatoire pour le minimalStand

            // Charger les premiers capteurs et containers
            Sensor sensor = sensorRepository.getFirstById(31L);
            Container container = containerRepository.getFirstById(1L);

            // Créer un nouveau Sender pour cet utilisateur
            Sender sender = new Sender(null, location, minimalStand, sensor, defaultUser, container);
            senderRepository.save(sender);
        }
    }
}
