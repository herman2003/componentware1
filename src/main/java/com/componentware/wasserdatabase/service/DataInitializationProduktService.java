package com.componentware.wasserdatabase.service;

import com.componentware.wasserdatabase.entity.Container;
import com.componentware.wasserdatabase.entity.Sensor;
import com.componentware.wasserdatabase.entity.Produkt;
import com.componentware.wasserdatabase.repository.ContainerRepository;
import com.componentware.wasserdatabase.repository.SensorRepository;
import com.componentware.wasserdatabase.repository.ProduktRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class DataInitializationProduktService implements CommandLineRunner {
    @Autowired
    private final ContainerRepository containerRepository; // Pour insérer des objets de type Container
    @Autowired
    private final SensorRepository sensorRepository; // Pour insérer des objets de type Sensor

    // Injection des dépendances via le constructeur
    public DataInitializationProduktService(
                                     ContainerRepository containerRepository,
                                     SensorRepository sensorRepository) {

        this.containerRepository = containerRepository;
        this.sensorRepository = sensorRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Random random = new Random();

        // Vérifiez si des Containers existent déjà
        if (containerRepository.count() > 0) {
            System.out.println("Containers déjà initialisés.");
        } else {
            // Insérer 30 objets Container
            for (int i = 1; i <= 30; i++) {
                double volume = 50.0 + random.nextDouble() * 150.0; // Volume entre 50 et 200
                Container container = new Container("Container" + i, 100.0 + random.nextDouble() * 50.0, volume);
                containerRepository.save(container);
                System.out.println("Container inséré : " + container);
            }
        }

        // Vérifiez si un Sensor existe déjà
        if (sensorRepository.count() > 0) {
            System.out.println("Sensors déjà initialisés.");
        } else {
            Sensor sensor = new Sensor("Sensor1", 200.0 + random.nextDouble() * 100.0);
            sensorRepository.save(sensor);
            System.out.println("Sensor inséré : " + sensor);
        }
    }
}
