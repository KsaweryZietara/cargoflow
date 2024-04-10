package pl.polsl.cargoflow.model.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.polsl.cargoflow.model.DriverRoute;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DriverRouteResponse {
    private Long id;

    private LocalDateTime departureDate;

    private LocalDateTime arrivalDate;
    
    private String cargoDescription;

    private EmployeeResponse employee;

    private VehicleResponse vehicle;

    private RouteResponse route;

    private boolean completed;

    private LocalDateTime actualDepartureDate;

    private LocalDateTime actualArrivalDate;

    private String comments;

    public DriverRouteResponse(DriverRoute driverRoute) {
        this.id = driverRoute.getId();
        this.departureDate = driverRoute.getDepartureDate();
        this.arrivalDate = driverRoute.getArrivalDate();
        this.cargoDescription = driverRoute.getCargoDescription();
        this.completed = driverRoute.isCompleted();
        this.actualDepartureDate = driverRoute.getActualDepartureDate();
        this.actualArrivalDate = driverRoute.getActualArrivalDate();
        this.comments = driverRoute.getComments();
        this.employee = new EmployeeResponse(driverRoute.getEmployee());
        this.vehicle = new VehicleResponse(driverRoute.getVehicle());
        this.route = new RouteResponse(driverRoute.getRoute());
    }
}
