package pl.polsl.cargoflow.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import pl.polsl.cargoflow.model.DriverRoute;
import pl.polsl.cargoflow.model.Employee;
import pl.polsl.cargoflow.model.Route;
import pl.polsl.cargoflow.model.Vehicle;
import pl.polsl.cargoflow.model.dto.DriverRouteResponse;
import pl.polsl.cargoflow.model.exception.UnauthorizedException;
import pl.polsl.cargoflow.model.dto.CompleteDriverRouteRequest;
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
        List<DriverRoute> driverRoutes = driverRouteRepo.findAllByEmployee(employee);
        driverRoutes.stream().forEach(route -> {
            if (!((route.getArrivalDate().isAfter(driverRouteRequest.getDepartureDate()) && route.getDepartureDate().isAfter(driverRouteRequest.getArrivalDate())) ||
                (route.getArrivalDate().isBefore(driverRouteRequest.getDepartureDate()) && route.getDepartureDate().isBefore(driverRouteRequest.getArrivalDate())))
            ) {
                throw new RuntimeException("Employee with id " + driverRouteRequest.getEmployeeId() + " has a different route planned during this period");
            }
        });
        Vehicle vehicle = vehicleRepo.findById(driverRouteRequest.getVehicleId()).orElseThrow(() -> 
            new RuntimeException("Vehicle with id " + driverRouteRequest.getVehicleId() + " not found")
        );
        if (!vehicle.isOperational()) {
            throw new RuntimeException("Vehicle with id " + driverRouteRequest.getVehicleId() + " is not operational");
        }
        List<DriverRoute> vehicleDriverRoutes = driverRouteRepo.findAllByVehicle(vehicle);
        vehicleDriverRoutes.stream().forEach(route -> {
            if (!((route.getArrivalDate().isAfter(driverRouteRequest.getDepartureDate()) && route.getDepartureDate().isAfter(driverRouteRequest.getArrivalDate())) ||
                (route.getArrivalDate().isBefore(driverRouteRequest.getDepartureDate()) && route.getDepartureDate().isBefore(driverRouteRequest.getArrivalDate())))
            ) {
                throw new RuntimeException("Vehicle with id " + driverRouteRequest.getEmployeeId() + " has a different route planned during this period");
            }
        });
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
        List<DriverRoute> driverRoutes = driverRouteRepo.findAllByEmployee(employee);
        driverRoutes.stream().forEach(route -> {
            if (!((route.getArrivalDate().isAfter(driverRouteRequest.getDepartureDate()) && route.getDepartureDate().isAfter(driverRouteRequest.getArrivalDate())) ||
                (route.getArrivalDate().isBefore(driverRouteRequest.getDepartureDate()) && route.getDepartureDate().isBefore(driverRouteRequest.getArrivalDate())))
            ) {
                throw new RuntimeException("Employee with id " + driverRouteRequest.getEmployeeId() + " has a different route planned during this period");
            }
        });
        Vehicle vehicle = vehicleRepo.findById(driverRouteRequest.getVehicleId()).orElseThrow(() -> 
            new RuntimeException("Vehicle with id " + driverRouteRequest.getVehicleId() + " not found")
        );
        if (!vehicle.isOperational()) {
            throw new RuntimeException("Vehicle with id " + driverRouteRequest.getVehicleId() + " is not operational");
        }
        List<DriverRoute> vehicleDriverRoutes = driverRouteRepo.findAllByVehicle(vehicle);
        vehicleDriverRoutes.stream().forEach(route -> {
            if (!((route.getArrivalDate().isAfter(driverRouteRequest.getDepartureDate()) && route.getDepartureDate().isAfter(driverRouteRequest.getArrivalDate())) ||
                (route.getArrivalDate().isBefore(driverRouteRequest.getDepartureDate()) && route.getDepartureDate().isBefore(driverRouteRequest.getArrivalDate())))
            ) {
                throw new RuntimeException("Vehicle with id " + driverRouteRequest.getEmployeeId() + " has a different route planned during this period");
            }
        });
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

    public DriverRouteResponse completeDriverRoute(Long driverRouteId, Long employeeId, CompleteDriverRouteRequest request) {
        DriverRoute driverRoute = driverRouteRepo.findById(driverRouteId).orElseThrow(() -> 
            new RuntimeException("Driver route with id " + driverRouteId + " not found")
        );
        if (driverRoute.getEmployee().getId() != employeeId) {
            throw new UnauthorizedException();
        }
        driverRoute.setCompleted(true);
        driverRoute.setActualArrivalDate(request.getActualArrivalDate());
        driverRoute.setActualDepartureDate(request.getActualDepartureDate());
        driverRoute.setComments(request.getComments());
        DriverRoute savedDriverRoute = driverRouteRepo.save(driverRoute);
        return new DriverRouteResponse(savedDriverRoute);
    }

    public void delete(Long id) {
        driverRouteRepo.deleteById(id);
    }

    public Double getReportByVehicle(Long id, LocalDateTime startDate, LocalDateTime endDate){
        Vehicle vehicle = vehicleRepo.findById(id).orElseThrow(() -> 
            new RuntimeException("Vehicle with id " + id + " not found")
        );
        Double totalDistance = driverRouteRepo.findAllByVehicle(vehicle)
            .stream()
            .filter(route -> route.isCompleted() && route.getDepartureDate().isAfter(startDate) && route.getArrivalDate().isBefore(endDate))
            .mapToDouble(route -> route.getRoute().getDistance())
            .sum();
        return totalDistance;
    }

    public Double getReportByEmployee(Long id, LocalDateTime startDate, LocalDateTime endDate){
        Employee employee = employeeRepo.findById(id).orElseThrow(() -> 
            new RuntimeException("Employee with id " + id + " not found")
        );
        Double totalDistance = driverRouteRepo.findAllByEmployee(employee)
            .stream()
            .filter(route -> route.isCompleted() && route.getDepartureDate().isAfter(startDate) && route.getArrivalDate().isBefore(endDate))
            .mapToDouble(route -> route.getRoute().getDistance())
            .sum();
        return totalDistance;
    }
}
