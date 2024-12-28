package com.componentware.wasserdatabase.Mqtt;

import jakarta.annotation.PostConstruct;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Service
public class MqttService {

    @Value("${mqtt.broker}")
    private String brokerUrl;

    @Value("${mqtt.topic}")
    private String topic;

    private MqttClient client;
    private Consumer<String> messageHandler;

    @PostConstruct
    public void initialize() {
        try {
            client = new MqttClient(brokerUrl, MqttClient.generateClientId());
            client.connect();

            // Abonnement au topic
            client.subscribe(topic);

            // Définir un callback pour traiter les messages reçus
            client.setCallback(new org.eclipse.paho.client.mqttv3.MqttCallback() {
                @Override
                public void connectionLost(Throwable cause) {
                    System.err.println("La connexion au broker MQTT a été perdue.");
                    cause.printStackTrace();
                }

                @Override
                public void messageArrived(String receivedTopic, MqttMessage message) {
                    // Récupérer le payload et appeler le handler pour traiter le message
                    String payload = new String(message.getPayload());
                    System.out.println("Message reçu sur le topic " + receivedTopic + ": " + payload);

                    if (messageHandler != null) {
                        messageHandler.accept(payload);  // Appel du handler pour le message
                    }
                }

                @Override
                public void deliveryComplete(org.eclipse.paho.client.mqttv3.IMqttDeliveryToken token) {
                    // Notifier que le message a été livré
                    try {
                        System.out.println("Message livré : " + token.getMessage());
                    } catch (MqttException e) {
                        throw new RuntimeException(e);
                    }
                }
            });

            System.out.println("Connecté au broker MQTT et abonné au topic : " + topic);
        } catch (MqttException e) {
            System.err.println("Erreur lors de l'initialisation de MQTT : " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Méthode pour publier un message MQTT
    public void publish(String messageContent) {
        try {
            if (client == null || !client.isConnected()) {
                initialize();  // S'assurer que la connexion est établie
            }

            MqttMessage message = new MqttMessage(messageContent.getBytes());
            message.setQos(1);  // Niveau de qualité de service
            client.publish(topic, message);

            System.out.println("Message publié : " + messageContent);
        } catch (MqttException e) {
            System.err.println("Erreur lors de la publication du message : " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Setter pour définir le handler des messages MQTT
    public void setMessageHandler(Consumer<String> messageHandler) {
        this.messageHandler = messageHandler;
    }
}
