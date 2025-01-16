package com.componentware.wasserdatabase.service;

import com.componentware.wasserdatabase.Mqtt.MqttService;
import com.componentware.wasserdatabase.entity.*;
import com.componentware.wasserdatabase.repository.AuftragRepository;
import com.componentware.wasserdatabase.repository.SenderRepository;
import com.componentware.wasserdatabase.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class LogistikService {

    @Autowired
    private AuftragRepository auftragRepository;

    @Autowired
    private MqttService mqttService;

    @Autowired
    private TransportService transportService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SenderRepository senderRepository;

    @PostConstruct
    public void setupMqttListener() {
        mqttService.setMessageHandler(this::processMqttMessage);
        System.out.println("MQTT listener setup completed.");
    }

    private void processMqttMessage(String payload) {
        try {
            // Extraction des informations du message
            String[] parts = payload.split(" - ");
            Long sensorId = null;
            String location = null;
            double wasserstand = 0.0;
            double minimalStand = 0.0;
            String status = null;

            for (String part : parts) {
                String[] keyValue = part.split(": ");
                String key = keyValue[0].trim();
                String value = keyValue[1].trim();

                switch (key) {
                    case "Sender ID":
                        sensorId = Long.parseLong(value);
                        break;
                    case "Location":
                        location = value;
                        break;
                    case "Wasserstand":
                        wasserstand = Double.parseDouble(value);
                        break;
                    case "MinimalStand":
                        minimalStand = Double.parseDouble(value);
                        break;
                    case "Status":
                        status = value;
                        break;
                    default:
                        System.out.println("Clé inconnue dans le payload: " + key);
                }
            }

            if (sensorId == null) {
                System.err.println("Sensor id fehlt in payload: " + payload);
                return;
            }

            // Trouver le Sender à partir de l'ID du capteur
            Sender sender = senderRepository.findById(Math.toIntExact(sensorId)).orElse(null);
            if (sender == null) {
                System.err.println("Sender non trouvé avec l'ID: " + sensorId);
                return;
            }

            // Trouver l'utilisateur lié au Sender (en supposant que l'utilisateur est lié au Sender)
            User user = sender.getUser();

            if ("OK".equals(status)) {
                System.out.println("Le status est OK, aucun Auftrag n'est nécessaire.");
                return;
            }

            // Créer un nouvel Auftrag et l'associer à l'utilisateur et au Sender
            Auftrag newAuftrag = new Auftrag();
            newAuftrag.setInfo("Location: " + location + ", Wasserstand: " + wasserstand);
            newAuftrag.setStatus(AuftragStatus.INPROCESS.name());
            newAuftrag.setType(AuftragType.TRANSPORT.name());
            newAuftrag.setUser(user); // Associer l'utilisateur
            newAuftrag.setSender(sender); // Associer le Sender

            auftragRepository.save(newAuftrag);
            transportService.auftragsberarbeiten(newAuftrag);
            System.out.println("Nouvel Auftrag créé avec le statut 'in process' : " + newAuftrag);

        } catch (Exception e) {
            System.err.println("Erreur lors du traitement du message MQTT : " + e.getMessage());
            e.printStackTrace();
        }
    }
}
