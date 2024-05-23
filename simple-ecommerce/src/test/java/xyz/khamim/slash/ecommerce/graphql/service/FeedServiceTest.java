package xyz.khamim.slash.ecommerce.graphql.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import xyz.khamim.slash.ecommerce.graphql.kafka.KafkaProducer;
import xyz.khamim.slash.ecommerce.graphql.mock.TestDataFixture;
import xyz.khamim.slash.ecommerce.graphql.model.Product;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class FeedServiceTest {

  @Mock
  private KafkaProducer kafkaProducer;

  @InjectMocks
  private FeedService service;

  @BeforeEach
  void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void sendProductFeed_ShouldCallKafkaProducer() {

    Product product = TestDataFixture.getSampleOfProduct();
    service.sendProductFeed(product);
    verify(kafkaProducer, times(1)).sendProductFeed(product);
  }

  @Test
  void sendRecommendationFeed_ShouldCallKafkaProducer() {
    Product product = TestDataFixture.getSampleOfProduct();
    service.sendRecommendationFeed(product);
    verify(kafkaProducer, times(1)).sendRecommendationFeed(product);
  }
}