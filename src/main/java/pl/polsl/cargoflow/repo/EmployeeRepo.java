package pl.polsl.cargoflow.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.polsl.cargoflow.model.Employee;

@Repository
public interface EmployeeRepo extends CrudRepository<Employee, Long> {
    
}
