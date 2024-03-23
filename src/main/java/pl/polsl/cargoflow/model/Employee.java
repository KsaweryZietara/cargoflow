package pl.polsl.cargoflow.model;

import java.sql.Date;
import java.util.List;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.polsl.cargoflow.model.dto.EmployeeRequest;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Employee {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String surname;

    @NotBlank
    @Size(min = 11, max = 11)
    private String pesel;

    private Date birthDate;

    @ManyToOne
    @NotNull
    private Position position;

    @ManyToMany
    private List<DrivingLicense> driverLicenses;

    @OneToMany(mappedBy = "employee")
    private List<DriverRoute> driverRoutes;

    public Employee(EmployeeRequest employeeRequest, Position position, List<DrivingLicense> drivingLicenses, List<DriverRoute> driverRoutes) {
        this.name = employeeRequest.getName();
        this.surname = employeeRequest.getSurname();
        this.pesel = employeeRequest.getPesel();
        this.birthDate = employeeRequest.getBirthDate();
        this.position = position;
        this.driverLicenses = drivingLicenses;
        this.driverRoutes = driverRoutes;
    }
}