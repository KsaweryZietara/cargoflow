package pl.polsl.cargoflow.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.polsl.cargoflow.model.DriverRoute;

@Repository
public interface DriverRouteRepo extends JpaRepository<DriverRoute, Long> {
    
}
