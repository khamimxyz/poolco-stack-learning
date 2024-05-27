package xyz.khamim.slash.ecommerce.graphql.api;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import xyz.khamim.slash.ecommerce.graphql.boot.DynamoDbTableInitializer;
import xyz.khamim.slash.ecommerce.graphql.util.DynamoDbTable;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public abstract class BaseDgsIntegrationTest {
  @Autowired
  protected WebTestClient webTestClient;
  @Autowired
  protected AmazonDynamoDB amazonDynamoDB;
  @Autowired
  protected DynamoDbTableInitializer tableInitializer;
  @Autowired
  protected DynamoDbTable table;

  @BeforeEach
  protected void setUp() {
    log.info("setup dynamo db table");
    tableInitializer.initTable();
  }

  @AfterEach
  protected void clean() {
    log.info("Clean dynamo db table");
    amazonDynamoDB.deleteTable(table.getTableName());
  }
}
