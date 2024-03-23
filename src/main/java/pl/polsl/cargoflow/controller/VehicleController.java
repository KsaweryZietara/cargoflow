package pl.polsl.cargoflow.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.polsl.cargoflow.model.dto.VehicleRequest;
import pl.polsl.cargoflow.model.dto.VehicleResponse;
import pl.polsl.cargoflow.service.VehicleService;
import java.util.List;

@RestController
@RequestMapping("/vehicles")
public class VehicleController {

    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<VehicleResponse> getVehicleById(@PathVariable Long id) {
        VehicleResponse vehicleResponse = vehicleService.getById(id);
        return ResponseEntity.ok(vehicleResponse);
    }

    @GetMapping
    public ResponseEntity<List<VehicleResponse>> getAllVehicles() {
        List<VehicleResponse> vehicleResponses = vehicleService.getAll();
        return ResponseEntity.ok(vehicleResponses);
    }

    @PostMapping
    public ResponseEntity<VehicleResponse> createVehicle(@RequestBody VehicleRequest vehicleRequest) {
        VehicleResponse vehicleResponse = vehicleService.save(vehicleRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(vehicleResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VehicleResponse> updateVehicle(@PathVariable Long id, @RequestBody VehicleRequest vehicleRequest) {
        VehicleResponse vehicleResponse = vehicleService.update(id, vehicleRequest);
        return ResponseEntity.ok(vehicleResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVehicle(@PathVariable Long id) {
        vehicleService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
