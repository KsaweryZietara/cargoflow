package pl.polsl.cargoflow.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.polsl.cargoflow.model.Credentials;
import java.util.List;

@Repository
public interface CredentialsRepo extends JpaRepository<Credentials, Long> {
    List<Credentials> findByLoginAndPassword(String login, String password);
}
