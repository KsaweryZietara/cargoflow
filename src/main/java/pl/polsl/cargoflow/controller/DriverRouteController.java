package pl.polsl.cargoflow.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import pl.polsl.cargoflow.model.Employee;
import pl.polsl.cargoflow.model.dto.DriverRouteEmployeeRequest;
import pl.polsl.cargoflow.model.dto.DriverRouteRequest;
import pl.polsl.cargoflow.model.dto.DriverRouteResponse;
import pl.polsl.cargoflow.model.exception.UnauthorizedException;
import pl.polsl.cargoflow.service.AuthService;
import pl.polsl.cargoflow.service.DriverRouteService;
import java.util.List;

@RestController
@RequestMapping("/driver-routes")
@SecurityRequirement(name = "basicAuth")
public class DriverRouteController {

    private final DriverRouteService driverRouteService;
    private final AuthService authService;

    public DriverRouteController(DriverRouteService driverRouteService, AuthService authService) {
        this.driverRouteService = driverRouteService;
        this.authService = authService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<DriverRouteResponse> getDriverRouteById(@RequestHeader("Authorization") String authHeader, @PathVariable Long id) {
        boolean isAuthenticated = authService.authenticateAdmin(authHeader);
        if (!isAuthenticated) {
            throw new UnauthorizedException();
        }
        DriverRouteResponse driverRouteResponse = driverRouteService.getById(id);
        return ResponseEntity.ok(driverRouteResponse);
    }

    @GetMapping
    public ResponseEntity<List<DriverRouteResponse>> getAllDriverRoutes(@RequestHeader("Authorization") String authHeader) {
        boolean isAuthenticated = authService.authenticateAdmin(authHeader);
        if (!isAuthenticated) {
            throw new UnauthorizedException();
        }
        List<DriverRouteResponse> driverRouteResponses = driverRouteService.getAll();
        return ResponseEntity.ok(driverRouteResponses);
    }

    @PostMapping
    public ResponseEntity<DriverRouteResponse> createDriverRoute(@RequestHeader("Authorization") String authHeader, @RequestBody DriverRouteRequest driverRouteRequest) {
        boolean isAuthenticated = authService.authenticateAdmin(authHeader);
        if (!isAuthenticated) {
            throw new UnauthorizedException();
        }
        DriverRouteResponse driverRouteResponse = driverRouteService.save(driverRouteRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(driverRouteResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DriverRouteResponse> updateDriverRoute(@RequestHeader("Authorization") String authHeader, @PathVariable Long id, @RequestBody DriverRouteRequest driverRouteRequest) {
        boolean isAuthenticated = authService.authenticateAdmin(authHeader);
        if (!isAuthenticated) {
            throw new UnauthorizedException();
        }
        DriverRouteResponse driverRouteResponse = driverRouteService.update(id, driverRouteRequest);
        return ResponseEntity.ok(driverRouteResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDriverRoute(@RequestHeader("Authorization") String authHeader, @PathVariable Long id) {
        boolean isAuthenticated = authService.authenticateAdmin(authHeader);
        if (!isAuthenticated) {
            throw new UnauthorizedException();
        }
        driverRouteService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/employee")
    public ResponseEntity<List<DriverRouteResponse>> getAllDriverRoutesByEmployee(@RequestHeader("Authorization") String authHeader) {
        Employee employee = authService.authenticateEmployee(authHeader);
        if (employee == null) {
            throw new UnauthorizedException();
        }
        List<DriverRouteResponse> driverRouteResponses = driverRouteService.getAllByEmployee(employee);
        return ResponseEntity.ok(driverRouteResponses);
    }

    @PostMapping("/employee")
    public ResponseEntity<DriverRouteResponse> createDriverRouteByEmployee(@RequestHeader("Authorization") String authHeader, @RequestBody DriverRouteEmployeeRequest driverRouteRequest) {
        Employee employee = authService.authenticateEmployee(authHeader);
        if (employee == null) {
            throw new UnauthorizedException();
        }
        DriverRouteResponse driverRouteResponse = driverRouteService.save(new DriverRouteRequest(driverRouteRequest, employee.getId()));
        return ResponseEntity.status(HttpStatus.CREATED).body(driverRouteResponse);
    }

    @PutMapping("/{id}/employee")
    public ResponseEntity<DriverRouteResponse> updateDriverRouteByEmployee(@RequestHeader("Authorization") String authHeader, @PathVariable Long id, @RequestBody DriverRouteEmployeeRequest driverRouteRequest) {
        Employee employee = authService.authenticateEmployee(authHeader);
        if (employee == null) {
            throw new UnauthorizedException();
        }
        DriverRouteResponse driverRouteResponse = driverRouteService.update(id, new DriverRouteRequest(driverRouteRequest, employee.getId()));
        return ResponseEntity.ok(driverRouteResponse);
    }

    @DeleteMapping("/{id}/employee")
    public ResponseEntity<Void> deleteDriverRouteByEmployee(@RequestHeader("Authorization") String authHeader, @PathVariable Long id) {
        Employee employee = authService.authenticateEmployee(authHeader);
        if (employee == null) {
            throw new UnauthorizedException();
        }
        driverRouteService.deleteByEmployee(id, employee);
        return ResponseEntity.noContent().build();
    }
}
