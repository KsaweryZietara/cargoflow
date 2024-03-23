package pl.polsl.cargoflow.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.polsl.cargoflow.model.Vehicle;
import pl.polsl.cargoflow.model.VehicleType;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class VehicleResponse {
    private Long id;

    private VehicleType type;

    private String engineNumber;

    private Double engineCapacity;

    private String brand;

    private Double maximumCapacity;

    public VehicleResponse(Vehicle vehicle) {
        this.id = vehicle.getId();
        this.type = vehicle.getType();
        this.engineNumber = vehicle.getEngineNumber();
        this.engineCapacity = vehicle.getEngineCapacity();
        this.brand = vehicle.getBrand();
        this.maximumCapacity = vehicle.getMaximumCapacity();
    }
}
