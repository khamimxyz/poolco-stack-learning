package xyz.khamim.kafkaexample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class KafkaProducer {

    private static final String TOPIC_NAME = "test-topic";

    private static final Long start = System.currentTimeMillis();

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Scheduled(fixedDelay = 5000)
    public void sendMessage() {

        int elapsedTime = (int) ((System.currentTimeMillis() - start) / 1000);

        System.out.println("Sending message");
        kafkaTemplate.send(TOPIC_NAME, "Hello Kafka, elapsed time "+elapsedTime);
    }
}
