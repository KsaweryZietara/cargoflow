package pl.polsl.cargoflow.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.polsl.cargoflow.model.DriverRoute;
import pl.polsl.cargoflow.model.Employee;
import pl.polsl.cargoflow.model.Route;
import pl.polsl.cargoflow.model.Vehicle;
import pl.polsl.cargoflow.model.dto.DriverRouteResponse;
import pl.polsl.cargoflow.model.dto.DriverRouteRequest;
import pl.polsl.cargoflow.repo.DriverRouteRepo;
import pl.polsl.cargoflow.repo.EmployeeRepo;
import pl.polsl.cargoflow.repo.RouteRepo;
import pl.polsl.cargoflow.repo.VehicleRepo;

@Service
public class DriverRouteService {

    private final DriverRouteRepo driverRouteRepo;
    private final EmployeeRepo employeeRepo;
    private final VehicleRepo vehicleRepo;
    private final RouteRepo routeRepo;

    public DriverRouteService(
        DriverRouteRepo DriverRouteRepo,
        EmployeeRepo employeeRepo,
        VehicleRepo vehicleRepo,
        RouteRepo routeRepo) {
            this.driverRouteRepo = DriverRouteRepo;
            this.employeeRepo = employeeRepo;
            this.vehicleRepo = vehicleRepo;
            this.routeRepo = routeRepo;
    }

    public DriverRouteResponse getById(Long id) {
        DriverRoute driverRoute = driverRouteRepo.findById(id).orElseThrow(() -> 
            new RuntimeException("DriverRoute with id " + id + " not found")
        );
        return new DriverRouteResponse(driverRoute);
    }

    public List<DriverRouteResponse> getAll() {
        return driverRouteRepo.findAll()
                .stream()
                .map(DriverRouteResponse::new)
                .collect(Collectors.toList());
    }

    public List<DriverRouteResponse> getAllByEmployee(Employee employee) {
        return driverRouteRepo.findAllByEmployee(employee)
                .stream()
                .map(DriverRouteResponse::new)
                .collect(Collectors.toList());
    }

    public DriverRouteResponse save(DriverRouteRequest driverRouteRequest) {
        Employee employee = employeeRepo.findById(driverRouteRequest.getEmployeeId()).orElseThrow(() -> 
            new RuntimeException("Employee with id " + driverRouteRequest.getEmployeeId() + " not found")
        );
        Vehicle vehicle = vehicleRepo.findById(driverRouteRequest.getVehicleId()).orElseThrow(() -> 
            new RuntimeException("Vehicle with id " + driverRouteRequest.getVehicleId() + " not found")
        );
        Route route = routeRepo.findById(driverRouteRequest.getRouteId()).orElseThrow(() -> 
            new RuntimeException("Route with id " + driverRouteRequest.getRouteId() + " not found")
        );
        DriverRoute driverRoute = driverRouteRepo.save(new DriverRoute(driverRouteRequest, employee, vehicle, route));
        return new DriverRouteResponse(driverRoute);
    }

    public DriverRouteResponse update(Long id, DriverRouteRequest driverRouteRequest) {
        Employee employee = employeeRepo.findById(driverRouteRequest.getEmployeeId()).orElseThrow(() -> 
            new RuntimeException("Employee with id " + driverRouteRequest.getEmployeeId() + " not found")
        );
        Vehicle vehicle = vehicleRepo.findById(driverRouteRequest.getVehicleId()).orElseThrow(() -> 
            new RuntimeException("Vehicle with id " + driverRouteRequest.getVehicleId() + " not found")
        );
        Route route = routeRepo.findById(driverRouteRequest.getRouteId()).orElseThrow(() -> 
            new RuntimeException("Route with id " + driverRouteRequest.getRouteId() + " not found")
        );
        return driverRouteRepo.findById(id)
                .map(driverRoute -> {
                    driverRoute.setDepartureDate(driverRouteRequest.getDepartureDate());
                    driverRoute.setArrivalDate(driverRouteRequest.getArrivalDate());
                    driverRoute.setCargoDescription(driverRouteRequest.getCargoDescription());
                    driverRoute.setEmployee(employee);
                    driverRoute.setVehicle(vehicle);
                    driverRoute.setRoute(route);
                    return driverRouteRepo.save(driverRoute);
                })
                .map(DriverRouteResponse::new)
                .orElseThrow(() ->
                    new RuntimeException("DriverRoute with id " + id + " not found")
                );
    }

    public void delete(Long id) {
        driverRouteRepo.deleteById(id);
    }

    @Transactional
    public void deleteByEmployee(Long id, Employee employee) {
        driverRouteRepo.deleteByIdAndEmployee(id, employee);
    }
}
