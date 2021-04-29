package smat.meal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParticipantDTO {
    private int guestAmount;
    private Instant timeArrived;
}
