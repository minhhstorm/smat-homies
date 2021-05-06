package smat.meal.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import smat.meal.dto.AddDishRequestDTO;
import smat.meal.dto.AddIngredientRequestDTO;
import smat.meal.dto.ParticipantDTO;
import smat.meal.entity.DishEntity;
import smat.meal.entity.IngredientEntity;
import smat.meal.entity.ParticipantEntity;
import smat.meal.repository.DishRepository;
import smat.meal.repository.IngredientRepository;
import smat.meal.repository.ParticipantRepository;

import javax.transaction.Transactional;
import java.time.*;
import java.util.*;

@Service
@AllArgsConstructor
public class AdminService {

    private final DishRepository dishRepository;
    private final IngredientRepository ingredientRepository;
    private final AuthService authService;
    private final ParticipantRepository participantRepository;

    public void insertIngredient(AddIngredientRequestDTO addIngredientRequestDTO) {
        IngredientEntity ingredientEntity = new IngredientEntity();
        ingredientEntity.setName(addIngredientRequestDTO.getName());
        ingredientEntity.setAddress(addIngredientRequestDTO.getAddress());
        ingredientEntity.setDescription(addIngredientRequestDTO.getDescription());
        ingredientEntity.setPrice(addIngredientRequestDTO.getPrice());
        ingredientEntity.setType(addIngredientRequestDTO.getType());
        ingredientRepository.save(ingredientEntity);
    }

    public void addDish(AddDishRequestDTO addDishRequestDTO) {
        DishEntity dishEntity = new DishEntity();
        dishEntity.setName(addDishRequestDTO.getName());
        dishEntity.setDescription(addDishRequestDTO.getDescription());
        dishEntity.setType(addDishRequestDTO.getType());
        Set<IngredientEntity> ingredients = new HashSet<>();

        int size = addDishRequestDTO.getIngredients().size();
        for (int i = 0; i < size; i++) {
            IngredientEntity ingredientEntity = new IngredientEntity();
            ingredientEntity.setId(addDishRequestDTO.getIngredients().get(i));
            ingredients.add(ingredientEntity);
        }
        dishEntity.setIngredients(ingredients);
        dishRepository.save(dishEntity);
    }

    public List<IngredientEntity> getAllIngredient() {
        return ingredientRepository.findAll();
    }


    @Transactional
    public void registerMeal(ParticipantDTO participantDTO) {
        LocalDate day = LocalDate.now();
        String[] timeArray = participantDTO.getTimeArrived().split(":");
        LocalTime hour = LocalTime.of(Integer.parseInt(timeArray[0]), Integer.parseInt(timeArray[1]), 0);
        LocalTime mock = LocalTime.of(20, 0, 0);

        Long userid = authService.getCurrentUser().getId();
        ParticipantEntity participantEntity = participantRepository.findByUserId(userid);
        if (participantEntity == null) {
            participantEntity = new ParticipantEntity();
        }

        participantEntity.setLate(hour.isAfter(mock));

        participantEntity.setTimeArrived((LocalDateTime.of(day, hour)));
        participantEntity.setGuestAmount(participantDTO.getGuestAmount());
        participantEntity.setUserId(authService.getCurrentUser().getId());
        participantEntity.setName(authService.getCurrentUser().getName());
        participantRepository.save(participantEntity);
    }

    public List<ParticipantEntity> showParticipant() {
        return participantRepository.findAll();
    }

    public List<IngredientEntity> getListIngredientMarket(List<DishEntity> dishEntities) {
        List<ParticipantEntity> participantEntities = participantRepository.findAll();
        int member = participantEntities.size();
        for (ParticipantEntity participantEntity : participantEntities) {
            member = participantEntity.getGuestAmount() + member;
        }
        for (DishEntity dishEntity : dishEntities) {
            System.out.println(dishRepository.findByName(dishEntity.getName()));
        }


        return null;
    }
}
