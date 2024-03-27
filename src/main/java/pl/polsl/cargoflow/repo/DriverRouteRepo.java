package pl.polsl.cargoflow.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.polsl.cargoflow.model.DriverRoute;
import pl.polsl.cargoflow.model.Employee;
import java.util.List;


@Repository
public interface DriverRouteRepo extends JpaRepository<DriverRoute, Long> {
    List<DriverRoute> findAllByEmployee(Employee employee);

    void deleteByIdAndEmployee(Long id, Employee employee);
}
