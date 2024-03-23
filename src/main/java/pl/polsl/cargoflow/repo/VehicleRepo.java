package pl.polsl.cargoflow.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pl.polsl.cargoflow.model.Vehicle;

@Repository
public interface VehicleRepo extends JpaRepository<Vehicle, Long> {
    
}
