package pl.polsl.cargoflow.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.polsl.cargoflow.model.City;

@Repository
public interface CityRepo extends JpaRepository<City, Long> {
    
}
