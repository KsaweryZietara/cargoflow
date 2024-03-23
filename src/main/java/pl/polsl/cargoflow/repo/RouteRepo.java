package pl.polsl.cargoflow.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.polsl.cargoflow.model.Route;

@Repository
public interface RouteRepo extends JpaRepository<Route, Long> {
    
}
