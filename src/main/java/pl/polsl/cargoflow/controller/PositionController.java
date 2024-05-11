package pl.polsl.cargoflow.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import pl.polsl.cargoflow.model.Employee;
import pl.polsl.cargoflow.model.dto.PositionRequest;
import pl.polsl.cargoflow.model.dto.PositionResponse;
import pl.polsl.cargoflow.model.exception.UnauthorizedException;
import pl.polsl.cargoflow.service.AuthService;
import pl.polsl.cargoflow.service.PositionService;
import java.util.List;

@RestController
@RequestMapping("/positions")
@SecurityRequirement(name = "basicAuth")
public class PositionController {

    private final PositionService positionService;
    private final AuthService authService;

    public PositionController(PositionService positionService, AuthService authService) {
        this.positionService = positionService;
        this.authService = authService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<PositionResponse> getPositionById(@RequestHeader("Authorization") String authHeader, @PathVariable Long id) {
        Employee employee = authService.authenticate(authHeader);
        if (employee == null) {
            throw new UnauthorizedException();
        }
        if (!employee.getPosition().getName().equals("admin")) {
            throw new UnauthorizedException();
        }
        PositionResponse positionResponse = positionService.getById(id);
        return ResponseEntity.ok(positionResponse);
    }

    @GetMapping
    public ResponseEntity<List<PositionResponse>> getAllPositions(@RequestHeader("Authorization") String authHeader) {
        Employee employee = authService.authenticate(authHeader);
        if (employee == null) {
            throw new UnauthorizedException();
        }
        if (!employee.getPosition().getName().equals("admin")) {
            throw new UnauthorizedException();
        }
        List<PositionResponse> positionResponses = positionService.getAll();
        return ResponseEntity.ok(positionResponses);
    }

    @PostMapping
    public ResponseEntity<PositionResponse> createPosition(@RequestHeader("Authorization") String authHeader, @RequestBody PositionRequest positionRequest) {
        Employee employee = authService.authenticate(authHeader);
        if (employee == null) {
            throw new UnauthorizedException();
        }
        if (!employee.getPosition().getName().equals("admin")) {
            throw new UnauthorizedException();
        }
        PositionResponse positionResponse = positionService.save(positionRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(positionResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PositionResponse> updatePosition(@RequestHeader("Authorization") String authHeader, @PathVariable Long id, @RequestBody PositionRequest positionRequest) {
        Employee employee = authService.authenticate(authHeader);
        if (employee == null) {
            throw new UnauthorizedException();
        }
        if (!employee.getPosition().getName().equals("admin")) {
            throw new UnauthorizedException();
        }
        PositionResponse positionResponse = positionService.update(id, positionRequest);
        return ResponseEntity.ok(positionResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePosition(@RequestHeader("Authorization") String authHeader, @PathVariable Long id) {
        Employee employee = authService.authenticate(authHeader);
        if (employee == null) {
            throw new UnauthorizedException();
        }
        if (!employee.getPosition().getName().equals("admin")) {
            throw new UnauthorizedException();
        }
        positionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
