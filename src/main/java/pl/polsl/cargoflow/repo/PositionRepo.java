package pl.polsl.cargoflow.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.polsl.cargoflow.model.Position;
import java.util.List;


@Repository
public interface PositionRepo extends JpaRepository<Position, Long> {
    List<Position> findByName(String name);
}
