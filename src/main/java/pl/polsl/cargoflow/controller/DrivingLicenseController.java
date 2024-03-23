package pl.polsl.cargoflow.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.polsl.cargoflow.model.dto.DrivingLicenseRequest;
import pl.polsl.cargoflow.model.dto.DrivingLicenseResponse;
import pl.polsl.cargoflow.service.DrivingLicenseService;
import java.util.List;

@RestController
@RequestMapping("/driving-licenses")
public class DrivingLicenseController {

    private final DrivingLicenseService drivingLicenseService;

    public DrivingLicenseController(DrivingLicenseService drivingLicenseService) {
        this.drivingLicenseService = drivingLicenseService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<DrivingLicenseResponse> getDrivingLicenseById(@PathVariable Long id) {
        DrivingLicenseResponse drivingLicenseResponse = drivingLicenseService.getById(id);
        return ResponseEntity.ok(drivingLicenseResponse);
    }

    @GetMapping
    public ResponseEntity<List<DrivingLicenseResponse>> getAllDrivingLicenses() {
        List<DrivingLicenseResponse> drivingLicenseResponses = drivingLicenseService.getAll();
        return ResponseEntity.ok(drivingLicenseResponses);
    }

    @PostMapping
    public ResponseEntity<DrivingLicenseResponse> createDrivingLicense(@RequestBody DrivingLicenseRequest drivingLicenseRequest) {
        DrivingLicenseResponse drivingLicenseResponse = drivingLicenseService.save(drivingLicenseRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(drivingLicenseResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DrivingLicenseResponse> updateDrivingLicense(@PathVariable Long id, @RequestBody DrivingLicenseRequest drivingLicenseRequest) {
        DrivingLicenseResponse drivingLicenseResponse = drivingLicenseService.update(id, drivingLicenseRequest);
        return ResponseEntity.ok(drivingLicenseResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDrivingLicense(@PathVariable Long id) {
        drivingLicenseService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
