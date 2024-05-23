package xyz.khamim.slash.ecommerce.graphql.api;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import xyz.khamim.slash.ecommerce.graphql.input.AuthReq;
import xyz.khamim.slash.ecommerce.graphql.input.VerifyReq;
import xyz.khamim.slash.ecommerce.graphql.service.AuthService;
import xyz.khamim.slash.ecommerce.graphql.util.GraphQLReqBuilder;

import java.util.Map;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@SuppressWarnings("unchecked")
public class AuthControllerIntegrationTest {

  @Autowired
  private WebTestClient webTestClient;

  @MockBean
  private AuthService authService;

  @Test
  public void testAuthEndpoint() {

    when(authService.auth(any(AuthReq.class))).thenReturn("dummy-session");

    final String req = "{ \"query\": \""+new GraphQLReqBuilder()
      .mutation()
      .operation("auth", Map.of("authReq", AuthReq
        .builder()
          .email("myemail@mydomain.com")
          .group("customer")
        .build()))
      .fields("session", "message")
      .build()+"\"}";

    webTestClient.post().uri("/graphql")
      .contentType(MediaType.APPLICATION_JSON)
      .bodyValue(req)
      .exchange()
      .expectStatus().isOk()
      .expectBody(Map.class)
      .value(map -> {
        Map<String, Map<String, Object>> data = (Map<String, Map<String, Object>>) map.get("data");
        Assertions.assertThat(data).isNotNull();
        Map<String, Object> auth = data.get("auth");
        Assertions.assertThat(auth.size()).isEqualTo(2);
        Assertions.assertThat(auth.get("session")).isEqualTo("dummy-session");
        Assertions.assertThat(auth.get("message")).isEqualTo("Your verification code has been sent");
      });
  }

  @Test
  public void testVerifyEndpoint() {

    when(authService.verify(any(VerifyReq.class))).thenReturn("dummy-token");

    final String req = "{ \"query\": \""+new GraphQLReqBuilder()
      .mutation()
      .operation("verify", Map.of("verifyReq", VerifyReq
        .builder()
        .email("myemail@mydomain.com")
        .session("dummy-session")
        .code("12345")
        .build()))
      .fields("token")
      .build()+"\"}";

    webTestClient.post().uri("/graphql")
      .contentType(MediaType.APPLICATION_JSON)
      .bodyValue(req)
      .exchange()
      .expectStatus().isOk()
      .expectBody(Map.class)
      .value(map -> {
        Map<String, Map<String, Object>> data = (Map<String, Map<String, Object>>) map.get("data");
        Assertions.assertThat(data).isNotNull();
        Map<String, Object> auth = data.get("verify");
        Assertions.assertThat(auth.size()).isEqualTo(1);
        Assertions.assertThat(auth.get("token")).isEqualTo("dummy-token");
      });
  }
}
