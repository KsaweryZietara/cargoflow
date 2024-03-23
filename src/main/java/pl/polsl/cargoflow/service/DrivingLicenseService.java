package pl.polsl.cargoflow.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import pl.polsl.cargoflow.model.DrivingLicense;
import pl.polsl.cargoflow.model.dto.DrivingLicenseResponse;
import pl.polsl.cargoflow.model.dto.DrivingLicenseRequest;
import pl.polsl.cargoflow.repo.DrivingLicenseRepo;

@Service
public class DrivingLicenseService {

    private final DrivingLicenseRepo drivingLicenseRepo;

    public DrivingLicenseService(DrivingLicenseRepo drivingLicenseRepo) {
        this.drivingLicenseRepo = drivingLicenseRepo;
    }

    public DrivingLicenseResponse getById(Long id) {
        DrivingLicense drivingLicense = drivingLicenseRepo.findById(id).orElseThrow(() -> 
            new RuntimeException("Driving license with id " + id + " not found")
        );
        return new DrivingLicenseResponse(drivingLicense);
    }

    public List<DrivingLicenseResponse> getAll() {
        return drivingLicenseRepo.findAll()
                .stream()
                .map(DrivingLicenseResponse::new)
                .collect(Collectors.toList());
    }

    public DrivingLicenseResponse save(DrivingLicenseRequest drivingLicenseRequest) {
        DrivingLicense drivingLicense = drivingLicenseRepo.save(new DrivingLicense(drivingLicenseRequest));
        return new DrivingLicenseResponse(drivingLicense);
    }

    public DrivingLicenseResponse update(Long id, DrivingLicenseRequest drivingLicenseRequest) {
        return drivingLicenseRepo.findById(id)
                .map(drivingLicense -> {
                    drivingLicense.setName(drivingLicenseRequest.getName());
                    return drivingLicenseRepo.save(drivingLicense);
                })
                .map(DrivingLicenseResponse::new)
                .orElseThrow(() ->
                    new RuntimeException("Driving license with id " + id + " not found")
                );
    }

    public void delete(Long id) {
        drivingLicenseRepo.deleteById(id);
    }
}
