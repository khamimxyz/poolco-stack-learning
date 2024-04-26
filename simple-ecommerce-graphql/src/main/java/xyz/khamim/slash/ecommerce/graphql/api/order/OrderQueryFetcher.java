package xyz.khamim.slash.ecommerce.graphql.api.order;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;
import xyz.khamim.slash.ecommerce.graphql.dto.OrderDetailDto;
import xyz.khamim.slash.ecommerce.graphql.service.OrderService;

@DgsComponent
@RequiredArgsConstructor
public class OrderQueryFetcher {

    private final OrderService service;

    @DgsQuery
    public Mono<OrderDetailDto> getOrderDetail(String id) {

        return service.getOrderDetail(id);
    }
}
