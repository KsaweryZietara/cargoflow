package pl.polsl.cargoflow.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import pl.polsl.cargoflow.config.PasswordEncoder;
import pl.polsl.cargoflow.model.Employee;
import pl.polsl.cargoflow.repo.EmployeeRepo;
import java.util.Base64;
import java.util.List;

@Service
public class AuthService {

    @Value("${ADMIN_LOGIN}")
    private String adminLogin;

    @Value("${ADMIN_PASSWORD}")
    private String adminPassword;

    private EmployeeRepo employeeRepo;

    public AuthService(EmployeeRepo employeeRepo) {
        this.employeeRepo = employeeRepo;
    }

    public boolean authenticateAdmin(String authorizationHeader) {
        String[] credentials = authenticate(authorizationHeader);
        if (credentials.length == 0) {
            return false;
        }
        if (!credentials[0].equals(adminLogin) || !credentials[1].equals(adminPassword)) {
            return false;
        }
        return true;
    }

    public Employee authenticateEmployee(String authorizationHeader) {
        String[] credentials = authenticate(authorizationHeader);
        if (credentials.length == 0) {
            return null;
        }
        List<Employee> employees = employeeRepo.findByLoginAndPassword(credentials[0], PasswordEncoder.encodePassword(credentials[1]));
        if (employees.isEmpty()) {
            return null;
        }
        return employees.getFirst();
    }

    private String[] authenticate(String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Basic ")) {
            return new String[0];
        }

        String base64Credentials = authorizationHeader.substring("Basic ".length());
        String credentials = new String(Base64.getDecoder().decode(base64Credentials));
        String[] split = credentials.split(":", 2);

        if (split.length != 2) {
            return new String[0];
        }

        return split;
    }
}
