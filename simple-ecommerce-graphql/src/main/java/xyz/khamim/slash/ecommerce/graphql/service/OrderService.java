package xyz.khamim.slash.ecommerce.graphql.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import xyz.khamim.slash.ecommerce.graphql.dto.OrderDetailDto;
import xyz.khamim.slash.ecommerce.graphql.dto.OrderProductDetailDto;
import xyz.khamim.slash.ecommerce.graphql.model.*;
import xyz.khamim.slash.ecommerce.graphql.payload.OrderReq;
import xyz.khamim.slash.ecommerce.graphql.repository.OrderProductRepository;
import xyz.khamim.slash.ecommerce.graphql.repository.OrderRepository;
import xyz.khamim.slash.ecommerce.graphql.repository.ProductRepository;
import xyz.khamim.slash.ecommerce.graphql.repository.PromotionRepository;
import xyz.khamim.slash.ecommerce.graphql.service.helper.PayloadToModelConverter;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    private final OrderProductRepository orderProductRepository;

    private final ProductRepository productRepository;

    private final PromotionRepository promotionRepository;

    private final PayloadToModelConverter converter;

    public Mono<Order> createOrder(OrderReq orderReq) {

        List<OrderProduct> orderProducts = converter.fromOrderProductReq(orderReq.getOrderProducts());
        return orderProductRepository.createOrderProducts(orderProducts)
                .flatMap(results -> {
                    final List<String> orderProductIds = results
                            .stream()
                            .map(DynamoItem::getId)
                            .toList();
                    final Order order = Order.builder()
                            .orderProductIds(orderProductIds)
                            .build();

                    return orderRepository.create(order);
                });
    }

    public Mono<OrderDetailDto> getOrderDetail(String id) {

        return orderRepository.get(id).flatMap(order -> {
            Flux<String> flux = Flux.fromIterable(order.getOrderProductIds());
            Flux<OrderProductDetailDto> orderProductsFlux = flux
                .flatMap(orderProductRepository::get)
                .flatMap(orderProduct -> {
                    Mono<Product> productMono = productRepository.get(orderProduct.getProductId());
                    Mono<Promotion> promotionMono = promotionRepository.get(orderProduct.getPromotionId());

                    return Mono.zip(productMono, promotionMono, (product, promotion) -> OrderProductDetailDto.builder()
                            .productName(product.getName())
                            .price(product.getPrice())
                            .qty(orderProduct.getQty())
                            .promotionName(promotion.getName())
                            .discount(promotion.getDiscount())
                            .build());
                });

            return orderProductsFlux.collectList().flatMap(list -> {
                OrderDetailDto dto = OrderDetailDto.builder()
                        .id(id)
                        .orderProducts(list)
                        .build();
                return Mono.just(dto);
            });
        });
    }
}
