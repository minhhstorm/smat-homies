package smat.meal.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import smat.meal.dto.AddDishRequestDTO;
import smat.meal.dto.AddIngredientRequestDTO;
import smat.meal.entity.DishEntity;
import smat.meal.entity.IngredientEntity;
import smat.meal.repository.DishRepository;
import smat.meal.repository.IngredientRepository;
import smat.meal.service.AdminService;

import java.util.List;

@RestController
@RequestMapping("/admin")
@AllArgsConstructor
public class AdminController {

    private final AdminService adminService;
    @Autowired
    private final DishRepository dishRepository;

    @Autowired
    private final IngredientRepository ingredientRepository;

    @PostMapping("/add-ingredient")
    public ResponseEntity<String> addIngredient(@Validated @RequestBody AddIngredientRequestDTO addIngredientRequestDTO) {
        if (ingredientRepository.existsByName(addIngredientRequestDTO.getName())) {
            return new ResponseEntity<>("Nguyên liệu đã tồn tại", HttpStatus.CONFLICT);
        }
        adminService.insertIngredient(addIngredientRequestDTO);
        return new ResponseEntity<>("Thêm nguyên liệu thành công", HttpStatus.CREATED);
    }

    @PostMapping("/add-dish")
    public ResponseEntity<String> addDish(@Validated @RequestBody AddDishRequestDTO addDishRequestDTO) {
        if (dishRepository.existsByName(addDishRequestDTO.getName())) {
            return new ResponseEntity<>("Món ăn đã tồn tại", HttpStatus.CONFLICT);
        }
        adminService.addDish(addDishRequestDTO);
        return new ResponseEntity<>("Thêm món ăn thành công", HttpStatus.CREATED);
    }

    @GetMapping("/get-ingredient")
    public ResponseEntity<List<IngredientEntity>> getAllIngredient() {
        return new ResponseEntity<>(adminService.getAllIngredient(), HttpStatus.OK);
    }

    @GetMapping("/get-dish")
    public ResponseEntity<List<DishEntity>> getAllDish() {
        return new ResponseEntity<>(adminService.getAllDish(), HttpStatus.OK);
    }
}
