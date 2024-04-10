package pl.polsl.cargoflow.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.polsl.cargoflow.config.PasswordEncoder;
import pl.polsl.cargoflow.model.Credentials;
import pl.polsl.cargoflow.model.Employee;
import pl.polsl.cargoflow.repo.CredentialsRepo;
import java.util.Base64;
import java.util.List;

@Service
public class AuthService {

    @Value("${ADMIN_LOGIN}")
    private String adminLogin;

    @Value("${ADMIN_PASSWORD}")
    private String adminPassword;

    private CredentialsRepo credentialsRepo;

    public AuthService(CredentialsRepo credentialsRepo) {
        this.credentialsRepo = credentialsRepo;
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
        List<Credentials> credentialsList = credentialsRepo.findByLoginAndPassword(credentials[0], PasswordEncoder.encodePassword(credentials[1]));
        if (credentialsList.isEmpty()) {
            return null;
        }
        return credentialsList.getFirst().getEmployee();
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
