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
import smat.meal.dto.AddIngredientDishDTO;
import smat.meal.repository.DishRepository;
import smat.meal.service.AdminService;

@RestController
@RequestMapping("/smat")
@AllArgsConstructor
public class AdminController {

    private final AdminService adminService;
    @Autowired
    private final DishRepository dishRepository;

    @PostMapping("/addDish")
    public ResponseEntity<String> addDish(@Validated @RequestBody AddIngredientDishDTO addIngredientDishDTO) {
        if (dishRepository.existsByName(addIngredientDishDTO.getNameDish())) {
            return new ResponseEntity<>("Tên món đã tồn tại", HttpStatus.CONFLICT);
        }
        adminService.createDish(addIngredientDishDTO);
        return new ResponseEntity<>("Tạo món thành công", HttpStatus.CREATED);
    }
}
