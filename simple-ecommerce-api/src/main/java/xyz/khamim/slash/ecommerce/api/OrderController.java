package xyz.khamim.slash.ecommerce.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import xyz.khamim.slash.ecommerce.payload.OrderReq;
import xyz.khamim.slash.ecommerce.service.OrderService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order")
public class OrderController {

    private final OrderService service;
    
    @PostMapping("/create")
    public Mono<ResponseEntity<?>> createOrder(@RequestBody OrderReq orderReq) {

        return service.createOrder(orderReq).map(ResponseEntity::ok);
    }

    @GetMapping("/detail/{id}")
    public Mono<ResponseEntity<?>> getOrderDetail(@PathVariable("id") String id) {

        return service.getOrderDetail(id).map(ResponseEntity::ok);
    }
}
