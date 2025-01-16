package com.componentware.wasserdatabase.Mqtt;

import jakarta.annotation.PostConstruct;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Consumer;

@Service
public class MqttService {

    @Value("${mqtt.broker}")
    private String brokerUrl;

    @Value("${mqtt.topic}")
    private String topic;

    private MqttClient client;
    private Consumer<String> messageHandler;
    private BlockingQueue<String> messageQueue = new LinkedBlockingQueue<>();

    @PostConstruct
    public void initialize() {
        connectMqttClient();
    }

    private void connectMqttClient() {
        try {
            client = new MqttClient(brokerUrl, MqttClient.generateClientId());
            client.connect();

            // Abonnement au topic
            client.subscribe(topic);

            // Définir un callback pour traiter les messages reçus
            client.setCallback(new org.eclipse.paho.client.mqttv3.MqttCallback() {
                @Override
                public void connectionLost(Throwable cause) {
                    System.err.println("Connexion au broker MQTT perdue");
                    cause.printStackTrace();
                    reconnect();
                }

                @Override
                public void messageArrived(String receivedTopic, MqttMessage message) {
                    // Traiter le message reçu
                    String payload = new String(message.getPayload());
                    System.out.println("Message reçu, Topic: " + receivedTopic + ", Message: " + payload);
                    if (messageHandler != null) {
                        messageHandler.accept(payload);  // Appeler le handler pour traiter le message
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

            System.out.println("Connexion réussie au broker MQTT et abonnement au topic : " + topic);
        } catch (MqttException e) {
            System.err.println("Erreur lors de l'initialisation de la connexion MQTT : " + e.getMessage());
            e.printStackTrace();
            reconnect(); // Essayer de se reconnecter en cas d'erreur
        }
    }

    private void reconnect() {
        System.out.println("Tentative de reconnexion au broker MQTT...");
        try {
            while (!client.isConnected()) {
                Thread.sleep(5000);  // Attendre 5 secondes avant de tenter une reconnexion
                connectMqttClient();  // Essayer de se reconnecter
            }
        } catch (InterruptedException e) {
            System.err.println("Erreur lors de la reconnexion : " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Méthode pour publier un message MQTT
    public void publish(String messageContent) {
        try {
            if (client == null || !client.isConnected()) {
                System.out.println("Client non connecté, tentative de reconnexion...");
                initialize();  // Réinitialiser la connexion
            }

            MqttMessage message = new MqttMessage(messageContent.getBytes());
            message.setQos(1);  // Niveau de qualité de service
            client.publish(topic, message);

            System.out.println("Message publié : " + messageContent);
        } catch (MqttException e) {
            if (e.getReasonCode() == MqttException.REASON_CODE_MAX_INFLIGHT) {
                System.err.println("Erreur : Trop de publications en cours. Réessayer...");
                retryPublish(messageContent); // Réessayer de publier
            } else {
                System.err.println("Erreur lors de la publication du message : " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
    private void retryPublish(String messageContent) {
        try {
            Thread.sleep(120000);  // Attendre 500 ms avant de réessayer
            publish(messageContent);  // Réessayer de publier le message
        } catch (InterruptedException e) {
            System.err.println("Le délai de réessai a été interrompu.");
            Thread.currentThread().interrupt(); // Réinterrompre le thread
        }
    }
    // Setter pour définir le handler des messages MQTT
    public void setMessageHandler(Consumer<String> messageHandler) {
        this.messageHandler = messageHandler;
    }
}
