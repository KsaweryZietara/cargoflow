package pl.polsl.cargoflow.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.polsl.cargoflow.model.dto.DriverRouteRequest;
import pl.polsl.cargoflow.model.dto.DriverRouteResponse;
import pl.polsl.cargoflow.service.DriverRouteService;
import java.util.List;

@RestController
@RequestMapping("/driver-routes")
public class DriverRouteController {

    private final DriverRouteService driverRouteService;

    public DriverRouteController(DriverRouteService driverRouteService) {
        this.driverRouteService = driverRouteService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<DriverRouteResponse> getDriverRouteById(@PathVariable Long id) {
        DriverRouteResponse driverRouteResponse = driverRouteService.getById(id);
        return ResponseEntity.ok(driverRouteResponse);
    }

    @GetMapping
    public ResponseEntity<List<DriverRouteResponse>> getAllDriverRoutes() {
        List<DriverRouteResponse> driverRouteResponses = driverRouteService.getAll();
        return ResponseEntity.ok(driverRouteResponses);
    }

    @PostMapping
    public ResponseEntity<DriverRouteResponse> createDriverRoute(@RequestBody DriverRouteRequest driverRouteRequest) {
        DriverRouteResponse driverRouteResponse = driverRouteService.save(driverRouteRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(driverRouteResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DriverRouteResponse> updateDriverRoute(@PathVariable Long id, @RequestBody DriverRouteRequest driverRouteRequest) {
        DriverRouteResponse driverRouteResponse = driverRouteService.update(id, driverRouteRequest);
        return ResponseEntity.ok(driverRouteResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDriverRoute(@PathVariable Long id) {
        driverRouteService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
