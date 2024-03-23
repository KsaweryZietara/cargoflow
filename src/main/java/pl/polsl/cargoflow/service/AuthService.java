package pl.polsl.cargoflow.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import pl.polsl.cargoflow.model.exception.UnauthorizedException;

import java.util.Base64;

@Service
public class AuthService {

    @Value("${ADMIN_LOGIN}")
    private String adminLogin;

    @Value("${ADMIN_PASSWORD}")
    private String adminPassword;

    public void authenticate(String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Basic ")) {
            throw new UnauthorizedException();
        }

        String base64Credentials = authorizationHeader.substring("Basic ".length());
        String credentials = new String(Base64.getDecoder().decode(base64Credentials));
        String[] split = credentials.split(":", 2);

        if (split.length != 2) {
            throw new UnauthorizedException();
        }

        String username = split[0];
        String password = split[1];
        if (!username.equals(adminLogin) || !password.equals(adminPassword)) {
            throw new UnauthorizedException();
        }
    }
}
