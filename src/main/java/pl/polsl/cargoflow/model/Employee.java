package pl.polsl.cargoflow.model;

import java.sql.Date;
import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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

    @OneToOne(cascade = CascadeType.ALL)
    private Credentials credentials;

    @ManyToOne
    @NotNull
    private Position position;

    @ManyToMany
    private List<DrivingLicense> driverLicenses;

    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY)
    private List<DriverRoute> driverRoutes;

    public Employee(EmployeeRequest employeeRequest, Position position, List<DrivingLicense> drivingLicenses) {
        this.name = employeeRequest.getName();
        this.surname = employeeRequest.getSurname();
        this.pesel = employeeRequest.getPesel();
        this.birthDate = employeeRequest.getBirthDate();
        this.credentials = new Credentials(employeeRequest.getLogin(), employeeRequest.getPassword());
        this.position = position;
        this.driverLicenses = drivingLicenses;
    }
}
