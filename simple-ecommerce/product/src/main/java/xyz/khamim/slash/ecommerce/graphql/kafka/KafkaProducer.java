package xyz.khamim.slash.ecommerce.graphql.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import xyz.khamim.slash.ecommerce.graphql.kafka.util.JsonUtil;
import xyz.khamim.slash.ecommerce.graphql.kafka.util.TopicConstant;
import xyz.khamim.slash.ecommerce.graphql.model.Product;

@Component
@Slf4j
public class KafkaProducer {

  @Autowired
  private KafkaTemplate<String, String> kafkaTemplate;

  public void sendProductFeed(Product product) {

    try {
      String json = JsonUtil.convertToJson(product);
      kafkaTemplate.send(TopicConstant.PRODUCT_FEED_TOPIC_NAME, json);
    } catch (JsonProcessingException e) {
      log.error("Could not send product feed", e);
    }
  }

  public void sendRecommendationFeed(Product product) {

    try {
      String json = JsonUtil.convertToJson(product);
      kafkaTemplate.send(TopicConstant.RECOMMENDATION_TOPIC_NAME, json);
    } catch (JsonProcessingException e) {
      log.error("Could not send recommendation feed", e);
    }
  }
}