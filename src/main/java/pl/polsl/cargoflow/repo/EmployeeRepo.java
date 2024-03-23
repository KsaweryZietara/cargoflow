package pl.polsl.cargoflow.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.polsl.cargoflow.model.Employee;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee, Long> {
    
}
