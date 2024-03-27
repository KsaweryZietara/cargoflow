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
public class DriverRouteRequest {
    private LocalDateTime departureDate;

    private LocalDateTime arrivalDate;
    
    private String cargoDescription;

    private Long employeeId;

    private Long vehicleId;

    private Long routeId;

    public DriverRouteRequest(DriverRouteEmployeeRequest driverRouteEmployeeRequest, Long employeeId) {
        this.departureDate = driverRouteEmployeeRequest.getDepartureDate();
        this.arrivalDate = driverRouteEmployeeRequest.getArrivalDate();
        this.cargoDescription = driverRouteEmployeeRequest.getCargoDescription();
        this.employeeId = employeeId;
        this.vehicleId = driverRouteEmployeeRequest.getVehicleId();
        this.routeId = driverRouteEmployeeRequest.getRouteId();
    }
}
