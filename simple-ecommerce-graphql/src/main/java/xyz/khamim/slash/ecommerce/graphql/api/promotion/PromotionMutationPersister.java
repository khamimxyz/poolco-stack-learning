package xyz.khamim.slash.ecommerce.graphql.api.promotion;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;
import xyz.khamim.slash.ecommerce.graphql.model.Promotion;
import xyz.khamim.slash.ecommerce.graphql.payload.PromotionReq;
import xyz.khamim.slash.ecommerce.graphql.service.PromotionService;

@DgsComponent
@RequiredArgsConstructor
public class PromotionMutationPersister {

    private final PromotionService service;

    @DgsMutation
    public Mono<Promotion> addPromotion(PromotionReq promotionReq) {

        return service.createPromotion(promotionReq);
    }

    @DgsMutation
    public Mono<Promotion> updatePromotion(String id, PromotionReq promotionReq) {

        return service.updatePromotion(id, promotionReq);
    }

    @DgsMutation
    public Mono<Boolean> deletePromotion(String id) {

        service.deletePromotion(id);

        return Mono.just(true);
    }
}
