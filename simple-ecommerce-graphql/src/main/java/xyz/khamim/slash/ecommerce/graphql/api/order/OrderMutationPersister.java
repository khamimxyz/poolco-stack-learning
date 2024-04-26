package xyz.khamim.slash.ecommerce.graphql.api.order;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;
import xyz.khamim.slash.ecommerce.graphql.model.Order;
import xyz.khamim.slash.ecommerce.graphql.payload.OrderReq;
import xyz.khamim.slash.ecommerce.graphql.service.OrderService;

@DgsComponent
@RequiredArgsConstructor
public class OrderMutationPersister {

    private final OrderService service;

    @DgsMutation
    public Mono<Order> addOrder(OrderReq orderReq) {

        return service.createOrder(orderReq);
    }
}
