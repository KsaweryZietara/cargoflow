package pl.polsl.cargoflow.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import pl.polsl.cargoflow.model.Credentials;
import pl.polsl.cargoflow.model.DriverRoute;
import pl.polsl.cargoflow.model.DrivingLicense;
import pl.polsl.cargoflow.model.Employee;
import pl.polsl.cargoflow.model.Position;
import pl.polsl.cargoflow.model.dto.EmployeeResponse;
import pl.polsl.cargoflow.model.dto.EmployeeRequest;
import pl.polsl.cargoflow.repo.DriverRouteRepo;
import pl.polsl.cargoflow.repo.DrivingLicenseRepo;
import pl.polsl.cargoflow.repo.EmployeeRepo;
import pl.polsl.cargoflow.repo.PositionRepo;

@Service
public class EmployeeService {

    private final EmployeeRepo employeeRepo;
    private final PositionRepo positionRepo;
    private final DrivingLicenseRepo drivingLicenseRepo;
    private final DriverRouteRepo driverRouteRepo;

    public EmployeeService(
        EmployeeRepo employeeRepo,
        PositionRepo positionRepo,
        DrivingLicenseRepo drivingLicenseRepo,
        DriverRouteRepo driverRouteRepo) {
            this.employeeRepo = employeeRepo;
            this.positionRepo = positionRepo;
            this.drivingLicenseRepo = drivingLicenseRepo;
            this.driverRouteRepo = driverRouteRepo;
    }

    public EmployeeResponse getById(Long id) {
        Employee employee = employeeRepo.findById(id).orElseThrow(() -> 
            new RuntimeException("Employee with id " + id + " not found")
        );
        return new EmployeeResponse(employee);
    }

    public List<EmployeeResponse> getAll() {
        return employeeRepo.findAll()
                .stream()
                .map(EmployeeResponse::new)
                .collect(Collectors.toList());
    }

    public EmployeeResponse save(EmployeeRequest employeeRequest) {
        Position position = positionRepo.findById(employeeRequest.getPositionId()).orElseThrow(() -> 
            new RuntimeException("Position with id " + employeeRequest.getPositionId() + " not found")
        );
        List<DrivingLicense> drivingLicenses = employeeRequest.getDriverLicensesIds().stream().map(id -> 
            drivingLicenseRepo.findById(id).orElseThrow(() -> 
                new RuntimeException("Driving license with id " + id + " not found")
            )
        ).collect(Collectors.toList());
        List<DriverRoute> driverRoutes = employeeRequest.getDriverRoutesIds().stream().map(id -> 
            driverRouteRepo.findById(id).orElseThrow(() -> 
                new RuntimeException("Driver route with id " + id + " not found")
            )
        ).collect(Collectors.toList());
        Employee employee = employeeRepo.save(new Employee(employeeRequest, position, drivingLicenses, driverRoutes));
        return new EmployeeResponse(employee);
    }

    public EmployeeResponse update(Long id, EmployeeRequest employeeRequest) {
        Position position = positionRepo.findById(employeeRequest.getPositionId()).orElseThrow(() -> 
            new RuntimeException("Position with id " + employeeRequest.getPositionId() + " not found")
        );
        List<DrivingLicense> drivingLicenses = employeeRequest.getDriverLicensesIds().stream().map(driverLicenseId -> 
            drivingLicenseRepo.findById(driverLicenseId).orElseThrow(() -> 
                new RuntimeException("Driving license with id " + id + " not found")
            )
        ).collect(Collectors.toList());
        List<DriverRoute> driverRoutes = employeeRequest.getDriverRoutesIds().stream().map(driverRouteId -> 
            driverRouteRepo.findById(driverRouteId).orElseThrow(() -> 
                new RuntimeException("Driver route with id " + id + " not found")
            )
        ).collect(Collectors.toList());
        return employeeRepo.findById(id)
                .map(employee -> {
                    employee.setName(employeeRequest.getName());
                    employee.setSurname(employeeRequest.getSurname());
                    employee.setPesel(employeeRequest.getPesel());
                    employee.setBirthDate(employeeRequest.getBirthDate());
                    employee.setCredentials(new Credentials(employeeRequest.getLogin(), employeeRequest.getPassword()));
                    employee.setPosition(position);
                    employee.setDriverLicenses(drivingLicenses);
                    employee.setDriverRoutes(driverRoutes);
                    return employeeRepo.save(employee);
                })
                .map(EmployeeResponse::new)
                .orElseThrow(() ->
                    new RuntimeException("Employee with id " + id + " not found")
                );
    }

    public void delete(Long id) {
        employeeRepo.deleteById(id);
    }
}
