package xyz.khamim.slash.ecommerce.graphql.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import xyz.khamim.slash.ecommerce.graphql.model.Order;
import xyz.khamim.slash.ecommerce.graphql.model.OrderData;
import xyz.khamim.slash.ecommerce.graphql.model.OrderProduct;
import xyz.khamim.slash.ecommerce.graphql.input.OrderReq;
import xyz.khamim.slash.ecommerce.graphql.repository.OrderRepository;
import xyz.khamim.slash.ecommerce.graphql.service.helper.PayloadToModelConverter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    private final PayloadToModelConverter converter;

    public Mono<Order> checkout(OrderReq orderReq) {

        List<OrderProduct> orderProducts = converter.fromOrderProductReq(orderReq.getOrderProducts());

        Order order = Order.builder()
                .orderData(OrderData.builder().orderProducts(orderProducts).build())
                .createdDate(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()))
                .createdBy(orderReq.getCustomerName())
                .build();

        return Mono.fromCallable(() -> orderRepository.create(order));
    }

    public Mono<Order> getOrder(String id) {

        return Mono.fromCallable(() -> orderRepository.get(id));
    }
}
