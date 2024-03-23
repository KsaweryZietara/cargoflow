package pl.polsl.cargoflow.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class CreateRoute {
    private Long departureCityId;

    private Long arrivalCityId;
    
    private Double distance;
}
