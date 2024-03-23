package pl.polsl.cargoflow.model;

import java.util.List;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.polsl.cargoflow.model.dto.VehicleRequest;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Vehicle {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    @NotNull
    private VehicleType type;

    @NotBlank
    private String engineNumber;

    @Min(0)
    private Double engineCapacity;

    @NotBlank
    private String brand;

    @Min(0)
    private Double maximumCapacity;
    
    @OneToMany(mappedBy = "vehicle")
    private List<DriverRoute> driverRoute;

    public Vehicle(VehicleRequest vehicleRequest) {
        this.type = vehicleRequest.getType();
        this.engineNumber = vehicleRequest.getEngineNumber();
        this.engineCapacity = vehicleRequest.getEngineCapacity();
        this.brand = vehicleRequest.getBrand();
        this.maximumCapacity = vehicleRequest.getMaximumCapacity();
    }
}
