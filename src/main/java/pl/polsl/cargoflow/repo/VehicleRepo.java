package pl.polsl.cargoflow.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import pl.polsl.cargoflow.model.Vehicle;

@Repository
public interface VehicleRepo extends CrudRepository<Vehicle, Long> {
    
}
