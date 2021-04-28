package smat.meal.service;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import smat.meal.entity.DishEntity;
import smat.meal.repository.DishRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class SmatService {

    private final DishRepository dishRepository;

    public List<DishEntity> getAllDish() {
        return  dishRepository.findAll();
    }

    public List<DishEntity> getMeal() {
        List<DishEntity> listDish = new ArrayList<>();
        DishEntity dishEntity;

        for (int i = 0; i < 3; i++) {
            dishEntity = dishRepository.findByType(i + 1);
            listDish.add(dishEntity);
        }
        return listDish;
    }
}
