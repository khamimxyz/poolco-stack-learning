package xyz.khamim.slash.ecommerce.graphql.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.khamim.slash.ecommerce.graphql.kafka.KafkaProducer;
import xyz.khamim.slash.ecommerce.graphql.model.Product;

@Service
@RequiredArgsConstructor
public class FeedService {

  private final KafkaProducer kafkaProducer;

  public void sendProductFeed(Product product) {

    kafkaProducer.sendProductFeed(product);
  }

  public void sendRecommendationFeed(Product product) {

    kafkaProducer.sendRecommendationFeed(product);
  }
}
