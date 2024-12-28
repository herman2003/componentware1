package com.componentware.wasserdatabase.service;

import com.componentware.wasserdatabase.Mqtt.MqttService;
import com.componentware.wasserdatabase.entity.Auftrag;
import com.componentware.wasserdatabase.entity.AuftragStatus;
import com.componentware.wasserdatabase.entity.AuftragType;
import com.componentware.wasserdatabase.repository.AuftragRepository;
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
                    case "Sensor ID":
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


            if ("OK".equals(status)) {
                System.out.println("das Status ist OK, man braucht keine Auftrag zu erstellen");
                return;
            }


            Auftrag newAuftrag = new Auftrag();
            newAuftrag.setInfo("Location: " + location + ", Wasserstand: " + wasserstand);
            newAuftrag.setStatus(AuftragStatus.INPROCESS.name());
            newAuftrag.setType(AuftragType.TRANSPORT.name());


            auftragRepository.save(newAuftrag);
            transportService.auftragsberarbeiten(newAuftrag);
            System.out.println("Neue Auftrag erstellt mit dem Status 'in process' : " + newAuftrag);

        } catch (Exception e) {
            System.err.println("Fehler bei der Bearbeitung in mqtt: " + e.getMessage());
            e.printStackTrace();
        }
    }


}
