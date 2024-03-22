package pl.polsl.cargoflow.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import pl.polsl.cargoflow.model.DrivingLicense;

@Repository
public interface DrivingLicenseRepo extends CrudRepository<DrivingLicense, Long> {
    
}
