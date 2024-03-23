package pl.polsl.cargoflow.model.dto;

import java.sql.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class EmployeeRequest {
    private String name;

    private String surname;

    private String pesel;

    private Date birthDate;
    
    private Long positionId;

    private List<Long> driverLicensesIds;

    private List<Long> driverRoutesIds;
}
