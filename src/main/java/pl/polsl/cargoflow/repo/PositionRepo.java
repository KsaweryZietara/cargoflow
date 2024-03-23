package pl.polsl.cargoflow.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pl.polsl.cargoflow.model.Position;

@Repository
public interface PositionRepo extends JpaRepository<Position, Long> {
    
}
