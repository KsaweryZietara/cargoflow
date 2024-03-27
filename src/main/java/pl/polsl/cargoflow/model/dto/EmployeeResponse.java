package pl.polsl.cargoflow.model.dto;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.polsl.cargoflow.model.Employee;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EmployeeResponse {
    private Long id;

    private String name;

    private String surname;

    private String pesel;

    private Date birthDate;

    private String login;

    private PositionResponse position;

    private List<DrivingLicenseResponse> driverLicenses;

    public EmployeeResponse(Employee employee) {
        this.id = employee.getId();
        this.name = employee.getName();
        this.surname = employee.getSurname();
        this.pesel = employee.getPesel();
        this.birthDate = employee.getBirthDate();
        this.login = employee.getLogin();
        this.position = new PositionResponse(employee.getPosition());
        this.driverLicenses = employee.getDriverLicenses().stream().map(DrivingLicenseResponse::new).collect(Collectors.toList());
    }
}
