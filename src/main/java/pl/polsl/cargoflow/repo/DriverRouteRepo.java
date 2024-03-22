package pl.polsl.cargoflow.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.polsl.cargoflow.model.DriverRoute;

@Repository
public interface DriverRouteRepo extends CrudRepository<DriverRoute, Long> {
    
}
