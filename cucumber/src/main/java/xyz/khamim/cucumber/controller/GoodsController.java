package xyz.khamim.cucumber.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.khamim.cucumber.model.Goods;
import xyz.khamim.cucumber.repository.GoodsRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/goods")
public class GoodsController {

    @Autowired
    private GoodsRepository goodsRepository;

    @GetMapping
    public List<Goods> getAllGoods() {
        return goodsRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Goods> getGoodsById(@PathVariable Long id) {
        Optional<Goods> goods = goodsRepository.findById(id);
        return goods.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Goods> createGoods(@RequestBody Goods goods) {
        Goods createdGoods = goodsRepository.save(goods);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdGoods);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Goods> updateGoods(@PathVariable Long id, @RequestBody Goods goods) {
        if (goodsRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        goods.setId(id);
        Goods updatedGoods = goodsRepository.save(goods);
        return ResponseEntity.ok(updatedGoods);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGoods(@PathVariable Long id) {
        if (goodsRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        goodsRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

