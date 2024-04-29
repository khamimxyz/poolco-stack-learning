package xyz.khamim.cucumber.repository;

import org.springframework.stereotype.Repository;
import xyz.khamim.cucumber.model.Goods;

import java.util.*;

@Repository
public class GoodsRepository {

    private final Map<Long, Goods> goodsMap = new HashMap<>();

    private Long generatedId = 1L;

    public List<Goods> findAll() {

        return new ArrayList<>(goodsMap.values());
    }

    public Optional<Goods> findById(Long id) {

        return Optional.of(goodsMap.get(id));
    }

    public Goods save(Goods goods) {

        if (goods.getId() == null || goods.getId() == 0) {
           goods.setId(generatedId);
        }

        goodsMap.put(generatedId++, goods);

        return goods;
    }

    public boolean existsById(Long id) {

        return goodsMap.containsKey(id);
    }

    public void deleteById(Long id) {

        goodsMap.remove(id);
    }
}
