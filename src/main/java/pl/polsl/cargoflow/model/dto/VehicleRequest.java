package pl.polsl.cargoflow.model.dto;

import java.sql.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.polsl.cargoflow.model.VehicleType;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class VehicleRequest {
    private VehicleType type;

    private String engineNumber;

    private Double engineCapacity;

    private String brand;

    private Double maximumCapacity;

    private boolean operational = true;

    private Date nextTechnicalInspection;
}
