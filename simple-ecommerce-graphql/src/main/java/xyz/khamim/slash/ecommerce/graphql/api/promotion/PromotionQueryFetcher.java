package xyz.khamim.slash.ecommerce.graphql.api.promotion;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;
import xyz.khamim.slash.ecommerce.graphql.model.Promotion;
import xyz.khamim.slash.ecommerce.graphql.service.PromotionService;

import java.util.List;

@DgsComponent
@RequiredArgsConstructor
public class PromotionQueryFetcher {

    private final PromotionService service;

    @DgsQuery
    public Mono<Promotion> getPromotion(String id) {

        return service.getPromotion(id);
    }

    @DgsQuery
    public Mono<List<Promotion>> getPromotionsByProductId(String productId) {

        return service.getPromotionsByProductId(productId);
    }

    @DgsQuery
    public Mono<List<Promotion>> getAllPromotions() {

        return service.getAllPromotions();
    }
}
