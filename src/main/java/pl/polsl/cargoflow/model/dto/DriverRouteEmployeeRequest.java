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
public class DriverRouteEmployeeRequest {
    private LocalDateTime departureDate;

    private LocalDateTime arrivalDate;
    
    private String cargoDescription;

    private Long vehicleId;

    private Long routeId;
}
