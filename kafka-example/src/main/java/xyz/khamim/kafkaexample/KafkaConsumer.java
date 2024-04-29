package xyz.khamim.kafkaexample;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {

    private static final String TOPIC_NAME = "test-topic";

    @KafkaListener(topics = TOPIC_NAME, groupId = "test-group")
    public void receiveMessage(String message) {
        System.out.println("Received message: " + message);
    }
}
