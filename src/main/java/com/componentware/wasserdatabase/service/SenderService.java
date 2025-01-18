package com.componentware.wasserdatabase.service;

import com.componentware.wasserdatabase.Mqtt.MqttService;
import com.componentware.wasserdatabase.entity.*;
import com.componentware.wasserdatabase.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@AllArgsConstructor
@Service
public class SenderService {

    @Autowired
    private SenderRepository senderRepository;
    @Autowired
    private NachrichtRepository nachrichtRepository;
    @Autowired
    private ProduktRepository produktRepository;
    @Autowired
    private MqttService mqttService;
    @Autowired
    private UserRepository userRepository;
    private final List<Long> activeSenders = new ArrayList<>();
    @Transactional
    public void generateAndSendData() {
        Random random = new Random();

        // Récupérer tous les Senders existants
        List<Sender> senders = senderRepository.findAll();

        // Créer des maps pour stocker le dernier wasserstand et le temps d'atteinte de 10% pour chaque sender
        Map<Long, Float> lastWasserstandMap = new HashMap<>();
        Map<Long, Long> resetTimeMap = new HashMap<>();

        // Pour chaque Sender, démarrer un thread uniquement s'il n'est pas déjà actif
        for (Sender sender : senders) {
            if (!activeSenders.contains(sender.getId())) {
                activeSenders.add(sender.getId()); // Marquer comme actif
                new Thread(() -> {
                    while (true) {
                        // Récupérer le dernier wasserstand ou initialiser à 100% si c'est le premier
                        float lastWasserstand = lastWasserstandMap.getOrDefault(sender.getId(), 100f);

                        // Récupérer l'heure à laquelle le niveau d'eau a atteint 10%
                        long lastResetTime = resetTimeMap.getOrDefault(sender.getId(), System.currentTimeMillis());

                        // Si 3 minutes se sont écoulées depuis la dernière réinitialisation, réinitialiser le niveau
                        if (System.currentTimeMillis() - lastResetTime >= 3 * 60 * 1000) {
                            lastWasserstand = 100f; // Réinitialiser à 100%
                            resetTimeMap.put(sender.getId(), System.currentTimeMillis()); // Mettre à jour le temps de réinitialisation
                        }

                        // Diminuer le niveau d'eau de 10% à chaque itération
                        float wasserstand = lastWasserstand * 0.9f; // Réduction de 10%

                        // Si le niveau d'eau atteint ou descend en dessous de 10%, réinitialiser à 100%
                        if (wasserstand <= 20f) {
                            wasserstand = 100f; // Réinitialiser à 100%
                            resetTimeMap.put(sender.getId(), System.currentTimeMillis()); // Mettre à jour le temps de réinitialisation
                        }

                        // Mettre à jour la carte avec le dernier wasserstand pour le sender
                        lastWasserstandMap.put(sender.getId(), wasserstand);

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

                            // Attendre 1 seconde avant de continuer
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        }
    }

    public Sender addSenderToUser(Long userId,Long sensorId,String location,Long  containerId,float minimalStand) {
        // Vérifier si l'utilisateur existe
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));
        Container container = (Container) produktRepository.findById(containerId).orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));
        Sensor sensor = (Sensor) produktRepository.findById(sensorId).orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));
       Sender sender=new Sender(location,minimalStand,sensor,user,container);

        // Sauvegarder le sender
        Sender savedSender = senderRepository.save(sender);

        // Relancer generateAndSendData pour prendre en compte le nouveau sender
        generateAndSendData();

        return savedSender;
    }

    public List<Sender> findSendersByUser(User user) {
        return senderRepository.findSendersByUser(user); // Appel au repository pour récupérer les senders
    }
    public void deleteSender(Long senderId) {
        if (senderRepository.existsById(Math.toIntExact(senderId))) {
            senderRepository.deleteById(Math.toIntExact(senderId));
        } else {
            throw new RuntimeException("Sender non trouvé avec l'id: " + senderId);
        }
    }
}
