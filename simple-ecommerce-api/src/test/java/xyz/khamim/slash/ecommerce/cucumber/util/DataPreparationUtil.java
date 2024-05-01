package xyz.khamim.slash.ecommerce.cucumber.util;

import org.junit.jupiter.api.Assertions;
import org.springframework.boot.test.web.client.TestRestTemplate;
import xyz.khamim.slash.ecommerce.cucumber.Constant;
import xyz.khamim.slash.ecommerce.cucumber.payload.IdResponse;
import xyz.khamim.slash.ecommerce.payload.ProductReq;
import xyz.khamim.slash.ecommerce.payload.PromotionReq;
import xyz.khamim.slash.ecommerce.payload.auth.AuthRequest;
import xyz.khamim.slash.ecommerce.payload.auth.AuthResponse;

public class DataPreparationUtil {

    public static String authenticate(TestRestTemplate restTemplate, String username, String password) {
        final AuthRequest payload = new AuthRequest(username, password);

        final AuthResponse authResponse = RestUtil.post(restTemplate, "/auth/authenticate",
                payload, AuthResponse.class);
        Assertions.assertNotNull(authResponse);

        return authResponse.getToken();
    }

    public static String createProduct(TestRestTemplate restTemplate, ProductReq product) {
        final AuthRequest payload = new AuthRequest(Constant.PRODUCT_MANAGER_USERNAME, Constant.PASSWORD);

        final AuthResponse authResponse = RestUtil.post(restTemplate, "/auth/authenticate",
                payload, AuthResponse.class);
        Assertions.assertNotNull(authResponse);
        String token = authResponse.getToken();

        final IdResponse response = RestUtil.post(restTemplate, "/product/create",
                product, IdResponse.class, token);
        Assertions.assertNotNull(response);

        return response.getId();
    }

    public static String createPromotion(TestRestTemplate restTemplate, PromotionReq promotion) {
        final AuthRequest payload = new AuthRequest(Constant.PROMOTION_MANAGER_USERNAME, Constant.PASSWORD);

        final AuthResponse authResponse = RestUtil.post(restTemplate, "/auth/authenticate",
                payload, AuthResponse.class);
        Assertions.assertNotNull(authResponse);
        String token = authResponse.getToken();
        final IdResponse response = RestUtil.post(restTemplate, "/promotion/create",
                promotion, IdResponse.class, token);
        Assertions.assertNotNull(response);

        return response.getId();
    }
}
