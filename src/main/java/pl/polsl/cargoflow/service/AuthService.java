package pl.polsl.cargoflow.service;

import org.springframework.stereotype.Service;
import pl.polsl.cargoflow.config.PasswordEncoder;
import pl.polsl.cargoflow.model.Credentials;
import pl.polsl.cargoflow.model.Employee;
import pl.polsl.cargoflow.repo.CredentialsRepo;
import java.util.Base64;
import java.util.List;

@Service
public class AuthService {
    private CredentialsRepo credentialsRepo;

    public AuthService(CredentialsRepo credentialsRepo) {
        this.credentialsRepo = credentialsRepo;
    }

    public Employee authenticate(String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Basic ")) {
            return null;
        }

        String base64Credentials = authorizationHeader.substring("Basic ".length());
        String[] credentials = new String(Base64.getDecoder().decode(base64Credentials)).split(":", 2);

        if (credentials.length != 2) {
            return null;
        }

        List<Credentials> credentialsList = credentialsRepo.findByLoginAndPassword(credentials[0], PasswordEncoder.encodePassword(credentials[1]));
        if (credentialsList.isEmpty()) {
            return null;
        }
        
        return credentialsList.getFirst().getEmployee();
    }
}
