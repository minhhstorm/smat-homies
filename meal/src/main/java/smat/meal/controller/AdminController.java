package smat.meal.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import smat.meal.dto.AddDishRequestDTO;
import smat.meal.dto.AddIngredientRequestDTO;
import smat.meal.repository.DishRepository;
import smat.meal.repository.IngredientRepository;
import smat.meal.service.AdminService;

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
    public ResponseEntity<String> addDiss(@Validated @RequestBody AddDishRequestDTO addDishRequestDTO) {
        if (dishRepository.existsByName(addDishRequestDTO.getName())) {
            return new ResponseEntity<>("Món ăn đã tồn tại", HttpStatus.CONFLICT);
        }
        adminService.insertDish(addDishRequestDTO);
        return new ResponseEntity<>("Thêm món ăn thành công", HttpStatus.CREATED);
    }
}
