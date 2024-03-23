package pl.polsl.cargoflow.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pl.polsl.cargoflow.model.DrivingLicense;

@Repository
public interface DrivingLicenseRepo extends JpaRepository<DrivingLicense, Long> {
    
}
