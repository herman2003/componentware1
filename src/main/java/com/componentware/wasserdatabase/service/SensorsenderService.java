package com.componentware.wasserdatabase.service;

import com.componentware.wasserdatabase.Mqtt.MqttService;
import com.componentware.wasserdatabase.entity.Nachricht;
import com.componentware.wasserdatabase.entity.SensorSender;
import com.componentware.wasserdatabase.repository.NachrichtRepository;
import com.componentware.wasserdatabase.repository.SensorSenderRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Random;

@AllArgsConstructor
@Service
public class SensorsenderService {
    @Autowired
    private SensorSenderRepository sensorSenderRepository;

    @Autowired
    private NachrichtRepository nachrichtRepository;
    @Autowired
    private MqttService mqttService;
    @Transactional
        public void generateAndSendData() {
            Random random = new Random();
            float minimalStand=20;
            String location="Location1";
            // insert new sensorsender
            SensorSender sensorSender = new SensorSender(location,minimalStand);
            sensorSenderRepository.save(sensorSender);

            new Thread(() -> {
                while (true) {
                    float wasserstand=random.nextFloat(0,200);
                    try {
                        //insert new message
                        String status="";
                        if(wasserstand>=minimalStand){
                       status="OK";
                        }else{
                            System.out.println("WARNUNG: Wasserstand ist kritisch niedrig: " + wasserstand + " cm");
                            status="wasserstand ausfuellen";
                        }
String info="Sensor ID: " + sensorSender.getId() + " - Location: "+sensorSender.getLocation()+ " - Wasserstand: " + wasserstand+ " - MinimalStand: " + sensorSender.getMinimalStand()+ " - Status: " + status;
                        Nachricht nachricht = new Nachricht(LocalDateTime.now(), status,info,sensorSender,wasserstand);

                        // Sauvegarder le message dans la base de données
                        nachrichtRepository.save(nachricht);

                        // Créer et envoyer le message MQTT
                        mqttService.publish(nachricht.getInfo());

                        // Attendre 1 seconde avant de générer la prochaine mesure
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        System.err.println("Ungültiges Payload-Format: " +minimalStand );
                        e.printStackTrace();
                    }
                }
            }).start();
        }
}
