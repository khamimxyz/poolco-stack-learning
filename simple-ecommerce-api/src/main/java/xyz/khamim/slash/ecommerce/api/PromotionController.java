package xyz.khamim.slash.ecommerce.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import xyz.khamim.slash.ecommerce.payload.PromotionReq;
import xyz.khamim.slash.ecommerce.service.PromotionService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/promotion")
public class PromotionController {

    private final PromotionService service;

    @PostMapping("/create")
    public Mono<ResponseEntity<?>> createPromotion(@RequestBody PromotionReq promotionReq) {

        return service.createPromotion(promotionReq).map(ResponseEntity::ok);
    }

    @GetMapping("/get/{id}")
    public Mono<ResponseEntity<?>> getPromotion(@PathVariable("id") String id) {

        return service.getPromotion(id).map(ResponseEntity::ok);
    }

    @GetMapping("/all")
    public Mono<ResponseEntity<?>> allPromotions() {

        return service.getAllPromotions().map(ResponseEntity::ok);
    }

    @PostMapping("/update/{id}")
    public Mono<ResponseEntity<?>> updatePromotion(
            @PathVariable("id") String id,
            @RequestBody PromotionReq promotionReq) {

        return service.updatePromotion(id, promotionReq).map(ResponseEntity::ok);
    }
}
