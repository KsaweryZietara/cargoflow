package pl.polsl.cargoflow.model.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class DriverRouteRequest {
    private LocalDateTime departureDate;

    private LocalDateTime arrivalDate;
    
    private String cargoDescription;

    private Long employeeId;

    private Long vehicleId;

    private Long routeId;
}
