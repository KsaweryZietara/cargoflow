package pl.polsl.cargoflow.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import pl.polsl.cargoflow.model.Employee;
import pl.polsl.cargoflow.model.dto.EmployeeRequest;
import pl.polsl.cargoflow.model.dto.EmployeeResponse;
import pl.polsl.cargoflow.model.exception.UnauthorizedException;
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
        boolean isAuthenticated = authService.authenticateAdmin(authHeader);
        if (!isAuthenticated) {
            throw new UnauthorizedException();
        }
        EmployeeResponse employeeResponse = employeeService.getById(id);
        return ResponseEntity.ok(employeeResponse);
    }

    @GetMapping("/current")
    public ResponseEntity<EmployeeResponse> getCurrentEmployee(@RequestHeader("Authorization") String authHeader) {
        Employee employee = authService.authenticateEmployee(authHeader);
        if (employee == null) {
            throw new UnauthorizedException();
        }
        return ResponseEntity.ok(new EmployeeResponse(employee));
    }

    @GetMapping
    public ResponseEntity<List<EmployeeResponse>> getAllEmployees(@RequestHeader("Authorization") String authHeader) {
        boolean isAuthenticated = authService.authenticateAdmin(authHeader);
        if (!isAuthenticated) {
            throw new UnauthorizedException();
        }
        List<EmployeeResponse> employeeResponses = employeeService.getAll();
        return ResponseEntity.ok(employeeResponses);
    }

    @PostMapping
    public ResponseEntity<EmployeeResponse> createEmployee(@RequestHeader("Authorization") String authHeader, @RequestBody EmployeeRequest employeeRequest) {
        boolean isAuthenticated = authService.authenticateAdmin(authHeader);
        if (!isAuthenticated) {
            throw new UnauthorizedException();
        }
        EmployeeResponse employeeResponse = employeeService.save(employeeRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(employeeResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeResponse> updateEmployee(@RequestHeader("Authorization") String authHeader, @PathVariable Long id, @RequestBody EmployeeRequest employeeRequest) {
        boolean isAuthenticated = authService.authenticateAdmin(authHeader);
        if (!isAuthenticated) {
            throw new UnauthorizedException();
        }
        EmployeeResponse employeeResponse = employeeService.update(id, employeeRequest);
        return ResponseEntity.ok(employeeResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@RequestHeader("Authorization") String authHeader, @PathVariable Long id) {
        boolean isAuthenticated = authService.authenticateAdmin(authHeader);
        if (!isAuthenticated) {
            throw new UnauthorizedException();
        }
        employeeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
