package smat.meal.controller;


import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import smat.meal.entity.DishEntity;
import smat.meal.service.SmatService;

import java.util.List;

@RestController
@RequestMapping("/smat")
@AllArgsConstructor
public class SmatController {

    private final SmatService smatService;

    @GetMapping("/get-dish")
    public ResponseEntity<List<DishEntity>> getAllDish() {
        return new ResponseEntity<>(smatService.getAllDish(), HttpStatus.OK);
    }

    @GetMapping("/get-meal")
    public ResponseEntity<List<DishEntity>> getMeal() {
        return new ResponseEntity<>(smatService.getMeal(), HttpStatus.OK);
    }
}
