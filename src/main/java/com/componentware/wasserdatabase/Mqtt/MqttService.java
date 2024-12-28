package com.componentware.wasserdatabase.Mqtt;


import jakarta.annotation.PostConstruct;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import static javax.management.remote.JMXConnectorFactory.connect;

@Service
public class MqttService {

	@Value("${mqtt.broker}")
	private String brokerUrl;

	@Value("${mqtt.topic}")
	private String topic;
    private MqttClient client;

    @PostConstruct
    public void initialize() {
        try {
            client = new MqttClient(brokerUrl, MqttClient.generateClientId());
            client.connect();

            // S'abonner au sujet
            client.subscribe(topic, (receivedTopic, message) -> {
                String payload = new String(message.getPayload());
                System.out.println("Empfangene Nachricht: " + payload);

                System.out.println("Nachricht gespeichert in der Datenbank: " + payload);
            });

            System.out.println("Mit MQTT-Broker verbunden und abonniert: " + topic);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void publish(String messageContent) {
        try {
            if (client == null || !client.isConnected()) {
                initialize();
            }

            MqttMessage message = new MqttMessage(messageContent.getBytes());
            message.setQos(1); // Niveau de qualité de service
            client.publish(topic, message);
            System.out.println("Nachricht veröffentlicht: " + messageContent);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

}