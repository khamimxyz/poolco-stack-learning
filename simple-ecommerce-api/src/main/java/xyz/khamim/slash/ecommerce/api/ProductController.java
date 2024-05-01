package xyz.khamim.slash.ecommerce.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import xyz.khamim.slash.ecommerce.payload.ProductReq;
import xyz.khamim.slash.ecommerce.service.ProductService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService service;

    @PostMapping("/create")
    public Mono<ResponseEntity<?>> createProduct(@RequestBody ProductReq productReq) {

        return service.createProduct(productReq).map(ResponseEntity::ok);
    }

    @GetMapping("/get/{id}")
    public Mono<ResponseEntity<?>> getProduct(@PathVariable("id") String id) {

        return service.getProduct(id).map(ResponseEntity::ok);
    }

    @GetMapping("/all")
    public Mono<ResponseEntity<?>> getAllProducts() {

        return service.getAllProducts().map(ResponseEntity::ok);
    }

    @PostMapping("/update/{id}")
    public Mono<ResponseEntity<?>> updateProduct(
            @PathVariable("id") String id,
            @RequestBody ProductReq productReq) {

        return service.updateProduct(id, productReq).map(ResponseEntity::ok);
    }
}
