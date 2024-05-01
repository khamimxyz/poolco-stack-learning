package xyz.khamim.slash.ecommerce.graphql.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import xyz.khamim.slash.ecommerce.graphql.model.Promotion;
import xyz.khamim.slash.ecommerce.graphql.payload.PromotionReq;
import xyz.khamim.slash.ecommerce.graphql.repository.PromotionRepository;
import xyz.khamim.slash.ecommerce.graphql.service.helper.PayloadToModelConverter;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PromotionService {

    private final PromotionRepository repository;
    
    private final PayloadToModelConverter converter;

    public Mono<Promotion> createPromotion(PromotionReq promotionReq) {

        Promotion Promotion = converter.fromPromotionReq(promotionReq);

        return repository.create(Promotion);
    }

    public Mono<Promotion> updatePromotion(String id, PromotionReq promotionReq) {

        Promotion Promotion = converter.fromPromotionReq(promotionReq);
        Promotion.setId(id);

        return repository.update(Promotion);
    }

    public void deletePromotion(String id) {

        repository.delete(id);
    }

    public Mono<Promotion> getPromotion(String id) {

        return repository.get(id);
    }

    public Mono<List<Promotion>> getPromotionsByProductId(String productId) {

        return repository.getPromotionsByProductId(productId);
    }

    public Mono<List<Promotion>> getAllPromotions() {

        return repository.getAll();
    }
}
