package pl.polsl.cargoflow.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import pl.polsl.cargoflow.model.Position;

@Repository
public interface PositionRepo extends CrudRepository<Position, Long> {
    
}
