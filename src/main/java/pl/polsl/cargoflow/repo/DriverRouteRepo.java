package pl.polsl.cargoflow.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.polsl.cargoflow.model.DriverRoute;
import pl.polsl.cargoflow.model.Employee;
import pl.polsl.cargoflow.model.Vehicle;
import java.util.List;


@Repository
public interface DriverRouteRepo extends JpaRepository<DriverRoute, Long> {
    List<DriverRoute> findAllByEmployee(Employee employee);

    List<DriverRoute> findAllByVehicle(Vehicle vehicle);

    void deleteByIdAndEmployee(Long id, Employee employee);
}
