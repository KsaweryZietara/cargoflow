package pl.polsl.cargoflow.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.polsl.cargoflow.model.DrivingLicense;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DrivingLicenseResponse {
    private Long id;

    private String name;

    public DrivingLicenseResponse(DrivingLicense city) {
        this.id = city.getId();
        this.name = city.getName();
    }
}
