package pl.polsl.cargoflow.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import pl.polsl.cargoflow.model.Vehicle;
import pl.polsl.cargoflow.model.dto.VehicleResponse;
import pl.polsl.cargoflow.model.dto.VehicleRequest;
import pl.polsl.cargoflow.repo.VehicleRepo;

@Service
public class VehicleService {

    private final VehicleRepo vehicleRepo;

    public VehicleService(VehicleRepo vehicleRepo) {
        this.vehicleRepo = vehicleRepo;
    }

    public VehicleResponse getById(Long id) {
        Vehicle vehicle = vehicleRepo.findById(id).orElseThrow(() -> 
            new RuntimeException("Vehicle with id " + id + " not found")
        );
        return new VehicleResponse(vehicle);
    }

    public List<VehicleResponse> getAll() {
        return vehicleRepo.findAll()
                .stream()
                .map(VehicleResponse::new)
                .collect(Collectors.toList());
    }

    public VehicleResponse save(VehicleRequest vehicleRequest) {
        Vehicle vehicle = vehicleRepo.save(new Vehicle(vehicleRequest));
        return new VehicleResponse(vehicle);
    }

    public VehicleResponse update(Long id, VehicleRequest vehicleRequest) {
        return vehicleRepo.findById(id)
                .map(vehicle -> {
                    vehicle.setType(vehicleRequest.getType());
                    vehicle.setEngineNumber(vehicleRequest.getEngineNumber());
                    vehicle.setEngineCapacity(vehicleRequest.getEngineCapacity());
                    vehicle.setBrand(vehicleRequest.getBrand());
                    vehicle.setMaximumCapacity(vehicleRequest.getMaximumCapacity());
                    return vehicleRepo.save(vehicle);
                })
                .map(VehicleResponse::new)
                .orElseThrow(() ->
                    new RuntimeException("Vehicle with id " + id + " not found")
                );
    }

    public void delete(Long id) {
        vehicleRepo.deleteById(id);
    }
}
