package pl.polsl.cargoflow.model;

import java.util.List;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.polsl.cargoflow.model.dto.CityRequest;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class City {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @NotBlank
    private String name;

    @OneToMany(mappedBy = "departureCity")
    private List<Route> departureRoute;

    @OneToMany(mappedBy = "arrivalCity")
    private List<Route> arrivalRoute;

    public City(CityRequest cityRequest) {
        this.name = cityRequest.getName();
    }
}
