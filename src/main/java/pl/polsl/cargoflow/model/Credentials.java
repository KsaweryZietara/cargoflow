package pl.polsl.cargoflow.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.polsl.cargoflow.config.PasswordEncoder;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Credentials {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    private String login;

    private String password;

    @OneToOne(mappedBy = "credentials")
    private Employee employee;

    public Credentials(String login, String password) {
        this.login = login;
        this.password = PasswordEncoder.encodePassword(password);
    }
}
