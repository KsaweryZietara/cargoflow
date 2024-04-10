package pl.polsl.cargoflow.model.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CompleteDriverRouteRequest {
    private LocalDateTime actualDepartureDate;

    private LocalDateTime actualArrivalDate;

    private String comments;
}
