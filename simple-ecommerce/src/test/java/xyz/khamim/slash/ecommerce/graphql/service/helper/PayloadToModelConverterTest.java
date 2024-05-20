package xyz.khamim.slash.ecommerce.graphql.service.helper;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import xyz.khamim.slash.ecommerce.graphql.input.OrderProductReq;
import xyz.khamim.slash.ecommerce.graphql.input.ProductReq;
import xyz.khamim.slash.ecommerce.graphql.input.ReviewReq;
import xyz.khamim.slash.ecommerce.graphql.model.Product;
import xyz.khamim.slash.ecommerce.graphql.model.Review;
import xyz.khamim.slash.ecommerce.graphql.model.order.OrderProduct;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PayloadToModelConverterTest {

    @InjectMocks
    private PayloadToModelConverter converter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void fromProductReq_ShouldConvertToProduct() {

        ProductReq productReq = new ProductReq();
        productReq.setName("Samsung Galaxy");
        productReq.setCategory("Handphone");
        productReq.setPrice(100);

        Product product = converter.fromProductReq(productReq, "20240516131500");

        assertEquals("Samsung Galaxy", product.getName());
        assertEquals("Handphone", product.getCategory());
        assertEquals(100, product.getPrice());
        assertEquals("20240516131500", product.getCreatedDate());
    }

    @Test
    void fromReviewReq_ShouldConvertToReview() {
        ReviewReq reviewReq = new ReviewReq();
        reviewReq.setProductId("2588f64c1f4a41ca8a81ab73b513c13b");
        reviewReq.setStar(5);
        reviewReq.setReviewerName("reviewer 001");

        Review review = converter.fromReviewReq(reviewReq);

        assertEquals("2588f64c1f4a41ca8a81ab73b513c13b", review.getProductId());
        assertEquals(5, review.getStar());
        assertEquals("reviewer 001", review.getReviewerName());
    }

    @Test
    void fromOrderProductReq_ShouldConvertToOrderProduct() {

        List<OrderProductReq> reqs = List.of(
                OrderProductReq.builder()
                        .productId("2588f64c1f4a41ca8a81ab73b513c13b")
                        .productName("Samsung Galaxy")
                        .price(100)
                        .qty(1)
                        .build(),
                OrderProductReq.builder()
                        .productId("2588f64c1f4a41ca8a81ab73b513c13b")
                        .productName("Samsung Galaxy")
                        .price(100)
                        .qty(1)
                        .build()
        );

        List<OrderProduct> expected = List.of(
                OrderProduct.builder()
                        .productId("2588f64c1f4a41ca8a81ab73b513c13b")
                        .productName("Samsung Galaxy")
                        .price(100)
                        .qty(1)
                        .build(),
                OrderProduct.builder()
                        .productId("2588f64c1f4a41ca8a81ab73b513c13b")
                        .productName("Samsung Galaxy")
                        .price(100)
                        .qty(1)
                        .build()
        );

        List<OrderProduct> orderProducts = converter.fromOrderProductReq(reqs);
        Assertions.assertThat(orderProducts).usingRecursiveComparison().isEqualTo(expected);
    }
}
