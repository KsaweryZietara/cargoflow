package pl.polsl.cargoflow.model;

import java.util.List;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.polsl.cargoflow.model.dto.DrivingLicenseRequest;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class DrivingLicense {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @NotBlank
    private String name;

    @ManyToMany(mappedBy = "driverLicenses")
    private List<Employee> employees;

    public DrivingLicense(DrivingLicenseRequest drivingLicenseRequest) {
        this.name = drivingLicenseRequest.getName();
    }
}
