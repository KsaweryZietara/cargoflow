package pl.polsl.cargoflow.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.polsl.cargoflow.model.City;

@Repository
public interface CityRepo extends CrudRepository<City, Long> {
    
}
