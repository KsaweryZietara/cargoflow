package pl.polsl.cargoflow.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import pl.polsl.cargoflow.model.Route;

@AllArgsConstructor
@Getter
@Setter
public class RouteResponse {
    private Long id;

    private CityResponse departureCity;

    private CityResponse arrivalCity;

    private Double distance;

    public RouteResponse(Route route) {
        this.id = route.getId();
        this.distance = route.getDistance();
        this.departureCity = new CityResponse(route.getDepartureCity());
        this.arrivalCity = new CityResponse(route.getArrivalCity());
    }
}
