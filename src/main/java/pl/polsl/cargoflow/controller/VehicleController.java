package pl.polsl.cargoflow.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import pl.polsl.cargoflow.model.Employee;
import pl.polsl.cargoflow.model.dto.VehicleRequest;
import pl.polsl.cargoflow.model.dto.VehicleResponse;
import pl.polsl.cargoflow.model.exception.UnauthorizedException;
import pl.polsl.cargoflow.service.AuthService;
import pl.polsl.cargoflow.service.VehicleService;
import java.util.List;

@RestController
@RequestMapping("/vehicles")
@SecurityRequirement(name = "basicAuth")
public class VehicleController {

    private final VehicleService vehicleService;
    private final AuthService authService;

    public VehicleController(VehicleService vehicleService, AuthService authService) {
        this.vehicleService = vehicleService;
        this.authService = authService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<VehicleResponse> getVehicleById(@RequestHeader("Authorization") String authHeader, @PathVariable Long id) {
        Employee employee = authService.authenticateEmployee(authHeader);
        if (employee == null) {
            throw new UnauthorizedException();
        }
        if (!employee.getPosition().getName().equals("mechanik")) {
            throw new UnauthorizedException();
        }
        VehicleResponse vehicleResponse = vehicleService.getById(id);
        return ResponseEntity.ok(vehicleResponse);
    }

    @GetMapping
    public ResponseEntity<List<VehicleResponse>> getAllVehicles(@RequestHeader("Authorization") String authHeader) {
        Employee employee = authService.authenticateEmployee(authHeader);
        if (employee == null) {
            throw new UnauthorizedException();
        }
        if (!employee.getPosition().getName().equals("mechanik") && !employee.getPosition().getName().equals("koordynator")) {
            throw new UnauthorizedException();
        }
        List<VehicleResponse> vehicleResponses = vehicleService.getAll();
        return ResponseEntity.ok(vehicleResponses);
    }

    @PostMapping
    public ResponseEntity<VehicleResponse> createVehicle(@RequestHeader("Authorization") String authHeader, @RequestBody VehicleRequest vehicleRequest) {
        Employee employee = authService.authenticateEmployee(authHeader);
        if (employee == null) {
            throw new UnauthorizedException();
        }
        if (!employee.getPosition().getName().equals("mechanik")) {
            throw new UnauthorizedException();
        }
        VehicleResponse vehicleResponse = vehicleService.save(vehicleRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(vehicleResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VehicleResponse> updateVehicle(@RequestHeader("Authorization") String authHeader, @PathVariable Long id, @RequestBody VehicleRequest vehicleRequest) {
        Employee employee = authService.authenticateEmployee(authHeader);
        if (employee == null) {
            throw new UnauthorizedException();
        }
        if (!employee.getPosition().getName().equals("mechanik")) {
            throw new UnauthorizedException();
        }
        VehicleResponse vehicleResponse = vehicleService.update(id, vehicleRequest);
        return ResponseEntity.ok(vehicleResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVehicle(@RequestHeader("Authorization") String authHeader, @PathVariable Long id) {
        Employee employee = authService.authenticateEmployee(authHeader);
        if (employee == null) {
            throw new UnauthorizedException();
        }
        if (!employee.getPosition().getName().equals("mechanik")) {
            throw new UnauthorizedException();
        }
        vehicleService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
