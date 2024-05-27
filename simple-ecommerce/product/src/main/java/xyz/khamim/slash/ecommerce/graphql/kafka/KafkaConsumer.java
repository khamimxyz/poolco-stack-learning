package xyz.khamim.slash.ecommerce.graphql.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import xyz.khamim.slash.ecommerce.graphql.kafka.util.TopicConstant;

@Component
@Slf4j
public class KafkaConsumer {

  @KafkaListener(topics = TopicConstant.PRODUCT_FEED_TOPIC_NAME, groupId = "feed")
  public void receiveProductFeed(String message) {
    log.info("New Product Feed : {}", message);
  }

  @KafkaListener(topics = TopicConstant.RECOMMENDATION_TOPIC_NAME, groupId = "feed")
  public void receiveRecommendationFeed(String message) {
    log.info("New Recommendation Feed : {}", message);
  }
}
