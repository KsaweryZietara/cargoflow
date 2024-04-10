package pl.polsl.cargoflow.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import pl.polsl.cargoflow.model.Employee;
import pl.polsl.cargoflow.model.dto.RouteRequest;
import pl.polsl.cargoflow.model.dto.RouteResponse;
import pl.polsl.cargoflow.model.exception.UnauthorizedException;
import pl.polsl.cargoflow.service.AuthService;
import pl.polsl.cargoflow.service.RouteService;
import java.util.List;

@RestController
@RequestMapping("/routes")
@SecurityRequirement(name = "basicAuth")
public class RouteController {

    private final RouteService routeService;
    private final AuthService authService;

    public RouteController(RouteService routeService, AuthService authService) {
        this.routeService = routeService;
        this.authService = authService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<RouteResponse> getRouteById(@RequestHeader("Authorization") String authHeader, @PathVariable Long id) {
        Employee employee = authService.authenticateEmployee(authHeader);
        if (employee == null) {
            throw new UnauthorizedException();
        }
        if (!employee.getPosition().getName().equals("koordynator")) {
            throw new UnauthorizedException();
        }
        RouteResponse routeResponse = routeService.getById(id);
        return ResponseEntity.ok(routeResponse);
    }

    @GetMapping
    public ResponseEntity<List<RouteResponse>> getAllRoutes(@RequestHeader("Authorization") String authHeader) {
        Employee employee = authService.authenticateEmployee(authHeader);
        if (employee == null) {
            throw new UnauthorizedException();
        }
        if (!employee.getPosition().getName().equals("koordynator")) {
            throw new UnauthorizedException();
        }
        List<RouteResponse> routeResponses = routeService.getAll();
        return ResponseEntity.ok(routeResponses);
    }

    @PostMapping
    public ResponseEntity<RouteResponse> createRoute(@RequestHeader("Authorization") String authHeader, @RequestBody RouteRequest createRoute) {
        Employee employee = authService.authenticateEmployee(authHeader);
        if (employee == null) {
            throw new UnauthorizedException();
        }
        if (!employee.getPosition().getName().equals("koordynator")) {
            throw new UnauthorizedException();
        }
        RouteResponse routeResponse = routeService.save(createRoute);
        return ResponseEntity.status(HttpStatus.CREATED).body(routeResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RouteResponse> updateRoute(@RequestHeader("Authorization") String authHeader, @PathVariable Long id, @RequestBody RouteRequest createRoute) {
        Employee employee = authService.authenticateEmployee(authHeader);
        if (employee == null) {
            throw new UnauthorizedException();
        }
        if (!employee.getPosition().getName().equals("koordynator")) {
            throw new UnauthorizedException();
        }
        RouteResponse routeResponse = routeService.update(id, createRoute);
        return ResponseEntity.ok(routeResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoute(@RequestHeader("Authorization") String authHeader, @PathVariable Long id) {
        Employee employee = authService.authenticateEmployee(authHeader);
        if (employee == null) {
            throw new UnauthorizedException();
        }
        if (!employee.getPosition().getName().equals("koordynator")) {
            throw new UnauthorizedException();
        }
        routeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
