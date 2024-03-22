package pl.polsl.cargoflow.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.polsl.cargoflow.model.Route;

@Repository
public interface RouteRepo extends CrudRepository<Route, Long> {
    
}
