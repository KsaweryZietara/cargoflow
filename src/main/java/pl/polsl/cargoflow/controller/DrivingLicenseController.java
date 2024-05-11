package pl.polsl.cargoflow.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import pl.polsl.cargoflow.model.Employee;
import pl.polsl.cargoflow.model.dto.DrivingLicenseRequest;
import pl.polsl.cargoflow.model.dto.DrivingLicenseResponse;
import pl.polsl.cargoflow.model.exception.UnauthorizedException;
import pl.polsl.cargoflow.service.AuthService;
import pl.polsl.cargoflow.service.DrivingLicenseService;
import java.util.List;

@RestController
@RequestMapping("/driving-licenses")
@SecurityRequirement(name = "basicAuth")
public class DrivingLicenseController {

    private final DrivingLicenseService drivingLicenseService;
    private final AuthService authService;

    public DrivingLicenseController(DrivingLicenseService drivingLicenseService, AuthService authService) {
        this.drivingLicenseService = drivingLicenseService;
        this.authService = authService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<DrivingLicenseResponse> getDrivingLicenseById(@RequestHeader("Authorization") String authHeader, @PathVariable Long id) {
        Employee employee = authService.authenticate(authHeader);
        if (employee == null) {
            throw new UnauthorizedException();
        }
        if (!employee.getPosition().getName().equals("admin")) {
            throw new UnauthorizedException();
        }
        DrivingLicenseResponse drivingLicenseResponse = drivingLicenseService.getById(id);
        return ResponseEntity.ok(drivingLicenseResponse);
    }

    @GetMapping
    public ResponseEntity<List<DrivingLicenseResponse>> getAllDrivingLicenses(@RequestHeader("Authorization") String authHeader) {
        Employee employee = authService.authenticate(authHeader);
        if (employee == null) {
            throw new UnauthorizedException();
        }
        if (!employee.getPosition().getName().equals("admin")) {
            throw new UnauthorizedException();
        }
        List<DrivingLicenseResponse> drivingLicenseResponses = drivingLicenseService.getAll();
        return ResponseEntity.ok(drivingLicenseResponses);
    }

    @PostMapping
    public ResponseEntity<DrivingLicenseResponse> createDrivingLicense(@RequestHeader("Authorization") String authHeader, @RequestBody DrivingLicenseRequest drivingLicenseRequest) {
        Employee employee = authService.authenticate(authHeader);
        if (employee == null) {
            throw new UnauthorizedException();
        }
        if (!employee.getPosition().getName().equals("admin")) {
            throw new UnauthorizedException();
        }
        DrivingLicenseResponse drivingLicenseResponse = drivingLicenseService.save(drivingLicenseRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(drivingLicenseResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DrivingLicenseResponse> updateDrivingLicense(@RequestHeader("Authorization") String authHeader, @PathVariable Long id, @RequestBody DrivingLicenseRequest drivingLicenseRequest) {
        Employee employee = authService.authenticate(authHeader);
        if (employee == null) {
            throw new UnauthorizedException();
        }
        if (!employee.getPosition().getName().equals("admin")) {
            throw new UnauthorizedException();
        }
        DrivingLicenseResponse drivingLicenseResponse = drivingLicenseService.update(id, drivingLicenseRequest);
        return ResponseEntity.ok(drivingLicenseResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDrivingLicense(@RequestHeader("Authorization") String authHeader, @PathVariable Long id) {
        Employee employee = authService.authenticate(authHeader);
        if (employee == null) {
            throw new UnauthorizedException();
        }
        if (!employee.getPosition().getName().equals("admin")) {
            throw new UnauthorizedException();
        }
        drivingLicenseService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
