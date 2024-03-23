package pl.polsl.cargoflow.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import pl.polsl.cargoflow.model.dto.EmployeeRequest;
import pl.polsl.cargoflow.model.dto.EmployeeResponse;
import pl.polsl.cargoflow.service.AuthService;
import pl.polsl.cargoflow.service.EmployeeService;
import java.util.List;

@RestController
@RequestMapping("/employees")
@SecurityRequirement(name = "basicAuth")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final AuthService authService;

    public EmployeeController(EmployeeService employeeService, AuthService authService) {
        this.employeeService = employeeService;
        this.authService = authService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponse> getEmployeeById(@RequestHeader("Authorization") String authHeader, @PathVariable Long id) {
        authService.authenticate(authHeader);
        EmployeeResponse employeeResponse = employeeService.getById(id);
        return ResponseEntity.ok(employeeResponse);
    }

    @GetMapping
    public ResponseEntity<List<EmployeeResponse>> getAllEmployees(@RequestHeader("Authorization") String authHeader) {
        authService.authenticate(authHeader);
        List<EmployeeResponse> employeeResponses = employeeService.getAll();
        return ResponseEntity.ok(employeeResponses);
    }

    @PostMapping
    public ResponseEntity<EmployeeResponse> createEmployee(@RequestHeader("Authorization") String authHeader, @RequestBody EmployeeRequest employeeRequest) {
        authService.authenticate(authHeader);
        EmployeeResponse employeeResponse = employeeService.save(employeeRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(employeeResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeResponse> updateEmployee(@RequestHeader("Authorization") String authHeader, @PathVariable Long id, @RequestBody EmployeeRequest employeeRequest) {
        authService.authenticate(authHeader);
        EmployeeResponse employeeResponse = employeeService.update(id, employeeRequest);
        return ResponseEntity.ok(employeeResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@RequestHeader("Authorization") String authHeader, @PathVariable Long id) {
        authService.authenticate(authHeader);
        employeeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
