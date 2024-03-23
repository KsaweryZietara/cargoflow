package pl.polsl.cargoflow.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.polsl.cargoflow.model.City;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CityResponse {
    private Long id;

    private String name;

    public CityResponse(City city) {
        this.id = city.getId();
        this.name = city.getName();
    }
}
