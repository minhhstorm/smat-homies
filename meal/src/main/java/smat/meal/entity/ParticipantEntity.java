package smat.meal.entity;

import lombok.*;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "participant")
public class ParticipantEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "guest_amount")
    private int guestAmount;

    @Column(name = "is_late")
    private boolean isLate;

    @Column(name = "time_arrived")
    private Instant timeArrived;

    @Column(name = "user_id")
    private Long userId;

    @ManyToOne
    @JoinColumn(name = "meal_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private MealEntity mealEntity;

}
