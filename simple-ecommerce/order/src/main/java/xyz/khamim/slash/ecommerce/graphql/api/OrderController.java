package xyz.khamim.slash.ecommerce.graphql.api;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;
import xyz.khamim.slash.ecommerce.graphql.model.Order;
import xyz.khamim.slash.ecommerce.graphql.input.OrderReq;
import xyz.khamim.slash.ecommerce.graphql.security.SecureMethod;
import xyz.khamim.slash.ecommerce.graphql.service.OrderService;

@DgsComponent
@RequiredArgsConstructor
public class OrderController {

    private final OrderService service;

    @DgsQuery
    public Mono<Order> getOrder(@InputArgument String id) {

        return service.getOrder(id);
    }

    @DgsMutation
    @SecureMethod(module = "order")
    public Mono<Order> checkout(@InputArgument OrderReq orderReq) {

        return service.checkout(orderReq);
    }
}
