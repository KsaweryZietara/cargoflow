package pl.polsl.cargoflow.model;

import java.util.List;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.polsl.cargoflow.model.dto.RouteRequest;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Route {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @NotNull
    private City departureCity;

    @ManyToOne
    @NotNull
    private City arrivalCity;

    @Min(0)
    private Double distance;

    @OneToMany(mappedBy = "route")
    private List<DriverRoute> driverRoute;

    public Route(RouteRequest createRoute, City departureCity, City arrivalCity) {
        this.distance = createRoute.getDistance();
        this.departureCity = departureCity;
        this.arrivalCity = arrivalCity;
    }
}
