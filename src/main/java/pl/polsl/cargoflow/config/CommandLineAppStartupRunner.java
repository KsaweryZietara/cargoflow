package pl.polsl.cargoflow.config;

import java.sql.Date;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.polsl.cargoflow.model.Credentials;
import pl.polsl.cargoflow.model.Employee;
import pl.polsl.cargoflow.model.Position;
import pl.polsl.cargoflow.repo.EmployeeRepo;
import pl.polsl.cargoflow.repo.PositionRepo;

@Component
public class CommandLineAppStartupRunner implements CommandLineRunner {
    private final PositionRepo positionRepo;
    private final EmployeeRepo employeeRepo;

    @Value("${ADMIN_LOGIN}")
    private String adminLogin;

    @Value("${ADMIN_PASSWORD}")
    private String adminPassword;

    public CommandLineAppStartupRunner(PositionRepo positionRepo, EmployeeRepo employeeRepo) {
        this.positionRepo = positionRepo;
        this.employeeRepo = employeeRepo;
    }
    
    @Override
    public void run(String...args) throws Exception {
        if (positionRepo.findByName("admin").isEmpty()) {
            Position position = new Position();
            position.setName("admin");
            positionRepo.save(position);
        }
        if (positionRepo.findByName("coordinator").isEmpty()) {
            Position position = new Position();
            position.setName("coordinator");
            positionRepo.save(position);
        }
        if (positionRepo.findByName("driver").isEmpty()) {
            Position position = new Position();
            position.setName("driver");
            positionRepo.save(position);
        }
        if (positionRepo.findByName("mechanic").isEmpty()) {
            Position position = new Position();
            position.setName("mechanic");
            positionRepo.save(position);
        }

        Employee employee = new Employee();
        employee.setName(adminLogin);
        employee.setSurname(adminLogin);
        employee.setPesel("99999999999");
        employee.setBirthDate(Date.valueOf(LocalDate.now()));
        Credentials credentials = new Credentials(adminLogin, adminPassword);
        employee.setCredentials(credentials);
        Position position = positionRepo.findByName("admin").getFirst();
        employee.setPosition(position);
        employeeRepo.save(employee);
    }
}
