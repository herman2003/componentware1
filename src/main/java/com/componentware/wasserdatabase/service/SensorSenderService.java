package com.componentware.wasserdatabase.service;

import com.componentware.wasserdatabase.Mqtt.MqttService;
import com.componentware.wasserdatabase.entity.*;
import com.componentware.wasserdatabase.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@AllArgsConstructor
@Service
public class SensorSenderService {

    @Autowired
    private SenderRepository senderRepository;
    @Autowired
    private NachrichtRepository nachrichtRepository;
    @Autowired
    private MqttService mqttService;
    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void generateAndSendData() {
        Random random = new Random();



        // Récupérer un utilisateur existant
        User defaultUser = userRepository.findByEmail("admin@example.com");
        if (defaultUser == null) {
            throw new RuntimeException("Aucun utilisateur trouvé. Veuillez initialiser un utilisateur.");
        }

        // Récupérer tous les senders de l'utilisateur
        List<Sender> senders = senderRepository.findSendersByUser(defaultUser);

        // Pour chaque Sender, démarrer un thread pour générer et envoyer des données
        for (Sender sender : senders) {
            new Thread(() -> {
                while (true) {
                    float wasserstand = random.nextFloat(0, 200);
                    try {
                        String status = wasserstand >= sender.getMinimalStand() ? "OK" : "Wasserstand auffüllen";
                        String info = "Sender ID: " + sender.getId() + " - Location: " + sender.getLocation() +
                                " - Wasserstand: " + wasserstand + " - MinimalStand: " + sender.getMinimalStand() +
                                " - Status: " + status;

                        Nachricht nachricht = new Nachricht(LocalDateTime.now(), status, info, sender, wasserstand);

                        // Sauvegarder le message dans la base de données
                        nachrichtRepository.save(nachricht);

                        // Envoyer via MQTT
                        mqttService.publish(nachricht.getInfo());

                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
}
