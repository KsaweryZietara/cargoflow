package pl.polsl.cargoflow.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import pl.polsl.cargoflow.model.dto.DriverRouteRequest;
import pl.polsl.cargoflow.model.dto.DriverRouteResponse;
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
        authService.authenticate(authHeader);
        DriverRouteResponse driverRouteResponse = driverRouteService.getById(id);
        return ResponseEntity.ok(driverRouteResponse);
    }

    @GetMapping
    public ResponseEntity<List<DriverRouteResponse>> getAllDriverRoutes(@RequestHeader("Authorization") String authHeader) {
        authService.authenticate(authHeader);
        List<DriverRouteResponse> driverRouteResponses = driverRouteService.getAll();
        return ResponseEntity.ok(driverRouteResponses);
    }

    @PostMapping
    public ResponseEntity<DriverRouteResponse> createDriverRoute(@RequestHeader("Authorization") String authHeader, @RequestBody DriverRouteRequest driverRouteRequest) {
        authService.authenticate(authHeader);
        DriverRouteResponse driverRouteResponse = driverRouteService.save(driverRouteRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(driverRouteResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DriverRouteResponse> updateDriverRoute(@RequestHeader("Authorization") String authHeader, @PathVariable Long id, @RequestBody DriverRouteRequest driverRouteRequest) {
        authService.authenticate(authHeader);
        DriverRouteResponse driverRouteResponse = driverRouteService.update(id, driverRouteRequest);
        return ResponseEntity.ok(driverRouteResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDriverRoute(@RequestHeader("Authorization") String authHeader, @PathVariable Long id) {
        authService.authenticate(authHeader);
        driverRouteService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
