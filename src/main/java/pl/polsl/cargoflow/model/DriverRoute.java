package pl.polsl.cargoflow.model;

import java.time.LocalDateTime;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.polsl.cargoflow.model.dto.DriverRouteRequest;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class DriverRoute {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @NotNull
    private LocalDateTime departureDate;

    @NotNull
    private LocalDateTime arrivalDate;
    
    @NotBlank
    private String cargoDescription;

    @ManyToOne
    @NotNull
    private Employee employee;

    @ManyToOne
    @NotNull
    private Vehicle vehicle;

    @ManyToOne
    @NotNull
    private Route route;

    public DriverRoute(DriverRouteRequest driverRouteRequest, Employee employee, Vehicle vehicle, Route route) {
        this.departureDate = driverRouteRequest.getDepartureDate();
        this.arrivalDate = driverRouteRequest.getArrivalDate();
        this.cargoDescription = driverRouteRequest.getCargoDescription();
        this.employee = employee;
        this.vehicle = vehicle;
        this.route = route;
    }
}
